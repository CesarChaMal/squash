package dg.squash.utils;

/**
 * Class for holding two objects.
 *
 * @param <T1> first object
 * @param <T2> second object
 */
public class Pair<T1, T2> {

    private T2 o2;
    private T1 o1;

    /**
     * Constructor
     *
     * @param o1 first object
     * @param o2 second object
     */
    public Pair(T1 o1, T2 o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    /**
     * @return second object
     */
    public T2 getT2() {
        return o2;
    }

    /**
     * @return first object
     */
    public T1 getT1() {
        return o1;
    }
}
