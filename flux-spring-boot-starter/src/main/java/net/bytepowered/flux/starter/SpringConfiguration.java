package net.bytepowered.flux.starter;

import net.bytepowered.flux.endpoint.MetadataDecoder;
import net.bytepowered.flux.endpoint.MetadataResolver;
import net.bytepowered.flux.endpoint.Registry;
import net.bytepowered.flux.endpoint.impl.JsonDecoder;
import net.bytepowered.flux.endpoint.impl.registry.ZookeeperConfig;
import net.bytepowered.flux.endpoint.impl.registry.ZookeeperRegistry;
import net.bytepowered.flux.endpoint.impl.resolver.MethodMetadataResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
@Configuration
public class SpringConfiguration {

    @Bean
    SpringScannerBootstrap bootstrap() {
        return new SpringScannerBootstrap(clientConfig(), registry(), resolver());
    }

    @Bean
    MetadataDecoder decoder() {
        return new JsonDecoder();
    }

    @Bean
    MetadataResolver resolver() {
        return new MethodMetadataResolver();
    }

    @Bean
    Registry registry() {
        return new ZookeeperRegistry(zookeeperConfig(), decoder());
    }

    @Bean
    ZookeeperConfig zookeeperConfig() {
        SpringRegistryConfig c = registryConfig();
        return new ZookeeperConfig(
                c.getRootPath(),
                c.getSessionTimeoutMs(),
                c.getConnectionTimeoutMs(),
                c.getAddress());
    }

    @Bean
    SpringRegistryConfig registryConfig() {
        return new SpringRegistryConfig();
    }

    @Bean
    SpringClientConfig clientConfig() {
        return new SpringClientConfig();
    }
}
