package net.bytepowered.flux.endpoint;

import net.bytepowered.flux.endpoint.entity.EndpointVO;
import net.bytepowered.flux.endpoint.entity.ServiceBeanVO;

import java.util.List;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 * @since 1.0.0
 */
public interface EndpointResolver {

    List<EndpointVO> resolve(ServiceBeanVO beanMetadata);
}
