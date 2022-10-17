package com.zhang.engine.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 规则匹配计算结果
 * @since: 2022/10/16
 * @Author: zxx
 * @Date: 2022/10/16 0:40
*/
@Data
@AllArgsConstructor
@ToString
public class RuleMatchResult {
    String deviceId;
    String ruleId;
    //触发事件时间
    long trigEventTimestamp;
    //匹配时间
    long matchTimestamp;
}
