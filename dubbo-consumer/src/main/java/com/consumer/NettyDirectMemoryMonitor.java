package com.consumer;

import com.api.ReqData;
import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class NettyDirectMemoryMonitor {
    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        log.info("init => begin");
        //scheduledExecutorService.scheduleAtFixedRate(() -> directMemReporter(), 1, 1, TimeUnit.SECONDS);
        log.info("init => end");
        scheduledExecutorService.scheduleAtFixedRate(() -> putQueue(), 3000, 3000, TimeUnit.MILLISECONDS);
    }

    public void putQueue() {
        try {
            for (int i = 0; i < 100; i++) {
                Consumer.recvQueue.add(Arrays.asList(new ReqData()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void directMemReporter() {
        try {
            Field field = ReflectionUtils.findField(PlatformDependent.class, "DIRECT_MEMORY_COUNTER");
            field.setAccessible(true);
            AtomicLong directMem = (AtomicLong) field.get(PlatformDependent.class);
            log.info("DIRECT_MEMORY_COUNTER=>{}kb", directMem.get() / 1024);
        } catch (Exception e) {
            log.error("field异常" + e.getMessage());
        }
        try {
            Class c = Class.forName("java.nio.Bits");
            Field maxMemory = c.getDeclaredField("maxMemory");
            maxMemory.setAccessible(true);
            Field reservedMemory = c.getDeclaredField("reservedMemory");
            reservedMemory.setAccessible(true);
            synchronized (c) {
                log.info("maxMemoryValue => {},reservedMemoryValue=>{}", maxMemory.get(null), reservedMemory.get(null));
            }
        } catch (Exception e) {
            log.error("Bits异常" + e.getMessage());
        }
    }
}
 
