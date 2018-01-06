package com.derbysoft.content.common;

import java.util.List;

/**
 * Created by zhangyang on 2018/1/4.
 */
public interface MessageProcessor<T> {
    void process(T message);
}
