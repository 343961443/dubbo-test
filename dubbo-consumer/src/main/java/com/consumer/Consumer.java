package com.consumer;

import com.api.ReqData;
import com.huya.skyeye.common.util.collection.QueueFullStrategy;
import com.huya.skyeye.common.util.collection.SkyeyeBufferDefaultQueue;
import com.huya.skyeye.common.util.collection.SkyeyeBufferQueue;
import com.huya.skyeye.common.util.concurrent.PrefixThreadFactory;
import com.huya.skyeye.core.concurrent.QueueConsumer;
import com.huya.skyeye.core.util.function.FixedIntGetter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class Consumer {


    public static AnnotationConfigApplicationContext context;

    public static void main(String[] args) throws InterruptedException {
        context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();

        // Obtaining a remote service proxy
        WorkerService workerService = context.getBean(WorkerService.class);
        testLua(workerService);

        //execute(workerService);
        //context.getBean(SingleService.class).execute();
        //context.getBean(PoolService.class).execute();
        //new Semaphore(0).acquire();

    }

    private static void testLua(WorkerService workerService) throws InterruptedException {
        for (int i = 0; i < 20000; i++) {
            recvQueue.add(Arrays.asList(new ReqData()));
        }
        ExecutorService exec = Executors.newCachedThreadPool(new PrefixThreadFactory("q_consume"));
        exec.submit(new QueueConsumer("test", recvQueue, workerService, 100,
                    new FixedIntGetter(1), new FixedIntGetter(50), null));
    }

    @Configuration
    @EnableConfigurationProperties
   // @EnableDubbo(scanBasePackages = "com.consumer")
    @PropertySource({"classpath:dubbo-consumer.properties","classpath:application.properties"})
    @ComponentScan(value = {"com.consumer"})
    static class ConsumerConfiguration {

    }


    private static void execute(WorkerService workerService) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            recvQueue.add(Arrays.asList(new ReqData()));
        }
        ExecutorService exec = Executors.newCachedThreadPool(new PrefixThreadFactory("q_consume"));
        for (int i = 0; i < 10; i++) {
            exec.submit(new QueueConsumer("test", recvQueue, workerService, 10,
                    new FixedIntGetter(1), new FixedIntGetter(50), null));
        }
    }


    public static SkyeyeBufferQueue<ReqData> recvQueue = new SkyeyeBufferDefaultQueue<>(
            10000, QueueFullStrategy.REMOVE_FIRST
    );
}