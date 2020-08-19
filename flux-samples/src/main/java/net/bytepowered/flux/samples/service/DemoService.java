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

    @FxMapping(path = "/test/hello", method = FxMethod.GET, authorized = false)
    FxResponse hello(
            @Min(10)
            @FxQuery(value = "group") Integer group,
            @NotEmpty
            @FxQuery List<Integer> state
    );

    @FxMapping(path = "/test/pojo2", method = FxMethod.GET, authorized = false)
    Object pojo(
            @FxParam(value = "group") Integer group,
            @NotNull UserDO user
    );

    @FxMapping(path = "/test/{userId}", method = FxMethod.GET)
    Map<String, Object> helloDynamic(
            @FxPath(value = "userId") String userId,
            @FxParam String queryId);
}