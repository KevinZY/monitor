package com.derbysoft.content.monitor.dao.impl;

import com.derbysoft.content.monitor.dao.TopicDao;
import com.derbysoft.content.monitor.dao.TopicMapper;
import com.derbysoft.content.monitor.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
@Repository
public class TopicDaoImpl implements TopicDao {
    @Autowired
    TopicMapper topicMapper;

    @Override
    public Topic uniqueById(long id) {
        return topicMapper.uniqueById(id);
    }

    @Override
    public List<Topic> all() {
        return topicMapper.all();
    }
}
