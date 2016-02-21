package dg.squash.utils;

public class ThreeTuple<T1, T2, T3> implements Tuple {


    public T2 getT2() {
        return o2;
    }

    public T3 getT3() {
        return o3;
    }

    public T1 getT1() {
        return o1;
    }

    private T1 o1;
    private T2 o2;
    private T3 o3;

    public ThreeTuple(T1 o1, T2 o2, T3 o3){
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
    }
}
