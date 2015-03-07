package o083to.model;

import o083to.model.frog.Frog;
import o083to.model.snake.Snake;

import java.util.*;

public class Board {

    private final Set<Cell> busyCells = new HashSet<Cell>();

    private final int maxX;
    private final int maxY;
    private Snake snake;
    private List<Frog> frogs = new ArrayList<Frog>();
    private final Random random = new Random();

    public Board(int width, int height) {
        maxX = width - 1;
        maxY = height - 1;
    }

    public void addFrog(Frog frog) {
        frogs.add(frog);
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
        for (Cell cell : snake.getBody()) {
            busyCells.add(cell);
        }
    }

    public void releaseCell(Cell cell) {
        busyCells.remove(cell);
    }

    public synchronized Cell getNewCellForFrog(Cell oldCell) {
        Direction direction = Direction.fromInteger(random.nextInt(4));
        for (int i = 0; i < 4; i++) {
            Cell newCell = oldCell;
            // todo: ну что это???
            switch (direction) {
                case UP:
                    newCell = upCell(oldCell);
                    break;
                case DOWN:
                    newCell = downCell(oldCell);
                    break;
                case LEFT:
                    newCell = leftCell(oldCell);
                    break;
                case RIGHT:
                    newCell = rightCell(oldCell);
            }
            if (busyCells.contains(newCell)) {
                direction = direction.turnLeft();
            } else {
                busyCells.remove(oldCell);
                busyCells.add(newCell);
                return newCell;
            }
        }
        return oldCell;
    }

    public synchronized Cell getFreeCell() {
        Cell cell;
        do {
            int x = random.nextInt(maxX + 1);
            int y = random.nextInt(maxY + 1);
            cell = Cell.valueOf(x, y);
        } while (busyCells.contains(cell));
        busyCells.add(cell);
        return cell;
    }

    public synchronized Cell getUpCell(Cell cell) {
        Cell result = upCell(cell);
        busyCells.add(result);
        return result;
    }

    public synchronized Cell getDownCell(Cell cell) {
        Cell result = downCell(cell);
        busyCells.add(result);
        return result;
    }

    public synchronized Cell getLeftCell(Cell cell) {
        Cell result = leftCell(cell);
        busyCells.add(result);
        return result;
    }

    public synchronized Cell getRightCell(Cell cell) {
        Cell result = rightCell(cell);
        busyCells.add(result);
        return result;
    }

    private Cell upCell(Cell cell) {
        int oldY = cell.getY();
        return Cell.valueOf(cell.getX(), oldY == 0 ? maxY : oldY - 1);
    }

    private Cell downCell(Cell cell) {
        int oldY = cell.getY();
        return Cell.valueOf(cell.getX(), oldY == maxY ? 0 : oldY + 1);
    }

    private Cell leftCell(Cell cell) {
        int oldX = cell.getX();
        return Cell.valueOf(oldX == 0 ? maxX : oldX - 1, cell.getY());
    }

    private Cell rightCell(Cell cell) {
        int oldX = cell.getX();
        return Cell.valueOf(oldX == maxX ? 0 : oldX + 1, cell.getY());
    }
}
