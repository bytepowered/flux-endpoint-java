package com.github.libchengo.flux.annotation;

/**
 * HTTP数据源范围
 *
 * @author 陈哈哈 chenyongjia365@outlook.com
 */
public enum FxScope {
    /**
     * 从From表单参数中获取
     */
    FORM,

    /**
     * 从动态Path参数中获取
     */
    PATH,

    /**
     * 只从Query和Form表单参数参数列表中读取
     */
    PARAM,

    /**
     * 只从Header参数中读取
     */
    HEADER,

    /**
     * 获取Http Attrs整个Map结构
     */
    ATTRIBUTES,

    /**
     * 获取Http Attrs单个数值
     */
    ATTR,
    ;

}