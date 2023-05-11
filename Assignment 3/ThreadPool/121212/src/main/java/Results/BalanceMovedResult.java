package Results;

public class BalanceMovedResult implements Result {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BalanceMovedResult;
    }
}
