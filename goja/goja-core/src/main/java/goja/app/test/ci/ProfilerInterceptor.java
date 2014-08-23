/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.app.test.ci;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-02-24 16:51
 * @since JDK 1.6
 */
public class ProfilerInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(ProfilerInterceptor.class);

    @Override
    public void intercept(ActionInvocation ai) {
        try {
            String actionKey = ai.getActionKey();
            Profiler.start("profiler " + actionKey + " start...");

            ai.invoke();

        } finally {
            Profiler.release();
            logger.info(Profiler.dump());
        }
    }
}
