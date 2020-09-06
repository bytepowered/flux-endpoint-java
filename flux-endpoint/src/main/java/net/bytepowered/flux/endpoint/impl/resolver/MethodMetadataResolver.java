package net.bytepowered.flux.endpoint.impl.resolver;

import net.bytepowered.flux.annotation.FxMapping;
import net.bytepowered.flux.endpoint.*;
import net.bytepowered.flux.endpoint.entity.ArgumentVO;
import net.bytepowered.flux.endpoint.entity.EndpointVO;
import net.bytepowered.flux.endpoint.entity.ProtoType;
import net.bytepowered.flux.endpoint.entity.ServiceBeanVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 * @since 1.0.0
 */
public class MethodMetadataResolver implements MetadataResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodMetadataResolver.class);

    private final List<ParameterResolver> parameterResolvers = new ArrayList<>();

    public MethodMetadataResolver() {
        final JavaTypeHelper helper = new JavaTypeHelper();
        parameterResolvers.add(new JavaFieldParameterResolver(helper));
        parameterResolvers.add(new ObjectParameterResolver(helper));
    }

    @Override
    public List<EndpointVO> resolve(ServiceBeanVO metadata) {
        LOGGER.info("Dubbo.Bean: group={}, version={}, iface={}, methods={}",
                metadata.getGroup(), metadata.getVersion(), metadata.getInterfaceName(), metadata.getMethods().size());
        return metadata.getMethods().stream()
                .map(method -> resolveToMetadata(
                        metadata.getPathPrefix(),
                        metadata.getApplication(),
                        metadata.getGroup(),
                        metadata.getVersion(),
                        metadata.getInterfaceName(),
                        method.getDeclaredAnnotation(FxMapping.class),
                        method))
                .collect(Collectors.toList());
    }

    public EndpointVO resolveToMetadata(String prefix, String appName,
                                        String serviceGroup, String serviceVer, String interfaceName,
                                        FxMapping mapping, Method method) {
        final EndpointVO.Builder builder = EndpointVO.builder()
                .application(appName == null ? "" : appName)
                .rpcGroup(serviceGroup == null ? "" : serviceGroup)
                .rpcVersion(serviceVer == null ? "" : serviceVer);
        final String path = mapping.path();
        final String group = mapping.group();
        final String version = mapping.version();
        if (!group.isEmpty()) {
            builder.rpcGroup(group);
        }
        if (!version.isEmpty()) {
            builder.rpcVersion(version);
        }
        builder.protocol(ProtoType.DUBBO);
        builder.authorize(mapping.authorized());
        // 网关侧请求定义
        builder.httpPattern(Paths.get(prefix, path).toString());
        builder.httpMethod(mapping.method().name());
        // 后端目标请求定义
        builder.upstreamUri(interfaceName);
        builder.upstreamMethod(method.getName());
        // 解析方法参数类型
        final int count = method.getParameterCount();
        final List<ArgumentVO> arguments = new ArrayList<>(count);
        if (count > 0) {
            final java.lang.reflect.Parameter[] mps = method.getParameters();
            final Type[] gts = method.getGenericParameterTypes();
            for (int i = 0; i < count; i++) {
                ArgumentVO field = null;
                for (ParameterResolver resolver : parameterResolvers) {
                    field = resolver.resolve(mps[i], gts[i]);
                    if (field != null) {
                        break;
                    }
                }
                if (field == null) {
                    throw new IllegalArgumentException("无法解析的方法参数:" + method +
                            ", parameter=" + mps[i] +
                            ", generic=" + gts[i]
                    );
                } else {
                    arguments.add(field);
                }
            }
        }
        builder.arguments(arguments);
        return builder.build();
    }
}
