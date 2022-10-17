package com.zhang.engine.queryservice;


import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HbaseQueryServiceImpl implements  QueryService  {
    Connection hbaseConn;
    public HbaseQueryServiceImpl(Connection conn){
        this.hbaseConn = conn;
    }

    public  boolean queryProfileCondition(String deviceId, Map<String, String> profileCondition) throws IOException {
        Table table = hbaseConn.getTable(TableName.valueOf("zenniu_profile"));
        //设置hbase 的查询条件 rowkey
        Get get = new Get(deviceId.getBytes());
        Set<String> tags = profileCondition.keySet();
        //设置要查询的family 和 qualifier （标签名）
        for (String tag : tags) {
            get.addColumn("f".getBytes(), tag.getBytes());
        }
        // 然后请求Hbase查询
        Result result = table.get(get);
        for (String tag : tags) {
            byte[] value = result.getValue("f".getBytes(), tag.getBytes());
            log.debug("查询到一个标签： " + tag + " = " + new String(value));
            if (!profileCondition.get(tag).equals(new String(value))) return false;
        }
        return true;
    }
}
