package com.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import static org.apache.dubbo.rpc.RpcContext.getContext;

@Activate(
        group = {"consumer"}
)
@Slf4j
public class CleanContentFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            return invoker.invoke(invocation);
        } finally {
            RpcContext.removeContext();
            getContext().setFuture(null);
        }
    }
}