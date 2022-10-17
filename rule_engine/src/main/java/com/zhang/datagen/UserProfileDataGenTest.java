package com.zhang.datagen;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * @author 涛哥
 * @nick_name "deep as the sea"
 * @contact qq:657270652 wx:doit_edu
 * @site www.doitedu.cn
 * @date 2021-03-27
 * @desc 用户画像数据模拟器
 * <p>
 * deviceid,k1=v1
 * <p>
 * hbase中需要先创建好画像标签表
 * [root@hdp01 ~]# hbase shell
 * hbase> create 'yinew_profile','f'
 */
public class UserProfileDataGenTest {
    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();
       //conf.set("hbase.zookeeper.quorum", "aidb:2181");

        //conf.set("hbase.zookeeper.quorum", "k8s-node02:2181");
        //conf.set("hbase.master", "k8s-node02:16000");

        conf.set("hbase.zookeeper.quorum","aibu01:2181,aibu02:2181,aibu03:2181,aidb:2181");
        //conf.set("hbase.zookeeper.property.clientPort","2181");
        //conf.set("hbase.master", "aidb:16010");
        conf.set("zookeeper.znode.parent","/hbase-unsecure");
        //conf.setInt("hbase.regionserver.port", 16020);
        conf.setInt("hbase.rpc.timeout",3000);
        conf.setInt("hbase.client.operation.timeout",3000);
        conf.setInt("hbase.client.scanner.timeout.period",3000);



        Connection conn = ConnectionFactory.createConnection(conf);
        String tableName = "zenniu_profile";
        Table table = conn.getTable(TableName.valueOf("zenniu_profile"));
        Admin admin = conn.getAdmin();
        if (admin.tableExists(TableName.valueOf("zenniu_profile"))) {
            System.out.println("该表已存在");
        }

    }
}
