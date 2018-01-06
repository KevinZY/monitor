package com.derbysoft.content.monitor.dao;

import com.derbysoft.content.monitor.model.Topic;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
@MapperScan
public interface TopicDao {
    Topic uniqueById(long id);

    List<Topic> all();
}
