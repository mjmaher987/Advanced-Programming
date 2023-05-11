package model.primitive;

public class ArrayContainer {
    private final int[] ints;
    private final byte[] bytes;
    private final char[] chars;

    public ArrayContainer() {
        ints = new int[]{0,1,2,3,4,5};
        bytes = new byte[]{0,1,0,1,0,1,0};
        chars = "Maryam".toCharArray();
    }

    public int[] getInts() {
        return ints;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public char[] getChars() {
        return chars;
    }
}
