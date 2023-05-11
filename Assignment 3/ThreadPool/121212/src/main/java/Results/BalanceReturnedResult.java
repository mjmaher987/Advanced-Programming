package Results;

public class BalanceReturnedResult implements Result {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BalanceReturnedResult;
    }
}
