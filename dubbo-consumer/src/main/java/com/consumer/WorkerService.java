package com.consumer;

import com.api.IDemoService;
import com.api.ReqData;
import com.consumer.config.RedisConfig;
import com.consumer.util.LuaUtils;
import com.huya.skyeye.common.util.concurrent.PrefixThreadFactory;
import com.huya.skyeye.core.concurrent.handler.ConsumeWorker;
import com.huya.skyeye.core.redis.RedisTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhaoshuai
 * @Date: 2021/7/16
 */
@Slf4j
@Component
public class WorkerService extends ConsumeWorker<ReqData> {

   /* @Reference(timeout = 2000)
    IDemoService demoService;
*/
    private ExecutorService exec = null;

    private AtomicInteger errorCount = new AtomicInteger();

    private AtomicInteger successCount = new AtomicInteger();

    public AtomicInteger getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(AtomicInteger errorCount) {
        this.errorCount = errorCount;
    }

    @Autowired
    private RedisConfig redisConfig;

    @PostConstruct
    public void init() {
        int p = Runtime.getRuntime().availableProcessors();
        int coreSize = (int) Math.max(1, 1);
        log.info("initializing callback thread pool,  availableProcessors: {}, coreSize: {}", p, coreSize);
       /* exec = new ThreadPoolExecutor(coreSize, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new PrefixThreadFactory("cb"));*/
    }

    @Override
    public void consume(Collection<ReqData> collection) {
        String redisListKey = "list_1";
        String redisKey = "object_1";
        RedisTemplate jobRedisTemplate = redisConfig.getByKey("jobRedisTemplate");
        for (ReqData reqData : collection) {
            long systemCur = System.currentTimeMillis();

            try {
                Object setnxAndExpireAndSmembers = jobRedisTemplate.eval(LuaUtils.getScript("setnxAndExpireAndSmembers"),
                        4, redisListKey, redisKey, 10 + "", String.valueOf(reqData.getData()));
                List<String> stringList = new ArrayList<>();
                for (Object m : (List)setnxAndExpireAndSmembers) {
                    stringList.add(m.toString());
                }
                for (Object m : (List)setnxAndExpireAndSmembers) {
                    stringList.add(m.toString());
                }
                for (Object m : (List)setnxAndExpireAndSmembers) {
                    stringList.add(m.toString());
                }
                for (Object m : (List)setnxAndExpireAndSmembers) {
                    stringList.add(m.toString());
                }
                jobRedisTemplate.getMore(stringList);
                log.info("id={},successCount={}", reqData.getInteger(), successCount.getAndIncrement());
            } catch (Exception e) {
                log.error("id={},errorNum={}", reqData.getInteger(),errorCount.getAndIncrement(), e);
            } finally {

            }


        }

    }

    @Override
    public String getQueueName() {
        return "testworker";
    }
}
