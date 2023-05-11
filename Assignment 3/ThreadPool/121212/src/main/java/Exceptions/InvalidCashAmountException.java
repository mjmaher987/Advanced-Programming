package Exceptions;

public class InvalidCashAmountException extends Exception {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof InvalidCashAmountException;
    }
}
