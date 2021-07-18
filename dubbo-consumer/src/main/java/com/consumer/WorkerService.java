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
public class WorkerService extends ConsumeWorker<ReqData> {

    @Reference(timeout = 2000)
    IDemoService demoService;

    @Override
    public void consume(Collection<ReqData> collection) {
        for (ReqData reqData : collection) {
            try {
                demoService.getData(reqData.setData(new int[1 * 100 * 1024]));
            } catch (Exception e) {
                log.error("id=>{}超时了", reqData.getInteger(), e);
            } finally {
                log.error("id=>{},thread{}=>done", reqData.getInteger(), Thread.currentThread().getName());
            }


        }

    }

    @Override
    public String getQueueName() {
        return "testworker";
    }
}
