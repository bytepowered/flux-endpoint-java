package net.bytepowered.flux.endpoint.impl.resolver;

import net.bytepowered.flux.annotation.*;
import net.bytepowered.flux.endpoint.entity.ArgumentType;
import net.bytepowered.flux.endpoint.entity.ArgumentVO;

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
        final Scope scope;
        final String httpName;
        if (element.isAnnotationPresent(Attr.class)) {
            final Attr attr = element.getAnnotation(Attr.class);
            scope = Scope.ATTR;
            httpName = aliasFor(attr.name(), attr.value());
        } else if (element.isAnnotationPresent(Attrs.class)) {
            scope = Scope.ATTRS;
            httpName = "$attrs";
        } else if (element.isAnnotationPresent(Form.class)) {
            final Form param = element.getAnnotation(Form.class);
            scope = Scope.FORM;
            httpName = aliasFor(param.name(), param.value());
        } else if (element.isAnnotationPresent(Header.class)) {
            final Header header = element.getAnnotation(Header.class);
            scope = Scope.HEADER;
            httpName = aliasFor(header.name(), header.value());
        } else if (element.isAnnotationPresent(Param.class)) {
            final Param attr = element.getAnnotation(Param.class);
            scope = Scope.PARAM;
            httpName = aliasFor(attr.name(), attr.value());
        } else if (element.isAnnotationPresent(Path.class)) {
            final Path path = element.getAnnotation(Path.class);
            scope = Scope.PATH;
            httpName = aliasFor(path.name(), path.value());
        } else if (element.isAnnotationPresent(Query.class)) {
            final Query query = element.getAnnotation(Query.class);
            scope = Scope.QUERY;
            httpName = aliasFor(query.name(), query.value());
        } else {
            scope = Scope.AUTO;
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
