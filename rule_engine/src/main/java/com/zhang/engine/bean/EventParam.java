package com.zhang.engine.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @Description: 规则条件中表示一个事件的封装类
 * @since: 2022/10/16
 * @Author: zxx
 * @Date: 2022/10/16 11:25
*/
@Data
@AllArgsConstructor
@ToString
public class EventParam {

    private String eventId;
    private Map<String, String>  eventProperties;
    //次数
    private  int countThreshHold;
    private long timeRangeStart;
    private long timeRangeEnd;
}
