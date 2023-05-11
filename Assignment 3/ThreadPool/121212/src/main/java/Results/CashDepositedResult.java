package Results;

public class CashDepositedResult implements Result {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof CashDepositedResult;
    }
}
