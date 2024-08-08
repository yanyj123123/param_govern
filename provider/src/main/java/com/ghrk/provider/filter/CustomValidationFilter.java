package com.ghrk.provider.filter;

import com.ghrk.common.exception.GlobalException;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.validation.Validation;
import org.apache.dubbo.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;
import static org.apache.dubbo.common.constants.FilterConstants.VALIDATION_KEY;

@Activate(group = {CONSUMER, PROVIDER}, value = "customValidationFilter", order = 10000)
public class CustomValidationFilter implements Filter {

    private Validation validation;

    public void setValidation(Validation validation) { this.validation = validation; }

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (validation != null && !invocation.getMethodName().startsWith("$")
                && ConfigUtils.isNotEmpty(invoker.getUrl().getMethodParameter(invocation.getMethodName(), VALIDATION_KEY))) {
            try {
                Validator validator = validation.getValidator(invoker.getUrl());
                if (validator != null) {
                    validator.validate(invocation.getMethodName(), invocation.getParameterTypes(), invocation.getArguments());
                }
            } catch (RpcException e) {
                throw e;
            } catch (ConstraintViolationException e) {// 这边细化了异常类型
                // 注1
                Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
                if (CollectionUtils.isNotEmpty(violations)) {
                    ConstraintViolation<?> violation = violations.iterator().next();// 取第一个进行提示就行了
                    return AsyncRpcResult.newDefaultAsyncResult(new GlobalException(violation.getMessage()), invocation);
                }
                return AsyncRpcResult.newDefaultAsyncResult(new ValidationException("参数不合法!"), invocation);
            } catch (Throwable t) {
                return AsyncRpcResult.newDefaultAsyncResult(t, invocation);
            }
        }
        return invoker.invoke(invocation);
    }
}
