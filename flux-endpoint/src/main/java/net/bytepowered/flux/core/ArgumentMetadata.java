package net.bytepowered.flux.core;

import net.bytepowered.flux.annotation.FxScope;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
public class ArgumentMetadata implements Serializable {

    private transient AnnotatedElement element;

    /**
     * 字段数据类型的class名称
     */
    private String typeClass;

    /**
     * 泛型类型Class名称
     */
    private List<String> typeGeneric;

    /**
     * POJO字段名称，或者参数字段名
     */
    private String argName;

    /**
     * 参数字段类型
     */
    private ArgumentType argType;

    /**
     * 映射Http的参数名。表示值Value从网关Http请求的哪个数据字段中获取。
     */
    private String httpName;

    /**
     * 指定从网关Http请求的数据源，Value值将从指定的数据源中读取。
     */
    private FxScope httpScope;

    /**
     * 当type类型为POJO时，fields记录所有POJO成员字段的列表及其类型
     */
    private List<ArgumentMetadata> fields;

    //

    public AnnotatedElement getElement() {
        return element;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public List<String> getTypeGeneric() {
        return typeGeneric;
    }

    public String getArgName() {
        return argName;
    }

    public ArgumentType getArgType() {
        return argType;
    }

    public String getHttpName() {
        return httpName;
    }

    public FxScope getHttpScope() {
        return httpScope;
    }

    public List<ArgumentMetadata> getFields() {
        return fields;
    }

    public ArgumentMetadata(AnnotatedElement element, String typeClass, List<String> typeGeneric, String argName, ArgumentType argType, String httpName, FxScope httpScope, List<ArgumentMetadata> fields) {
        this.element = element;
        this.typeClass = typeClass;
        this.typeGeneric = typeGeneric;
        this.argName = argName;
        this.argType = argType;
        this.httpName = httpName;
        this.httpScope = httpScope;
        this.fields = fields;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private transient AnnotatedElement element;
        private String typeClass;
        private List<String> typeGeneric;
        private String argName;
        private ArgumentType argType;
        private String httpName;
        private FxScope httpScope;
        private List<ArgumentMetadata> fields;

        private Builder() {
        }

        public Builder element(AnnotatedElement element) {
            this.element = element;
            return this;
        }

        public Builder typeClass(String typeClass) {
            this.typeClass = typeClass;
            return this;
        }

        public Builder typeGeneric(List<String> genericTypes) {
            this.typeGeneric = genericTypes;
            return this;
        }

        public Builder argName(String argName) {
            this.argName = argName;
            return this;
        }

        public Builder argType(ArgumentType type) {
            this.argType = type;
            return this;
        }

        public Builder httpName(String httpName) {
            this.httpName = httpName;
            return this;
        }

        public Builder httpScope(FxScope httpScope) {
            this.httpScope = httpScope;
            return this;
        }

        public Builder fields(List<ArgumentMetadata> fields) {
            this.fields = fields;
            return this;
        }

        public ArgumentMetadata build() {
            return new ArgumentMetadata(element, typeClass, typeGeneric, argName, argType, httpName, httpScope, fields);
        }
    }

}
