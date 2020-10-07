package net.bytepowered.flux.endpoint;

import net.bytepowered.flux.endpoint.entity.EndpointVO;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 * @since 1.0.0
 */
public interface EndpointDecoder {

    String decode(EndpointVO metadata);
}
