package net.bytepowered.flux.impl.registry;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
public class ZookeeperRegistryConfig {

    private final String rootPath;
    /**
     * Session超时：毫秒
     */
    private final int sessionTimeoutMs;

    /**
     * 连接超时：毫秒
     */
    private final int connectionTimeoutMs;

    /**
     * 注册中心地址列表
     */
    private final String address;

    public ZookeeperRegistryConfig(String rootPath, int sessionTimeoutMs, int connectionTimeoutMs, String address) {
        this.rootPath = rootPath;
        this.sessionTimeoutMs = sessionTimeoutMs;
        this.connectionTimeoutMs = connectionTimeoutMs;
        this.address = address;
    }

    public String getRootPath() {
        return rootPath;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public String getAddress() {
        return address;
    }
}
