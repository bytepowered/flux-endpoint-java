package net.bytepowered.flux.endpoint;


import net.bytepowered.flux.endpoint.entity.ArgumentVO;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * FxParameter 解析器
 *
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 */
public interface ParameterResolver {

    /**
     * 解析参数，返回参数字段。如果不解析，返回Null
     *
     * @param parameter   参数对象
     * @param genericType 参数的泛型类型
     * @return ArgumentVO，或者为Null
     */
    ArgumentVO resolve(Parameter parameter, Type genericType);
}
