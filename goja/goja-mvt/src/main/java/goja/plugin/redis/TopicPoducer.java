/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */
package goja.plugin.redis;

import goja.Logger;
import goja.kits.SerializableKit;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class TopicPoducer {

    private TopicNest topic;
    private TopicNest subscriber;

    public TopicPoducer(String topic) {
        this.topic = new TopicNest("topic:" + topic);
        this.subscriber = new TopicNest(this.topic.cat("subscribers").key());
    }

    public void publish(Serializable message) {
        publish(message, 0);
    }

    protected Integer getNextMessageId() {
        String slastMessageId = JedisKit.get(topic.key());
        Integer lastMessageId = 0;
        if (slastMessageId != null) {
            lastMessageId = Integer.parseInt(slastMessageId);
        }
        lastMessageId++;
        Logger.debug(topic.key() + " nextMessageId " + lastMessageId);
        return lastMessageId;
    }

    /** 删除最近消费的消息 */
    public void clean() {
        Set<Tuple> zrangeWithScores = JedisKit.zrangeWithScores(subscriber.key(), 0, 1);
        Tuple next = zrangeWithScores.iterator().next();
        Integer lowest = (int) next.getScore();
        String key = topic.cat("message").cat(lowest).key();
        Logger.debug("clean key " + key);
        JedisKit.del(key);
    }

    /**
     * @param message menssage
     * @param seconds expiry time
     */
    public void publish(final Serializable message, final int seconds) {
        List<Object> exec = null;
        do {
            JedisKit.watch(topic.key());
            exec = JedisKit.tx(new JedisAtom() {
                @Override
                public void action(Transaction trans) {
                    Integer nextMessageId = getNextMessageId();
                    String msgKey = topic.cat("message").cat(nextMessageId).key();
                    if (message instanceof String) {
                        trans.set(msgKey, (String) message);
                    } else {
                        trans.set(msgKey.getBytes(), SerializableKit.toByteArray(message));
                    }
                    Logger.info("produce a message,key[" + msgKey + "],message[" + message + "]");
                    trans.set(topic.key(), nextMessageId.toString());
                    if (seconds > 0) {
                        trans.expire(msgKey, seconds);
                    }
                }
            });

        } while (exec == null);
    }
}
