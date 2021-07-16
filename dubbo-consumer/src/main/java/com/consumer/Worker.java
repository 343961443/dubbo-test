package com.consumer;

import com.api.IDemoService;
import com.api.ReqData;
import com.huya.skyeye.core.concurrent.handler.ConsumeWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author: zhaoshuai
 * @Date: 2021/7/16
 */
@Slf4j
@Component
public class Worker extends ConsumeWorker<ReqData> {

    @Reference(timeout = 3000)
    IDemoService demoService;
    @Override
    public void consume(Collection<ReqData> collection) {
        for (ReqData reqData:collection) {
            demoService.getData(reqData.setData(new int[1 * 1024 * 1024]));
            log.error("thread{}=>done", Thread.currentThread().getName());
        }

    }

    @Override
    public String getQueueName() {
        return "testworker";
    }
}
