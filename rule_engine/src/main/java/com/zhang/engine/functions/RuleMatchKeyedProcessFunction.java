package com.zhang.engine.functions;

import com.zhang.engine.bean.EventBean;
import com.zhang.engine.bean.RuleConditions;
import com.zhang.engine.bean.RuleMatchResult;
import com.zhang.engine.queryservice.HbaseQueryServiceImpl;
import com.zhang.engine.utils.ConnectionUtils;
import com.zhang.engine.utils.EventParamComparator;
import com.zhang.engine.utils.RuleMock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;

import java.util.Map;
import java.util.Set;

/**
 * @Description: 规则匹配核心函数
 * @since: 2022/10/16
 * @Author: zxx
 * @Date: 2022/10/16 0:38
 * 规则：
 * 触发事件 ：K 事件属性 （p2 =v1 ）
 * 画像属性条件 ： tag87 = v2  ，tag26 = v1
 * 行为次数条件 ： 2021-06-18 ~ 当前 c 【p6 = v8，p12 = v5】 做过>=2次 *
*/
@Slf4j
public class RuleMatchKeyedProcessFunction extends KeyedProcessFunction<String, EventBean, RuleMatchResult> {
    Connection hbaseConn ;
    HbaseQueryServiceImpl hbaseQueryService;
    @Override
    public void open(Configuration parameters) throws Exception {

        hbaseConn = ConnectionUtils.getHbaseConn();
        hbaseQueryService = new HbaseQueryServiceImpl(hbaseConn);
    }

    @Override
    public void processElement(EventBean event, Context context, Collector<RuleMatchResult> out) throws Exception {
        log.debug("收到一条事件数据 ： eventID = " + event.getDeviceId());

        //获取规则
        RuleConditions rule = RuleMock.getRule();

        //判断当前事件是否是规则定义的触发事件条件
        if(!EventParamComparator.compare(rule.getTriggerEvent(),event))  return;


        // 计算画像条件是否满足
        Map<String,String> userProfileConditions = rule.getUserProfileConditions();
        boolean profileQueryResult = false ;
        if (rule.getUserProfileConditions()!=null){
            hbaseQueryService.queryProfileCondition(event.getDeviceId(),userProfileConditions);
            //如果画像条件结果为false，则整个规则计算结束
            if (!profileQueryResult) return;

        }

        //TODO 行为条件是否满足

        //TODO 行为序列条件是否满足


        //TODO 模拟随机命中
/*        if (RandomUtils.nextInt(1,10)%2==0){
            RuleMatchResult ruleMatchResult = new RuleMatchResult(event.getDeviceId(), rule.getRuleId(), event.getTimeStamp(), System.currentTimeMillis());
            out.collect(ruleMatchResult);
        }*/
        RuleMatchResult ruleMatchResult = new RuleMatchResult(event.getDeviceId(), rule.getRuleId(), event.getTimeStamp(), System.currentTimeMillis());
        log.debug("命中规则为："+ruleMatchResult.toString());
        out.collect(ruleMatchResult);

    }
}
