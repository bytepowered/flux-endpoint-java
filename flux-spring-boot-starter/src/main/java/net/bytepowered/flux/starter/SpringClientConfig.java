package net.bytepowered.flux.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Flux客户端配置
 *
 * @author yongjia.chen
 * @since 1.0.0
 */
@Component
@ConfigurationProperties("flux")
public class SpringClientConfig {

    /**
     * 定义Flux元数据的interface接口定义包基础路径
     */
    private String basePackage;
    private String pathPrefix;
    @Deprecated
    private String prefix;

    public String getPathPrefix() {
        if (pathPrefix == null || pathPrefix.isEmpty()) {
            return prefix;
        } else {
            return pathPrefix;
        }
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
