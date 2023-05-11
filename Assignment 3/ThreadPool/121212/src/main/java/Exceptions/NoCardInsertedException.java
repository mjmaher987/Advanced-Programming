package Exceptions;

public class NoCardInsertedException extends Exception {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NoCardInsertedException;
    }
}
