package com.zhang.engine.utils;

import com.zhang.engine.bean.EventParam;
import com.zhang.engine.bean.RuleConditions;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description: 规则模拟器
 * @since: 2022/10/16
 * @Author: zxx
 * @Date: 2022/10/16 11:33
*/
public class RuleMock {

    public static RuleConditions getRule(){
        RuleConditions ruleConditions = new RuleConditions();
        ruleConditions.setRuleId("rule_zxx");
        HashMap<String, String> map1 = new HashMap<>();
        //map1.put("p2","v1");
        EventParam eventParam = new EventParam("K", map1, 0,-1,-1);
        ruleConditions.setTriggerEvent(eventParam);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("tag87","v2");
        map2.put("tag26","v1");

        ruleConditions.setUserProfileConditions(map2);

        HashMap<String, String> map3 = new HashMap<>();
        map3.put("p6","v8");
        map3.put("p12","v5");
        EventParam eventParam3 = new EventParam("c", map3, 2,1623254400000L,Long.MAX_VALUE);

        ruleConditions.setActionSequenceConditions(Arrays.asList(eventParam3));


        return  ruleConditions;
    }
}
