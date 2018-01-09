package com.derbysoft.content.monitor.mapper;

import com.derbysoft.content.monitor.model.Topic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
public interface TopicMapper {

    @Select("SELECT * FROM topic WHERE id=#{id}")
    Topic uniqueById(@Param("id") long id);

    @Select("SELECT * FROM topic")
    List<Topic> all();

    long add(@Param("topic") Topic topic);

    void update(Topic topic);

    Topic delete(Topic topic);

    Topic delete(long id);
}
