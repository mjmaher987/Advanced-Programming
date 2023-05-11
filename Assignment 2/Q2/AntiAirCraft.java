
public class AntiAirCraft {
    private static int code;

    static {
        code = 1;
    }

    private int aaCode;

    AntiAirCraft() {
        this.aaCode = code;
        code++;
    }

    public int getCode() {
        return this.aaCode;
    }

}
