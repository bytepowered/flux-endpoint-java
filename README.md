# Flux Endpoint - Flux网关Java接入端点客户端

## 依赖

见Maven中央仓库: [https://search.maven.org/artifact/net.bytepowered/flux-spring-boot-starter](https://search.maven.org/artifact/net.bytepowered/flux-spring-boot-starter)

Latest version: `1.0.0`

### Apache maven

```xml
<dependency>
  <groupId>net.bytepowered</groupId>
  <artifactId>flux-spring-boot-starter</artifactId>
  <version>{latest-version}</version>
</dependency>
```

### Gradle

```groovy
implementation 'net.bytepowered:flux-spring-boot-starter:{latest-version}'
```

### Kotlin Gradle DSL

```groovy
implementation("net.bytepowered:flux-spring-boot-starter:{latest-version}")
```

## 配置

```yaml
flux:
  base-package: "net.bytepowered.flux.samples.service"
  path-prefix: "/sample"
  metadata-registry:
    address: ${FLUX_REGISTRY_ADDRESSS:zookeeper://localhost:2181}
```

配置说明

**flux.base-package**

`base-package` 指定了FluxEndpoint客户端扫描的包目录路径。在启动时，FluxEndpoint客户端会扫描所有Dubbo的ServiceBean列表。
如果指定`base-package`参数，客户端将逐一过滤ServiceBean列表，如果ServiceBean所实现的Dubbo接口与base-package路径前缀相同，则进入接口方法扫描逻辑。

**path-prefix**

`path-prefix` 指定了生成API地址路径时的全局前缀，为空值时不使用全局前缀。例如在如下的接口定义中：

```java
public interface DemoService {

    @FxMapping(path = "/test/hello", method = FxMethod.GET, authorized = false)
    FxResponse hello(
            @Min(10)
            @FxQuery(value = "group") Integer group,
            @NotEmpty
            @FxQuery List<Integer> state
    );
}
```

`path-prefix`配置值为`/sample`，则FluxEndpoint客户端会添加全局前缀，注册到网关的接口API地址为：`/sample/test/hello`。

**metadata-registry**

元数据注册中心的配置信息。有如下可配置项：

- `address` : Zookeeper的服务地址，多个地址以英文逗号分割。