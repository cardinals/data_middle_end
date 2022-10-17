package com.zhang.engine.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description: 规则条件封装对象，封装一个规则中的所有判断条件
 * @since: 2022/10/16
 * @Author: zxx
 * @Date: 2022/10/16 10:17
*/
@Data
public class RuleConditions {
    //ruleid
    private String ruleId;
    // 触发事件
    private  EventParam triggerEvent;
    //画像属性条件
    private Map<String, String> userProfileConditions;

    //行为次数条件
    private List<EventParam> actionCountConditions;

    //行为序列条件
    private List<EventParam>  actionSequenceConditions;
}
