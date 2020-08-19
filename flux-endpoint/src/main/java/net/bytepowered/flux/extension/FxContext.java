package net.bytepowered.flux.extension;

import org.apache.dubbo.rpc.RpcContext;

import java.util.Map;

/**
 * @author yongjia.chen@hotmail.com
 * @since 1.0.0
 */
public final class FxContext {

    public static final String KeyRequestId = "x-request-id";
    public static final String KeyRequestHost = "x-request-host";
    public static final String KeyRequestAgent = "x-request-agent";
    public static final String KeyRequestBiz = "x-request-biz";

    public static Map<String, String> getAttachment() {
        return RpcContext.getContext().getAttachments();
    }

    public static String getString(String key) {
        return RpcContext.getContext().getAttachment(key);
    }

    public static String getRequestId() {
        return getString(KeyRequestId);
    }

    public static String getRequestHost() {
        return getString(KeyRequestHost);
    }

    public static String getRequestAgent() {
        return getString(KeyRequestAgent);
    }

    public static String getRequestBiz() {
        return getString(KeyRequestBiz);
    }
}
