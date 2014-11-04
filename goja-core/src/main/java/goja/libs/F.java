package goja.libs;


import goja.tuples.Pair;
import goja.tuples.Quartet;
import goja.tuples.Quintet;
import goja.tuples.Triplet;

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

    public static <A, B> Pair<A, B> Pair(A a, B b) {
        return Pair.with(a, b);
    }

    public static <A, B, C> Triplet<A, B, C> Triplet(A a, B b, C c) {
        return Triplet.with(a, b, c);
    }

    public static <A, B, C, D> Quartet<A, B, C, D> Quartet(A a, B b, C c, D d) {
        return Quartet.with(a, b, c, d);
    }

    public static <A, B, C, D, E> Quintet<A, B, C, D, E> Quintet(A a, B b, C c, D d, E e) {
        return Quintet.with(a, b, c, d, e);
    }
}