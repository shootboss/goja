/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.libs;

import com.google.common.collect.Lists;
import goja.libs.either.E3;
import goja.libs.either.E4;
import goja.libs.either.E5;
import goja.libs.either.Either;
import goja.libs.tuple.T3;
import goja.libs.tuple.T4;
import goja.libs.tuple.T5;
import goja.libs.tuple.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <p> . </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-09-11 13:19
 * @since JDK 1.6
 */
public class Promise<V> implements Future<V>, Action<V> {

    final CountDownLatch taskLock = new CountDownLatch(1);

    boolean invoked   = false;

    List<Action<Promise<V>>> callbacks = Lists.newArrayList();

    V         result    = null;
    Throwable exception = null;

    public static <T> Promise<List<T>> waitAll(final Promise<T>... promises) {
        return waitAll(Arrays.asList(promises));
    }

    public static <T> Promise<List<T>> waitAll(final Collection<Promise<T>> promises) {
        final CountDownLatch waitAllLock = new CountDownLatch(promises.size());
        final Promise<List<T>> result = new Promise<List<T>>() {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                boolean r = true;
                for (Promise<T> f : promises) {
                    r = r & f.cancel(mayInterruptIfRunning);
                }
                return r;
            }

            @Override
            public boolean isCancelled() {
                boolean r = true;
                for (Promise<T> f : promises) {
                    r = r & f.isCancelled();
                }
                return r;
            }

            @Override
            public boolean isDone() {
                boolean r = true;
                for (Promise<T> f : promises) {
                    r = r & f.isDone();
                }
                return r;
            }

            @Override
            public List<T> get() throws InterruptedException, ExecutionException {
                waitAllLock.await();
                List<T> r = new ArrayList<T>();
                for (Promise<T> f : promises) {
                    r.add(f.get());
                }
                return r;
            }

            @Override
            public List<T> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                waitAllLock.await(timeout, unit);
                return get();
            }
        };
        final Action<Promise<T>> action = new Action<Promise<T>>() {

            public void invoke(Promise<T> completed) {
                waitAllLock.countDown();
                if (waitAllLock.getCount() == 0) {
                    try {
                        result.invoke(result.get());
                    } catch (Exception e) {
                        result.invokeWithException(e);
                    }
                }
            }
        };
        for (Promise<T> f : promises) {
            f.onRedeem(action);
        }
        if (promises.isEmpty()) {
            result.invoke(Collections.<T>emptyList());
        }
        return result;
    }

    public static <A, B> Promise<Tuple<A, B>> wait2(Promise<A> tA, Promise<B> tB) {
        final Promise<Tuple<A, B>> result = new Promise<Tuple<A, B>>();
        final Promise<List<Object>> t = waitAll(new Promise[]{tA, tB});
        t.onRedeem(new Action<Promise<List<Object>>>() {

            public void invoke(Promise<List<Object>> completed) {
                List<Object> values = completed.getOrNull();
                if (values != null) {
                    result.invoke(new Tuple(values.get(0), values.get(1)));
                } else {
                    result.invokeWithException(completed.exception);
                }
            }
        });
        return result;
    }

    public static <A, B, C> Promise<T3<A, B, C>> wait3(Promise<A> tA, Promise<B> tB, Promise<C> tC) {
        final Promise<T3<A, B, C>> result = new Promise<T3<A, B, C>>();
        final Promise<List<Object>> t = waitAll(new Promise[]{tA, tB, tC});
        t.onRedeem(new Action<Promise<List<Object>>>() {

            public void invoke(Promise<List<Object>> completed) {
                List<Object> values = completed.getOrNull();
                if (values != null) {
                    result.invoke(new T3((A) values.get(0), (B) values.get(1), (C) values.get(2)));
                } else {
                    result.invokeWithException(completed.exception);
                }
            }
        });
        return result;
    }

    public static <A, B, C, D> Promise<T4<A, B, C, D>> wait4(Promise<A> tA, Promise<B> tB, Promise<C> tC, Promise<D> tD) {
        final Promise<T4<A, B, C, D>> result = new Promise<T4<A, B, C, D>>();
        final Promise<List<Object>> t = waitAll(new Promise[]{tA, tB, tC, tD});
        t.onRedeem(new Action<Promise<List<Object>>>() {

            public void invoke(Promise<List<Object>> completed) {
                List<Object> values = completed.getOrNull();
                if (values != null) {
                    result.invoke(new T4((A) values.get(0), (B) values.get(1), (C) values.get(2), (D) values.get(3)));
                } else {
                    result.invokeWithException(completed.exception);
                }
            }
        });
        return result;
    }

    public static <A, B, C, D, E> Promise<T5<A, B, C, D, E>> wait5(Promise<A> tA, Promise<B> tB, Promise<C> tC, Promise<D> tD, Promise<E> tE) {
        final Promise<T5<A, B, C, D, E>> result = new Promise<T5<A, B, C, D, E>>();
        final Promise<List<Object>> t = waitAll(new Promise[]{tA, tB, tC, tD, tE});
        t.onRedeem(new Action<Promise<List<Object>>>() {

            public void invoke(Promise<List<Object>> completed) {
                List<Object> values = completed.getOrNull();
                if (values != null) {
                    result.invoke(new T5(values.get(0), (B) values.get(1), (C) values.get(2), (D) values.get(3), (E) values.get(4)));
                } else {
                    result.invokeWithException(completed.exception);
                }
            }
        });
        return result;
    }

    private static Promise<Tuple<Integer, Promise<Object>>> waitEitherInternal(final Promise<?>... futures) {
        final Promise<Tuple<Integer, Promise<Object>>> result = new Promise<Tuple<Integer, Promise<Object>>>();
        for (int i = 0; i < futures.length; i++) {
            final int index = i + 1;
            ((Promise<Object>) futures[i]).onRedeem(new Action<Promise<Object>>() {

                public void invoke(Promise<Object> completed) {
                    result.invoke(new Tuple(index, completed));
                }
            });
        }
        return result;
    }

    public static <A, B> Promise<Either<A, B>> waitEither(final Promise<A> tA, final Promise<B> tB) {
        final Promise<Either<A, B>> result = new Promise<Either<A, B>>();
        final Promise<Tuple<Integer, Promise<Object>>> t = waitEitherInternal(tA, tB);

        t.onRedeem(new Action<Promise<Tuple<Integer, Promise<Object>>>>() {

            public void invoke(Promise<Tuple<Integer, Promise<Object>>> completed) {
                Tuple<Integer, Promise<Object>> value = completed.getOrNull();
                switch (value.a) {
                    case 1:
                        result.invoke(Either.<A, B>_1((A) value.b.getOrNull()));
                        break;
                    case 2:
                        result.invoke(Either.<A, B>_2((B) value.b.getOrNull()));
                        break;
                }

            }
        });

        return result;
    }

    public static <A, B, C> Promise<E3<A, B, C>> waitEither(final Promise<A> tA, final Promise<B> tB, final Promise<C> tC) {
        final Promise<E3<A, B, C>> result = new Promise<E3<A, B, C>>();
        final Promise<Tuple<Integer, Promise<Object>>> t = waitEitherInternal(tA, tB, tC);

        t.onRedeem(new Action<Promise<Tuple<Integer, Promise<Object>>>>() {

            public void invoke(Promise<Tuple<Integer, Promise<Object>>> completed) {
                Tuple<Integer, Promise<Object>> value = completed.getOrNull();
                switch (value.a) {
                    case 1:
                        result.invoke(E3.<A, B, C>_1((A) value.b.getOrNull()));
                        break;
                    case 2:
                        result.invoke(E3.<A, B, C>_2((B) value.b.getOrNull()));
                        break;
                    case 3:
                        result.invoke(E3.<A, B, C>_3((C) value.b.getOrNull()));
                        break;
                }

            }
        });

        return result;
    }

    public static <A, B, C, D> Promise<E4<A, B, C, D>> waitEither(final Promise<A> tA, final Promise<B> tB, final Promise<C> tC, final Promise<D> tD) {
        final Promise<E4<A, B, C, D>> result = new Promise<E4<A, B, C, D>>();
        final Promise<Tuple<Integer, Promise<Object>>> t = waitEitherInternal(tA, tB, tC, tD);

        t.onRedeem(new Action<Promise<Tuple<Integer, Promise<Object>>>>() {

            public void invoke(Promise<Tuple<Integer, Promise<Object>>> completed) {
                Tuple<Integer, Promise<Object>> value = completed.getOrNull();
                switch (value.a) {
                    case 1:
                        result.invoke(E4.<A, B, C, D>_1((A) value.b.getOrNull()));
                        break;
                    case 2:
                        result.invoke(E4.<A, B, C, D>_2((B) value.b.getOrNull()));
                        break;
                    case 3:
                        result.invoke(E4.<A, B, C, D>_3((C) value.b.getOrNull()));
                        break;
                    case 4:
                        result.invoke(E4.<A, B, C, D>_4((D) value.b.getOrNull()));
                        break;
                }

            }
        });

        return result;
    }

    public static <A, B, C, D, E> Promise<E5<A, B, C, D, E>> waitEither(final Promise<A> tA, final Promise<B> tB, final Promise<C> tC, final Promise<D> tD, final Promise<E> tE) {
        final Promise<E5<A, B, C, D, E>> result = new Promise<E5<A, B, C, D, E>>();
        final Promise<Tuple<Integer, Promise<Object>>> t = waitEitherInternal(tA, tB, tC, tD, tE);

        t.onRedeem(new Action<Promise<Tuple<Integer, Promise<Object>>>>() {

            public void invoke(Promise<Tuple<Integer, Promise<Object>>> completed) {
                Tuple<Integer, Promise<Object>> value = completed.getOrNull();
                switch (value.a) {
                    case 1:
                        result.invoke(E5.<A, B, C, D, E>_1((A) value.b.getOrNull()));
                        break;
                    case 2:
                        result.invoke(E5.<A, B, C, D, E>_2((B) value.b.getOrNull()));
                        break;
                    case 3:
                        result.invoke(E5.<A, B, C, D, E>_3((C) value.b.getOrNull()));
                        break;
                    case 4:
                        result.invoke(E5.<A, B, C, D, E>_4((D) value.b.getOrNull()));
                        break;
                    case 5:
                        result.invoke(E5.<A, B, C, D, E>_5((E) value.b.getOrNull()));
                        break;

                }

            }
        });

        return result;
    }

    public static <T> Promise<T> waitAny(final Promise<T>... futures) {
        final Promise<T> result = new Promise<T>();

        final Action<Promise<T>> action = new Action<Promise<T>>() {

            public void invoke(Promise<T> completed) {
                synchronized (this) {
                    if (result.isDone()) {
                        return;
                    }
                }
                T resultOrNull = completed.getOrNull();
                if (resultOrNull != null) {
                    result.invoke(resultOrNull);
                } else {
                    result.invokeWithException(completed.exception);
                }
            }
        };

        for (Promise<T> f : futures) {
            f.onRedeem(action);
        }

        return result;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return invoked;
    }

    public V getOrNull() {
        return result;
    }

    public V get() throws InterruptedException, ExecutionException {
        taskLock.await();
        if (exception != null) {
            // The result of the promise is an exception - throw it
            throw new ExecutionException(exception);
        }
        return result;
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        taskLock.await(timeout, unit);
        if (exception != null) {
            // The result of the promise is an exception - throw it
            throw new ExecutionException(exception);
        }
        return result;
    }

    public void invoke(V result) {
        invokeWithResultOrException(result, null);
    }

    public void invokeWithException(Throwable t) {
        invokeWithResultOrException(null, t);
    }

    protected void invokeWithResultOrException(V result, Throwable t) {
        synchronized (this) {
            if (!invoked) {
                invoked = true;
                this.result = result;
                this.exception = t;
                taskLock.countDown();
            } else {
                return;
            }
        }
        for (Action<Promise<V>> callback : callbacks) {
            callback.invoke(this);
        }
    }

    public void onRedeem(Action<Promise<V>> callback) {
        synchronized (this) {
            if (!invoked) {
                callbacks.add(callback);
            }
        }
        if (invoked) {
            callback.invoke(this);
        }
    }
}
