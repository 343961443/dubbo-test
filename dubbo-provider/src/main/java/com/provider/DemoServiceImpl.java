package com.provider;

import com.api.IDemoService;
import com.api.ReqData;
import org.apache.dubbo.config.annotation.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service(timeout = 2 * 1000, retries = 0)
public class DemoServiceImpl implements IDemoService {

  @Override
  public int[] getData(ReqData data) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    System.out.println(dateFormat.format(new Date()) + "get Data.");
    return null;
  }
}
