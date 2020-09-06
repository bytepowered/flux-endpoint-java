package net.bytepowered.flux.endpoint;

import net.bytepowered.flux.endpoint.entity.EndpointVO;

import java.util.List;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 * @since 1.0.0
 */
public interface Registry {

    /**
     * Startup
     */
    void startup();

    /**
     * Shutdown
     */
    void shutdown();

    /**
     * Publish metadata
     *
     * @param metadataList Metadata
     * @throws Exception Error if
     */
    void publish(List<EndpointVO> metadataList) throws Exception;

}
