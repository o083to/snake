package o083to.model;

import o083to.model.frog.Frog;
import o083to.model.snake.Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

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
        // todo: когда добавляется дичь, на этой клетке может оказаться другая дичь
        frogs.add(frog);
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public synchronized Cell getFreeCell() {
        Cell cell = null;
        while (cell == null) {
            int x = random.nextInt(maxX + 1);
            int y = random.nextInt(maxY + 1);
            cell = Cell.valueOf(x, y);
            for (Cell snakeBody : snake.getBody()) {
                if (snakeBody.equals(cell)) {
                    cell = null;
                    break;
                }
            }
            for (Frog frog : frogs) {
                if (frog.getBody().equals(cell)) {
                    cell = null;
                    break;
                }
            }
        }
        return cell;
    }

    public Cell getUpCell(Cell cell) {
        int oldY = cell.getY();
        return Cell.valueOf(cell.getX(), oldY == 0 ? maxY : oldY - 1);
    }

    public Cell getDownCell(Cell cell) {
        int oldY = cell.getY();
        return Cell.valueOf(cell.getX(), oldY == maxY ? 0 : oldY + 1);
    }

    public Cell getLeftCell(Cell cell) {
        int oldX = cell.getX();
        return Cell.valueOf(oldX == 0 ? maxX : oldX - 1, cell.getY());
    }

    public Cell getRightCell(Cell cell) {
        int oldX = cell.getX();
        return Cell.valueOf(oldX == maxX ? 0 : oldX + 1, cell.getY());
    }
}
