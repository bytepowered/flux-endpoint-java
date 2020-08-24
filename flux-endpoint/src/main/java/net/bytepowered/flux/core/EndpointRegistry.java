package net.bytepowered.flux.core;

import java.util.List;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 * @since 1.0.0
 */
public interface EndpointRegistry {

    /**
     * Startup
     */
    void startup();

    /**
     * Shutdown
     */
    void shutdown();

    /**
     * Submit metadata
     *
     * @param metadataList Metadata
     * @throws Exception Error if
     */
    void submit(List<EndpointMetadata> metadataList) throws Exception;

}
