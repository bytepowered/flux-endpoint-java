package net.bytepowered.flux.extension;

import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 陈哈哈 (yongjia.chen@hotmail.com)
 * @since 1.0.0
 */
public class FxMessageFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FxMessageFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        final Result ret = invoker.invoke(invocation);
        final Throwable err = ret.getException();
        if (err != null) {
            if (err instanceof ConstraintViolationException) {
                onHandleValidation((ConstraintViolationException) err, ret, invocation);
            } else {
                onHandleException(err, ret, invocation);
            }
        }
        return ret;
    }

    private void onHandleValidation(ConstraintViolationException cve, Result ret, Invocation invocation) {
        final Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
        if (violations.isEmpty()) {
            ret.setValue(FxResponse.ofError("VALIDATION:NO_MESSAGE"));
        } else {
            final String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .filter(m -> !StringUtils.isEmpty(m))
                    .distinct()
                    .collect(Collectors.joining(","));
            ret.setValue(FxResponse.bodyMap()
                    .asError()
                    .statusCode(400)
                    .message(message).build()
            );
        }
        ret.setException(null);
    }

    private void onHandleException(Throwable err, Result ret, Invocation invocation) {
        LOGGER.error("Invoke error, method: {}", invocation.getMethodName(), err);
        ret.setValue(FxResponse.ofError(err.getMessage()));
        ret.setException(null);
    }

}
