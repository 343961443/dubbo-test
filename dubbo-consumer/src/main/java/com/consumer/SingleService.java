package com.consumer;

import com.api.IDemoService;
import com.api.ReqData;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhaoshuai
 * @Date: 2021/7/16
 */
@Component
@Slf4j
public class SingleService {
    @Reference(timeout = 4000)
    IDemoService demoService;

    public void execute() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 5L, TimeUnit.MINUTES, new ArrayBlockingQueue(100000));
        for (int i = 0; i < 100; i++) {
            int [] array = new int[1 * 1024 * 1024];
            try {
                demoService.getData(new ReqData().setData(array));
            } catch (Exception e) {
                log.error("异常",e);
            }
            log.error("thread{}=>done", i);
            //uncomment line below, memory kept by InternalThreadLocalMap will be freed
            //InternalThreadLocal.removeAll();
        }

    }
}
