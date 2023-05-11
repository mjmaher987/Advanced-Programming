package Results;

public class PasswordChangedResult implements Result {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PasswordChangedResult;
    }
}
