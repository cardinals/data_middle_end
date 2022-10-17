package com.zhang.engine.functions;

import com.alibaba.fastjson.JSON;
import com.zhang.engine.bean.EventBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @Description: 原始行为日志json 2 logBean对象
 * @since: 2022/10/15
 * @Author: zxx
 * @Date: 2022/10/15 23:54
*/
@Slf4j
public class Json2EventBeanMapFunction implements MapFunction<String, EventBean> {
    @Override
    public EventBean map(String value) throws Exception {
        EventBean eventBean = null;
        try{
            eventBean = JSON.parseObject(value,EventBean.class);
        }catch (Exception e){

        }

        return eventBean;
    }
}
