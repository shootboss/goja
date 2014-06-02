/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package com.github.sog.plugin.redis;

import japp.Logger;
import redis.clients.jedis.Tuple;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TopicConsumer {

    private TopicNest topic;
    private TopicNest subscriber;
    private String    id;
    private int interval = 1000;

    public TopicConsumer(final String id, final String topic) {
        this.topic = new TopicNest("topic:" + topic);
        this.subscriber = new TopicNest(this.topic.cat("subscribers").key());
        this.id = id;
    }

    public TopicConsumer interval(int interval) {
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void consume(JedisMessage callback) {
        while (true) {
            Serializable message = readUntilEnd();
            if (message != null) {
                callback.onMessage(message);
                goNext();
            } else {
                waitForMessages();
            }
        }
    }

    public <T extends Serializable> T consume() {
        T message = readUntilEnd();
        goNext();
        return message;
    }

    private <T extends Serializable> T readUntilEnd() {
        if (unreadMessages() > 0) {
            Serializable message = read();
            return (T) message;
        }
        return null;
    }

    private void goNext() {
        JedisKit.zincrby(subscriber.key(), 1, id);
    }

    private int getLastReadMessage() {
        Double lastMessageRead = JedisKit.zscore(subscriber.key(), id);
        if (lastMessageRead == null) {
            Set<Tuple> zrangeWithScores = JedisKit.zrangeWithScores(subscriber.key(), 0, 1);
            if (zrangeWithScores.iterator().hasNext()) {
                Tuple next = zrangeWithScores.iterator().next();
                Integer lowest = (int) next.getScore() - 1;
                JedisKit.zadd(subscriber.key(), lowest, id);
                return lowest;
            } else {
                return 0;
            }
        }
        return lastMessageRead.intValue();
    }

    private int getTopicSize() {
        String stopicSize = JedisKit.get(topic.key());
        int topicSize = 0;
        if (stopicSize != null) {
            topicSize = Integer.valueOf(stopicSize);
        }
        return topicSize;
    }

    public <T extends Serializable> T read() {
        int lastReadMessage = getLastReadMessage();
        Logger.debug("lastReadMessage " + lastReadMessage);
        String key = topic.cat("message").cat(lastReadMessage + 1).key();
        T message = JedisKit.get(key);
        Logger.info("consume the message," + "key[" + key + "],value[" + message + "]");
        return message;
    }

    public int unreadMessages() {
        return getTopicSize() - getLastReadMessage();
    }
}
