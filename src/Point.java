public class Point {
    private final int x;
    private final int y;
    private final boolean put;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isPut() {
        return put;
    }//   false/true - kroz polje se proslo i polje se ne/nalazi na putu za izlaz

    public Point(int y, int x, boolean put)
    {
        this.x = x;
        this.y = y;
        this.put = put;
    }

    @Override
    public String toString() {
        return "(" + y + ", " + x + ") -" + put;
    }
}