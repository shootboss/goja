/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.libs;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <p> . </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-09-11 13:18
 * @since JDK 1.6
 */
public class Timeout extends Promise<Timeout> {

    static Timer timer = new Timer("F.Timeout", true);
    final public String token;
    final public long   delay;

    public Timeout(String delay) {
        this(Time.parseDuration(delay) * 1000);
    }

    public Timeout(String token, String delay) {
        this(token, Time.parseDuration(delay) * 1000);
    }

    public Timeout(long delay) {
        this("timeout", delay);
    }

    public Timeout(String token, long delay) {
        this.delay = delay;
        this.token = token;
        final Timeout timeout = this;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                timeout.invoke(timeout);
            }
        }, delay);
    }

    @Override
    public String toString() {
        return "Timeout(" + delay + ")";
    }

}
