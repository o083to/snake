package o083to.model;

import o083to.Game;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Board {

    private final Set<Cell> busyCells = new HashSet<Cell>();
    private final Game game;
    private final int maxX;
    private final int maxY;
    private final Random random = new Random();

    public Board(Game game, int width, int height) {
        this.game = game;
        maxX = width - 1;
        maxY = height - 1;
    }

    public void releaseCell(Cell cell) {
        busyCells.remove(cell);
    }

    public synchronized void markCellAsBusy(Cell cell) {
        busyCells.add(cell);
    }

    public synchronized void markCellsAsBusy(List<Cell> cells) {
        for (Cell cell : cells) {
            busyCells.add(cell);
        }
    }

    public synchronized Cell getNextCellForFrog(Cell oldCell) {
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

    public synchronized Cell snakeGoUp(Cell cell) {
        Cell result = upCell(cell);
        busyCells.add(result);
        game.checkMove(result);
        return result;
    }

    public synchronized Cell snakeGoDown(Cell cell) {
        Cell result = downCell(cell);
        busyCells.add(result);
        game.checkMove(result);
        return result;
    }

    public synchronized Cell snakeGoLeft(Cell cell) {
        Cell result = leftCell(cell);
        busyCells.add(result);
        game.checkMove(result);
        return result;
    }

    public synchronized Cell snakeGoRight(Cell cell) {
        Cell result = rightCell(cell);
        busyCells.add(result);
        game.checkMove(result);
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
