package com.wei840222.usi.image.bean;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@Configuration
public class HBaseTemplateBean {
    @Value("${spring.hbase.zookeeper.quorum}")
    private String hbaseZookeeperQuorum;

    @Value("${spring.hbase.zookeeper.znode.parent}")
    private String hbaseZookeeperZnodeParent;
    
    @Value("${spring.hbase.rootdir}")
    private String hbaseRootdir;

    @Bean 
    public HbaseTemplate getHBaseTemplate() { 
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create(); 
        configuration.set("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
        configuration.set("hbase.rootdir", hbaseRootdir);
        configuration.set("zookeeper.znode.parent", hbaseZookeeperZnodeParent);
        return new HbaseTemplate(configuration); 
    } 

} 