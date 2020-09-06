package net.bytepowered.flux.endpoint.entity;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
public class ServiceBeanVO {

    private String application;
    private String pathPrefix;
    private String version;
    private String group;
    private String interfaceName;
    private Class<?> interfaceClass;
    private List<Method> methods;

    public String getApplication() {
        return application;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public String getVersion() {
        return version;
    }

    public String getGroup() {
        return group;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String application;
        private String pathPrefix;
        private String version;
        private String group;
        private String interfaceName;
        private Class<?> interfaceClass;
        private List<Method> methods;

        private Builder() {
        }

        public Builder application(String application) {
            this.application = application;
            return this;
        }

        public Builder pathPrefix(String prefix) {
            this.pathPrefix = prefix;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder group(String group) {
            this.group = group;
            return this;
        }

        public Builder interfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public Builder interfaceClass(Class<?> interfaceClass) {
            this.interfaceClass = interfaceClass;
            return this;
        }

        public Builder methods(List<Method> methods) {
            this.methods = methods;
            return this;
        }

        public ServiceBeanVO build() {
            ServiceBeanVO serviceBeanVO = new ServiceBeanVO();
            serviceBeanVO.group = this.group;
            serviceBeanVO.pathPrefix = this.pathPrefix;
            serviceBeanVO.interfaceClass = this.interfaceClass;
            serviceBeanVO.methods = this.methods;
            serviceBeanVO.interfaceName = this.interfaceName;
            serviceBeanVO.application = this.application;
            serviceBeanVO.version = this.version;
            return serviceBeanVO;
        }
    }
}