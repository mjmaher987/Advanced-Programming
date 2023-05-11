package Results;

public class CardRemovedResult implements Result {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof CardRemovedResult;
    }
}
