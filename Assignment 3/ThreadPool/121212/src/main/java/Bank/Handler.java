package Bank;

public class Handler {
    private boolean isDone = false;

    public boolean notFinished () {
        return !isDone;
    }

    public void done () {
        this.isDone = true;
    }
}
