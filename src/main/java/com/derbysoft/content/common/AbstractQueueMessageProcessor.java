package com.derbysoft.content.common;

import com.google.common.collect.Queues;

import java.util.concurrent.BlockingQueue;

/**
 * Created by zhangyang on 2018/1/5.
 */
public abstract class AbstractQueueMessageProcessor<T> implements MessageProcessor<T>, Runnable {
    protected BlockingQueue<T> messageQueue = Queues.newLinkedBlockingQueue(200);

    public boolean addMessageToQueue(T message) {
        return messageQueue.offer(message);
    }
}
