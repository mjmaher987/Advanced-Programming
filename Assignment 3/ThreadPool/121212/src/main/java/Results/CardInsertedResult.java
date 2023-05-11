package Results;

public class CardInsertedResult implements Result {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof CardInsertedResult;
    }
}
