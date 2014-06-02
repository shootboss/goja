/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package com.github.sog.plugin.redis;

import com.github.sog.config.StringPool;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class QueueConsumer {
    static final String PROCESSINGLIST = StringPool.DASH + "processing";
    static final String PREFIX         = "queue" + StringPool.DASH;
    static Map<String, QueueConsumer> existingConsumers = Maps.newHashMap();
    int interval = 1000;

    boolean start = false;

    String queueName;

    private QueueConsumer(String queueName) {
        this.queueName = queueName;
    }

    public static QueueConsumer create(String queueName) {
        QueueConsumer consumer = existingConsumers.get(queueName);
        if (consumer == null) {
            consumer = new QueueConsumer(queueName);
        } else {
            throw new IllegalArgumentException("The consumer named " + queueName + " already exists");
        }
        return consumer;
    }

    public QueueConsumer interval(int interval) {
        this.interval = interval;
        return this;
    }

    private void waitForMessages() {
        try {
            TimeUnit.MILLISECONDS.sleep(interval);
        } catch (InterruptedException e) {
            // TODO
            e.printStackTrace();
        }
    }

    public <T extends Serializable> T consume() {
        return JedisKit.rpoplpush(queueName(), processingListName());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void consume(final JedisMessage callback) {
        if (start) {
            throw new RuntimeException("The Consumer named " + queueName + " is working");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    JedisKit.rpoplpush(queueName(), processingListName());
                    List<Serializable> message = JedisKit.lrange(processingListName(), -1, -1);
                    if (message.isEmpty()) {
                        waitForMessages();
                    } else {
                        callback.onMessage(message.get(0));
                        JedisKit.rpop(processingListName());
                    }
                }

            }
        }).start();
    }

    private String processingListName() {
        return PREFIX + queueName + PROCESSINGLIST;
    }

    private String queueName() {
        return PREFIX + queueName;
    }

}
