package net.bytepowered.flux.samples.service;

import net.bytepowered.flux.annotation.*;
import net.bytepowered.flux.extension.FxResponse;
import net.bytepowered.flux.samples.entity.UserDO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
public interface DemoService {

    @Mapping(path = "/test/hello", method = HttpMethod.GET, authorized = false)
    FxResponse hello(
            @Min(10)
            @Query(value = "group") Integer group,
            @NotEmpty
            @Query(value = "state") List<Integer> state
    );

    @Mapping(path = "/test/pojo2", method = HttpMethod.GET, authorized = false)
    Object pojo(
            @Param(value = "group") Integer group,
            @NotNull UserDO user
    );

    @Mapping(path = "/test/{userId}", method = HttpMethod.GET)
    Map<String, Object> helloDynamic(
            @Path(value = "userId") String userId,
            @Param String queryId);
}