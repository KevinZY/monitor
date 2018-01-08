package com.derbysoft.content.monitor.dao.impl;

import com.derbysoft.content.monitor.dao.TopicDao;
import com.derbysoft.content.monitor.dao.mapper.TopicMapper;
import com.derbysoft.content.monitor.model.Topic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhangyang on 2018/1/6.
 */
@Repository
public class TopicDaoImpl implements TopicDao {
    private final
    TopicMapper topicMapper;

    @Autowired
    public TopicDaoImpl(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    @Override
    public Topic uniqueById(long id) {
        return topicMapper.uniqueById(id);
    }

    @Override
    public List<Topic> all() {
        return topicMapper.selectAll();
    }

    @Override
    public long add(Topic topic) throws IllegalArgumentException {
        try {
            checkNotNull(topic, "add topic failed, empty topic entity");
            checkArgument(StringUtils.isNotBlank(topic.getName()), "add topic failed, empty topic name");
            topicMapper.insert(topic);
            return topic.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Topic topic) {

    }

    @Override
    public Topic delete(Topic topic) {
        return null;
    }

    @Override
    public Topic delete(long id) {
        return null;
    }
}
