package goja.libs;

import goja.libs.tuple.T2;
import goja.libs.tuple.T3;
import goja.libs.tuple.T4;
import goja.libs.tuple.T5;
import goja.libs.tuple.Tuple;

public class F {


    public static Timeout Timeout(String delay) {
        return new Timeout(delay);
    }

    public static Timeout Timeout(String token, String delay) {
        return new Timeout(token, delay);
    }

    public static Timeout Timeout(long delay) {
        return new Timeout(delay);
    }

    public static Timeout Timeout(String token, long delay) {
        return new Timeout(token, delay);
    }

    public static <A> Some<A> Some(A a) {
        return new Some<A>(a);
    }

    public static <A, B> Tuple<A, B> Tuple(A a, B b) {
        return new Tuple<A, B>(a, b);
    }

    public static <A, B> T2<A, B> T2(A a, B b) {
        return new T2<A, B>(a, b);
    }

    public static <A, B, C> T3<A, B, C> T3(A a, B b, C c) {
        return new T3<A, B, C>(a, b, c);
    }

    public static <A, B, C, D> T4<A, B, C, D> T4(A a, B b, C c, D d) {
        return new T4<A, B, C, D>(a, b, c, d);
    }

    public static <A, B, C, D, E> T5<A, B, C, D, E> T5(A a, B b, C c, D d, E e) {
        return new T5<A, B, C, D, E>(a, b, c, d, e);
    }
}