package net.bytepowered.flux.endpoint.impl.resolver;

import net.bytepowered.flux.endpoint.entity.ArgumentVO;
import net.bytepowered.flux.endpoint.ParameterResolver;

import java.lang.reflect.Type;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
public class JavaFieldParameterResolver implements ParameterResolver {

    private final JavaTypeHelper typeHelper;

    public JavaFieldParameterResolver(JavaTypeHelper typeHelper) {
        this.typeHelper = typeHelper;
    }

    @Override
    public ArgumentVO resolve(java.lang.reflect.Parameter parameter, Type genericType) {
        if (!typeHelper.isSupportedType(parameter.getType())) {
            return null;
        }
        final GenericTypeHelper generic = GenericTypeHelper.from(parameter, genericType);
        return typeHelper.create(
                parameter,
                generic.className,
                generic.genericTypes,
                parameter.getName(),
                parameter.getName());
    }

}
