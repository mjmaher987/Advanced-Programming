package Exceptions;

public class InvalidCardNoException extends Exception {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof InvalidCardNoException;
    }
}
