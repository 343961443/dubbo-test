package com.api;

import com.huya.skyeye.core.concurrent.QueueTimeBean;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import sun.awt.EventQueueItem;

import java.awt.*;
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
    private int[] data;

}
