package Results;

public class AccountCreatedResult implements Result {
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AccountCreatedResult);
    }
}
