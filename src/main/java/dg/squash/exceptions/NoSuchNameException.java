package dg.squash.exceptions;

public class NoSuchNameException extends RuntimeException {
    public NoSuchNameException(String name) {
        super("There is no entity with name: " + name);
    }
}
