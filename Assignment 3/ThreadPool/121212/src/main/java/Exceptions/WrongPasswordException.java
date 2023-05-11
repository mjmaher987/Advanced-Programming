package Exceptions;

public class WrongPasswordException extends Exception {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof WrongPasswordException;
    }
}
