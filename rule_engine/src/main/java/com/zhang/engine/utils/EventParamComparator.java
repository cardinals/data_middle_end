package com.zhang.engine.utils;

import com.zhang.engine.bean.EventBean;
import com.zhang.engine.bean.EventParam;
import lombok.extern.slf4j.Slf4j;


import java.util.Set;

/**
 * @Description: 比较规则条件和实时事件条件是否相等
 * @since: 2022/10/16
 * @Author: zxx
 * @Date: 2022/10/16 14:24
*/
@Slf4j
public class EventParamComparator {

    public static   boolean compare(EventParam param1 ,EventParam target){
        log.debug("规则触发事件 ：" + param1 );
        log.debug("当前真实事件 ：" + target.getEventId() );
        if (param1.getEventId().equals(target.getEventId())){

            Set<String> keys = param1.getEventProperties().keySet();
            for (String key:keys){
                String targetValue = target.getEventProperties().get(key);
                if (!param1.getEventProperties().get(key).equals(targetValue)){
                    return false;
                }
            }
            return  true;

        }
        return false;
    }

    public static boolean compare(EventParam param1 , EventBean target){
        log.debug("规则触发事件 ：" + param1 );
        log.debug("当前真实事件 ：" + target.getEventId() );
        if (param1.getEventId().equalsIgnoreCase(target.getEventId())){
            log.debug("规则触发事件ID 与 当前 事件ID 相等 准备比较属性："  );
            Set<String> keys = param1.getEventProperties().keySet();
            for (String key:keys){
                String targetValue = target.getProperties().get(key);
                if (!param1.getEventProperties().get(key).equals(targetValue)){
                    log.debug("规则触发事件中的属性"+key+"="+param1.getEventProperties().get(key));
                    log.debug("真实事件中的属性"+key+"="+targetValue);

                    return false;
                }
            }
            return  true;

        }
        log.debug("规则触发事件ID 与 当前 事件ID 不相等 返回 false："  );
        return false;
    }

}
