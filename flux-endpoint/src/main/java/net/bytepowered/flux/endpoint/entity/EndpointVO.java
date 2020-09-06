package net.bytepowered.flux.endpoint.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Endpoint 用于包装网关Http请求与后端Dubbo/Http服务请求的传输对象。
 *
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
public class EndpointVO implements Serializable {

    /**
     * Endpoint版本
     */
    private String version = "v1";

    /**
     * 应用名称
     */
    private String application;

    /**
     * 映射的协议名称
     */
    private ProtoType protocol;

    /**
     * Dubbo.group
     */
    private String rpcGroup;

    /**
     * Dubbo.version
     */
    private String rpcVersion;

    /**
     * 重试次数
     */
    private int rpcRetries;

    /**
     * 接口超时：Duration
     */
    private String rpcTimeout;

    /**
     * Should authorize
     */
    private boolean authorize;

    /**
     * 目标服务Host。在Http中使用
     */
    private String upstreamHost;

    /**
     * 网关连接目标服务路径，对应为后端Dubbo.interface或者Http地址
     */
    private String upstreamUri;

    /**
     * 网关连接目标服务Method。对应为后端Dubbo.method或者Http.method
     */
    private String upstreamMethod;

    /**
     * 网关侧定义的接收Http请求路径
     */
    private String httpPattern;

    /**
     * 网关侧定义的接收Http请求Method
     */
    private String httpMethod;

    /**
     * 参数列表
     */
    private List<ArgumentVO> arguments;

    public EndpointVO(String version, String application, ProtoType protocol,
                      String rpcGroup, String rpcVersion, int rpcRetries, String rpcTimeout,
                      boolean authorize,
                      String upstreamHost, String upstreamUri, String upstreamMethod,
                      String httpPattern, String httpMethod,
                      List<ArgumentVO> arguments) {
        this.version = version;
        this.application = application;
        this.protocol = protocol;
        this.rpcGroup = rpcGroup;
        this.rpcVersion = rpcVersion;
        this.rpcRetries = rpcRetries;
        this.rpcTimeout = rpcTimeout;
        this.authorize = authorize;
        this.upstreamHost = upstreamHost;
        this.upstreamUri = upstreamUri;
        this.upstreamMethod = upstreamMethod;
        this.httpPattern = httpPattern;
        this.httpMethod = httpMethod;
        this.arguments = arguments;
    }

    public String getVersion() {
        return version;
    }

    public String getApplication() {
        return application;
    }

    public ProtoType getProtocol() {
        return protocol;
    }

    public String getRpcGroup() {
        return rpcGroup;
    }

    public String getRpcVersion() {
        return rpcVersion;
    }

    public int getRpcRetries() {
        return rpcRetries;
    }

    public String getRpcTimeout() {
        return rpcTimeout;
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public String getUpstreamHost() {
        return upstreamHost;
    }

    public String getUpstreamUri() {
        return upstreamUri;
    }

    public String getUpstreamMethod() {
        return upstreamMethod;
    }

    public String getHttpPattern() {
        return httpPattern;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public List<ArgumentVO> getArguments() {
        return arguments;
    }

    //

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String version = "v1";
        private String application;
        private ProtoType protocol;
        private String rpcGroup;
        private String rpcVersion;
        private int rpcRetries;
        private String rpcTimeout;
        private boolean authorize;
        private String upstreamHost;
        private String upstreamUri;
        private String upstreamMethod;
        private String httpPattern;
        private String httpMethod;
        private List<ArgumentVO> arguments;

        private Builder() {
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder application(String application) {
            this.application = application;
            return this;
        }

        public Builder protocol(ProtoType protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder rpcGroup(String rpcGroup) {
            this.rpcGroup = rpcGroup;
            return this;
        }

        public Builder rpcVersion(String rpcVersion) {
            this.rpcVersion = rpcVersion;
            return this;
        }

        public Builder rpcRetries(int rpcRetries) {
            this.rpcRetries = rpcRetries;
            return this;
        }

        public Builder rpcTimeout(String rpcTimeout) {
            this.rpcTimeout = rpcTimeout;
            return this;
        }

        public Builder authorize(boolean authorize) {
            this.authorize = authorize;
            return this;
        }

        public Builder upstreamHost(String upstreamHost) {
            this.upstreamHost = upstreamHost;
            return this;
        }

        public Builder upstreamUri(String upstreamUri) {
            this.upstreamUri = upstreamUri;
            return this;
        }

        public Builder upstreamMethod(String upstreamMethod) {
            this.upstreamMethod = upstreamMethod;
            return this;
        }

        public Builder httpPattern(String httpPattern) {
            this.httpPattern = httpPattern;
            return this;
        }

        public Builder httpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder arguments(List<ArgumentVO> arguments) {
            this.arguments = arguments;
            return this;
        }

        public EndpointVO build() {
            return new EndpointVO(version, application, protocol, rpcGroup, rpcVersion, rpcRetries, rpcTimeout, authorize, upstreamHost, upstreamUri, upstreamMethod, httpPattern, httpMethod, arguments);
        }
    }
}
