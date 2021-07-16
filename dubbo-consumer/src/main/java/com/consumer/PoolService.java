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
public class PoolService {
    @Reference(timeout = 3000)
    IDemoService demoService;

    public void execute() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 5L, TimeUnit.MINUTES, new ArrayBlockingQueue(100000));
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(3*finalI *1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                demoService.getData(new ReqData().setData(new int[1 * 1024 * 1024]));
                log.error("thread{}=>done", finalI);
            });
            thread.setName("thread-" + i);
            executorService.execute(thread);
            //uncomment line below, memory kept by InternalThreadLocalMap will be freed
            //InternalThreadLocal.removeAll();
        }
    }
}
