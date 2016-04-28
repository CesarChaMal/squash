package dg.squash.exceptions;

public class DuplicateComponentException extends RuntimeException {

    public DuplicateComponentException(final Class c){
        super("ecs.Entity already has " + c.getSimpleName());
    }
}
