package net.bytepowered.flux.starter;

import net.bytepowered.flux.annotation.Mapping;
import net.bytepowered.flux.endpoint.EndpointResolver;
import net.bytepowered.flux.endpoint.Registry;
import net.bytepowered.flux.endpoint.entity.ServiceBeanVO;
import org.apache.dubbo.config.spring.ServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 * @since 1.0.0
 */
public class SpringScannerBootstrap implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringScannerBootstrap.class);

    private final SpringClientConfig config;
    private final Registry registry;
    private final EndpointResolver resolver;

    public SpringScannerBootstrap(SpringClientConfig config, Registry registry, EndpointResolver resolver) {
        this.config = config;
        this.registry = registry;
        this.resolver = resolver;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.info("Flux client scanner start scanning...");
        final Instant start = Instant.now();
        final List<ServiceBeanVO> metadata = searchMappingBeans(event.getApplicationContext());
        try {
            registry.startup();
            registry.publish(metadata.stream()
                    .flatMap(m -> resolver.resolve(m).stream())
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            LOGGER.error("Flux client scanner error: ", e);
        } finally {
            registry.shutdown();
        }
        LOGGER.info("Flux client scanner COMPLETED: {}ms", Duration.between(start, Instant.now()));
    }

    private List<ServiceBeanVO> searchMappingBeans(ApplicationContext context) {
        final List<String> packages = scanPackages();
        if (packages.isEmpty()) {
            return searchPackageBeans(null, context);
        } else {
            if (packages.size() == 1) {
                return searchPackageBeans(packages.get(0), context);
            } else {
                return packages.stream().parallel()
                        .flatMap(pack -> searchPackageBeans(pack, context).stream())
                        .collect(Collectors.toList());
            }
        }
    }

    private List<ServiceBeanVO> searchPackageBeans(String packageName, ApplicationContext context) {
        final boolean filterPackage = !StringUtils.isEmpty(packageName);
        if (filterPackage) {
            LOGGER.info("Flux filter package: {}", packageName);
        }
        final String pathPrefix = config.getPathPrefix();
        final String applicationName = context.getApplicationName();
        final Collection<ServiceBean> beans = context.getBeansOfType(ServiceBean.class).values();
        LOGGER.debug("Load dubbo service beans: {}", beans.size());
        return beans.stream()
                .filter(sb -> {
                    if (filterPackage) {
                        return sb.getInterface().startsWith(packageName);
                    } else {
                        return true;
                    }
                })
                .peek(bean -> LOGGER.info("Found dubbo.bean: {}", bean))
                .map(bean -> ServiceBeanVO.builder()
                        .application(applicationName)
                        .pathPrefix(pathPrefix)
                        .group(bean.getGroup())
                        .version(bean.getVersion())
                        .interfaceName(bean.getInterface())
                        .interfaceClass(bean.getInterfaceClass())
                        .methods(Arrays.stream(bean.getInterfaceClass().getDeclaredMethods())
                                .filter(m -> m.isAnnotationPresent(Mapping.class))
                                .collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList());
    }

    private List<String> scanPackages() {
        final String basePackage = config.getBasePackage();
        if (basePackage == null || basePackage.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(basePackage.split(","))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
