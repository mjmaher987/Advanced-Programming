package Exceptions;

public class NotEnoughBalanceException extends Exception {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof NotEnoughBalanceException;
    }
}
