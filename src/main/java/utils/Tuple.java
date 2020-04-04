package utils;

import java.io.Serializable;

public class Tuple implements Serializable {

    public static <A> Tuple1<A> of(A a) {
        return new Tuple1<>(a);
    }

    public static <A, B> Tuple2<A, B> of(A a, B b) {
        return new Tuple2<>(a, b);
    }

    public static <A, B, C> Tuple3<A, B, C> of(A a, B b, C c) {
        return new Tuple3<>(a, b, c);
    }

    public static <A, B, C, D> Tuple4<A, B, C, D> of(A a, B b, C c, D d) {
        return new Tuple4<>(a, b, c, d);
    }

    public static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(A a, B b, C c, D d, E e) {
        return new Tuple5<>(a, b, c, d, e);
    }

    public static class Tuple1<A> extends Tuple {

        private static final long serialVersionUID = -5941342767276820277L;

        protected A p1;

        public Tuple1(A p1) {
            this.p1 = p1;
        }

        public Tuple1() {
        }

        public void set1(A p1) {
            this.p1 = p1;
        }

        public A get1() {
            return p1;
        }

        @Override
        public String toString() {
            return "{" + p1 + '}';
        }

    }

    public static class Tuple2<A, B> extends Tuple1<A> {

        private static final long serialVersionUID = 7417621066133862549L;

        protected B p2;

        public Tuple2(A p1, B p2) {
            super(p1);
            this.p2 = p2;
        }

        public Tuple2() {
        }

        public void set2(B p2) {
            this.p2 = p2;
        }

        public B get2() {
            return p2;
        }

        @Override
        public String toString() {
            return "{" + p1 + ',' + p2 + '}';
        }

    }

    public static class Tuple3<A, B, C> extends Tuple2<A, B> {

        private static final long serialVersionUID = -3012868138670181302L;

        protected C p3;

        public Tuple3(A p1, B p2, C p3) {
            super(p1, p2);
            this.p3 = p3;
        }

        public Tuple3() {
        }

        public void set3(C p3) {
            this.p3 = p3;
        }

        public C get3() {
            return p3;
        }

        @Override
        public String toString() {
            return "{" + p1 + ',' + p2 + ',' + p3 + '}';
        }

    }

    public static class Tuple4<A, B, C, D> extends Tuple3<A, B, C> {

        private static final long serialVersionUID = -5524508796713266534L;

        protected D p4;

        public Tuple4(A p1, B p2, C p3, D p4) {
            super(p1, p2, p3);
            this.p4 = p4;
        }

        public Tuple4() {
        }


        public void set4(D p4) {
            this.p4 = p4;
        }

        public D get4() {
            return p4;
        }

        @Override
        public String toString() {
            return "{" + p1 + ',' + p2 + ',' + p3 + ',' + p4 + '}';
        }

    }

    public static class Tuple5<A, B, C, D, E> extends Tuple4<A, B, C, D> {

        private static final long serialVersionUID = 6990787833706681195L;

        protected E p5;

        public Tuple5(A p1, B p2, C p3, D p4, E p5) {
            super(p1, p2, p3, p4);
            this.p5 = p5;
        }

        public Tuple5() {
        }


        public void set5(E p5) {
            this.p5 = p5;
        }

        public E get5() {
            return p5;
        }

        @Override
        public String toString() {
            return "{" + p1 + ',' + p2 + ',' + p3 + ',' + p4 + ',' + p5 + '}';
        }

    }

    private static final long serialVersionUID = -3816473443618591136L;

    private Tuple() {
    }

}
