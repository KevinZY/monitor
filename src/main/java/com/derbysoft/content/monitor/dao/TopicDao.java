package com.derbysoft.content.monitor.dao;

import com.derbysoft.content.monitor.model.Topic;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
public interface TopicDao {
    Topic uniqueById(long id);

    List<Topic> all();

    long add(Topic topic) throws IllegalArgumentException;

    void update(Topic topic);

    Topic delete(Topic topic);

    Topic delete(long id);
}
