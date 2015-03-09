package o083to.model.board;


import java.util.HashMap;
import java.util.Map;

public class Cell {

    private static final Map<Integer, Cell> CACHE = new HashMap<Integer, Cell>();

    private final int x;
    private final int y;

    public static Cell valueOf(int x, int y) {
        Cell cell = CACHE.get(getHash(x, y));
        if (cell == null) {
            cell = new Cell(x, y);
            CACHE.put(getHash(x, y), cell);
        }
        return cell;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return  x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return getHash(x, y);
    }

    private static int getHash(int x, int y) {
        // if x = 12 and y = 34 then hash = 1234
        return x * (int)Math.pow(10, y / 10 + 1) + y;
    }
}
