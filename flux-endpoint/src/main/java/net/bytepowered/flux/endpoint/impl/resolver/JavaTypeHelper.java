package net.bytepowered.flux.endpoint.impl.resolver;

import net.bytepowered.flux.annotation.*;
import net.bytepowered.flux.endpoint.entity.ArgumentVO;
import net.bytepowered.flux.endpoint.entity.ArgumentType;

import java.lang.reflect.AnnotatedElement;
import java.util.*;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
public class JavaTypeHelper {

    private final Map<Class<?>, Class<?>> supportedTypes = new HashMap<>(16);

    public JavaTypeHelper() {
        supportedTypes.put(byte.class, Byte.class);
        supportedTypes.put(char.class, Character.class);
        supportedTypes.put(boolean.class, Boolean.class);
        supportedTypes.put(int.class, Integer.class);
        supportedTypes.put(long.class, Long.class);
        supportedTypes.put(float.class, Float.class);
        supportedTypes.put(double.class, Double.class);
        supportedTypes.put(String.class, String.class);
        supportedTypes.put(Collection.class, Collection.class);
        supportedTypes.put(Set.class, Set.class);
        supportedTypes.put(List.class, List.class);
        supportedTypes.put(Map.class, Map.class);
    }

    public ArgumentVO create(AnnotatedElement element,
                             String className, List<String> genericTypes,
                             String fieldName, String defaultHttpName) {
        final FxScope scope;
        final String httpName;
        if (element.isAnnotationPresent(FxAttr.class)) {
            final FxAttr attr = element.getAnnotation(FxAttr.class);
            scope = FxScope.ATTR;
            httpName = aliasFor(attr.name(), attr.value());
        } else if (element.isAnnotationPresent(FxAttrs.class)) {
            scope = FxScope.ATTRS;
            httpName = "$attrs";
        } else if (element.isAnnotationPresent(FxForm.class)) {
            final FxForm param = element.getAnnotation(FxForm.class);
            scope = FxScope.FORM;
            httpName = aliasFor(param.name(), param.value());
        } else if (element.isAnnotationPresent(FxHeader.class)) {
            final FxHeader header = element.getAnnotation(FxHeader.class);
            scope = FxScope.HEADER;
            httpName = aliasFor(header.name(), header.value());
        } else if (element.isAnnotationPresent(FxParam.class)) {
            final FxParam attr = element.getAnnotation(FxParam.class);
            scope = FxScope.PARAM;
            httpName = aliasFor(attr.name(), attr.value());
        } else if (element.isAnnotationPresent(FxPath.class)) {
            final FxPath path = element.getAnnotation(FxPath.class);
            scope = FxScope.PATH;
            httpName = aliasFor(path.name(), path.value());
        } else if (element.isAnnotationPresent(FxQuery.class)) {
            final FxQuery query = element.getAnnotation(FxQuery.class);
            scope = FxScope.QUERY;
            httpName = aliasFor(query.name(), query.value());
        } else {
            scope = FxScope.AUTO;
            httpName = defaultHttpName;
        }
        return ArgumentVO.builder()
                .element(element)
                .typeClass(className)
                .typeGeneric(genericTypes)
                .argName(fieldName)
                .httpName(fixHttpName(httpName, defaultHttpName))
                .httpScope(scope)
                .argType(ArgumentType.PRIMITIVE)
                .build();
    }

    /**
     * 判断参数类型，是否为支持的端点数值类型。
     * 前提条件：必须是Java运行时内置数据类型：即可以被网关运行时加载；
     *
     * @param paramType 参数类型
     * @return 是否值类型字段
     */
    public boolean isSupportedType(Class<?> paramType) {
        return supportedTypes.containsKey(paramType) || supportedTypes.containsValue(paramType);
    }

    private String aliasFor(String nameOfNameMethod, String nameOfValueMethod) {
        if (nameOfNameMethod == null || nameOfNameMethod.isEmpty()) {
            return nameOfValueMethod;
        } else {
            return nameOfNameMethod;
        }
    }

    private String fixHttpName(String httpName, String defaultHttpName) {
        return (httpName == null || httpName.isEmpty()) ? defaultHttpName : httpName;
    }
}
