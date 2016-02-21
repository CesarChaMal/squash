package dg.squash.utils;

public class OneTuple<T1> implements Tuple {

    private T1 o;

    public T1 getT1(){
        return o;
    }

    public OneTuple(T1 o) {
        this.o = o;
    }
}
