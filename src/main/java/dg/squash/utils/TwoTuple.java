package dg.squash.utils;

public class TwoTuple<T1, T2> implements Tuple {


    public T2 getT2() {
        return o2;
    }

    public T1 getT1() {
        return o1;
    }

    private T2 o2;
    private T1 o1;

    public TwoTuple(T1 o1, T2 o2){
        this.o1 = o1;
        this.o2 = o2;
    }
}
