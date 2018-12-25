package com.wei840222.usi.image;

import org.apache.hadoop.hbase.HBaseConfiguration; 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.data.hadoop.hbase.HbaseTemplate; 

@Configuration 
public class HbaseConfig { 

    @Bean 
    public HbaseTemplate hbaseTemplate() { 
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create(); 
        configuration.set("hbase.zookeeper.quorum", "localhost:2181");
        configuration.set("hbase.rootdir", "file:///Users/wei/Downloads/hbase-2.1.1/data");
        configuration.set("zookeeper.znode.parent", "/hbase");
        return new HbaseTemplate(configuration); 
    } 

} 