package o083to.model;


import java.util.HashMap;
import java.util.Map;

// todo: Что с синхронизацией всего этого?
public class Cell {

    private static final Map<Integer, Cell> CACHE = new HashMap<Integer, Cell>();

    private final int x;
    private final int y;

    public static Cell valueOf(int x, int y) {
        Cell cell = CACHE.get(getHash(x, y));
        if (cell == null) {
            cell = new Cell(x, y);
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

    private static int getHash(int x, int y) {
        // if x = 12 and y = 34 then hash = 1234
        return x * (y / 10 + 1) + y;
    }
}
