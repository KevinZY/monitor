package com.derbysoft.content.monitor.dao;

import com.derbysoft.content.monitor.model.Topic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/6.
 */
public interface TopicMapper {

    @Select("SELECT * FROM topic WHERE id = #{id}")
    Topic uniqueById(@Param("id") long id);

    @Select("SELECT * FROM topic")
    List<Topic> all();
}
