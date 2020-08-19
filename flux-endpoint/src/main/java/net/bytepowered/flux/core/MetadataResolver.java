package net.bytepowered.flux.core;

import java.util.List;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 * @since 1.0.0
 */
public interface MetadataResolver {

    List<EndpointMetadata> resolve(ServiceBeanMetadata beanMetadata);
}
