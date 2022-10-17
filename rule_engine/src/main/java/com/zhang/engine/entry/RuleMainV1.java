package com.zhang.engine.entry;

import com.zhang.engine.bean.EventBean;
import com.zhang.engine.bean.RuleMatchResult;
import com.zhang.engine.functions.Json2EventBeanMapFunction;
import com.zhang.engine.functions.RuleMatchKeyedProcessFunction;
import com.zhang.engine.sources.KafkaSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Description: 规则实现V1版本
 * @since: 2022/10/15
 * @Author: zxx
 * @Date: 2022/10/15 23:09
 * 触发事件 ：K 事件属性 （p2 =v1 ）
 * 画像属性条件 ： tag87 = v2  ，tag26 = v1
 * 行为次数条件 ： 2021-06-18 ~ 当前 c 【p6 = v8，p12 = v5】 做过>=2次
*/
@Slf4j
public class RuleMainV1 {

    public static void main(String[] args) throws Exception {
        //构建env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        //读取kafka 中的用户行为日志数据
        KafkaSourceBuilder kafkaSourceBuilder = new KafkaSourceBuilder();
        DataStream<String> dss = env.addSource(kafkaSourceBuilder.build("zenniu_applog"));
        //dss.print("applog");
        //json 解析
        DataStream<EventBean> dsBean =  dss.map(new Json2EventBeanMapFunction()).filter(e->e !=null);

        //dsBean.print();
        //keyby : 按 deviceid
        KeyedStream<EventBean,String> keyedStream = dsBean.keyBy(bean ->bean.getDeviceId());

        //keyedStream.print();
        DataStream<RuleMatchResult> resultds = keyedStream.process(new RuleMatchKeyedProcessFunction());
        log.debug("规则触发结果 ........");
        resultds.print();
        env.execute();


    }

}
