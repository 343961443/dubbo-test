package com.api;

import com.huya.skyeye.core.concurrent.QueueTimeBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: zhaoshuai
 * @Date: 2021/7/16
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ReqData extends QueueTimeBean implements Serializable {
    private int integer = getNext();
    private int[] data;

    private static volatile int value = 1;

    private static int getNext() {
        return value++;
    }

}
