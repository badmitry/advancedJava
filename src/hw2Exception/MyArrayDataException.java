package hw2Exception;

public class MyArrayDataException extends NumberFormatException {
    private int x;
    private int y;

    public MyArrayDataException(String s, int x, int y) {
        super(s);
        this.x = x + 1;
        this.y = y + 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
