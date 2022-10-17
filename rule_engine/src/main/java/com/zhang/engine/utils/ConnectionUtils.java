package com.zhang.engine.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Description: 各类连接创建工具
 * @since: 2022/10/16
 * @Author: zxx
 * @Date: 2022/10/16 15:48
*/
@Slf4j
public class ConnectionUtils {
    static  Config config = ConfigFactory.load();

    public static Connection getHbaseConn() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        //conf.set("hbase.zookeeper.quorum", "aidb:2181");

        //conf.set("hbase.zookeeper.quorum", "k8s-node02:2181");
        //conf.set("hbase.master", "k8s-node02:16000");

        conf.set("hbase.zookeeper.quorum","aibu01:2181,aibu02:2181,aibu03:2181,aidb:2181");
        //conf.set("hbase.zookeeper.property.clientPort","2181");
        //conf.set("hbase.master", "aidb:16000");
        conf.set("zookeeper.znode.parent","/hbase-unsecure");
        //conf.setInt("hbase.regionserver.port", 16020);
        //conf.set("hbase.master", "k8s-node02:16010");
        //conf.set("zookeeper.znode.parent","/hbase");
        conf.setInt("hbase.rpc.timeout",10000);
        conf.setInt("hbase.client.operation.timeout",10000);
        conf.setInt("hbase.client.scanner.timeout.period",10000);

        Connection conn = ConnectionFactory.createConnection(conf);

        log.debug("hbase连接创建c成功......");
        return  conn;
    }

}
