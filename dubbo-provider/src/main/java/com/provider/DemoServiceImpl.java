package com.provider;

import com.api.IDemoService;
import com.api.ReqData;
import org.apache.dubbo.config.annotation.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service(timeout = 3 * 1000, retries = 0)
public class DemoServiceImpl implements IDemoService {

    @Override
    public int[] getData(final ReqData data) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(dateFormat.format(new Date()) + "get Data.");
        try {
            data.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
