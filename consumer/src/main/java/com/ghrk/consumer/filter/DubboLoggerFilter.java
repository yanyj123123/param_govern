package com.ghrk.consumer.filter;
import com.alibaba.fastjson2.JSON;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DubboLoggerFilter implements Filter{
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * rpc日志最长字符串
     */
    private final static Integer MAX_LOG_LENGTH=5000;
    /**
     * rpc日志超过长度截取长度
     */
    private final static Integer REMAINING_LOG_LENGTH=1000;
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String methodName = invocation.getMethodName(); //获取方法名
        Object[] arguments = invocation.getArguments(); //获取参数
        String className = invoker.getInterface().getName();
        String callMethod = className + "." + methodName;
        String argsJson = JSON.toJSONString(arguments); //转为json字符串
        // 开始记录
        logger.info("rpc接口callMethod:{}>>入参:{}", callMethod, argsJson);
        System.err.println("rpc接口callMethod:{}>>入参:{}"+callMethod+argsJson);
        long start = System.currentTimeMillis(); // 开始时间

        // 调用RPC方法，获取返回结果    invoker.invoke(invocation)表示执行RPC
        AsyncRpcResult result = (AsyncRpcResult) invoker.invoke(invocation);
        if (result.hasException()) {
            // 如果存在异常
            logger.info("rpc接口callMethod:{},接口耗时:{},异常:{}ms,", callMethod, System.currentTimeMillis() - start, result.getException().getMessage());
        } else {
            Object resultString = JSON.toJSON(result.getAppResponse().getValue());
            if (resultString != null && resultString.toString().length() > MAX_LOG_LENGTH) {
                resultString = resultString.toString().substring(0, REMAINING_LOG_LENGTH) + "...";
            }
            logger.info("rpc接口callMethod:{},出参:{},接口耗时:{}ms", callMethod, resultString, System.currentTimeMillis() - start);
        }
        return result;
    }
}
