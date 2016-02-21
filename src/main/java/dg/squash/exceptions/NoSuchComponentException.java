package dg.squash.exceptions;

public class NoSuchComponentException extends RuntimeException {

    public NoSuchComponentException(final Class c){
       super("ecs.Entity doesn't have" + c.getSimpleName());
    }
}
