package o083to.model;

import o083to.model.snake.Snake;

public class Board {

    private final WallCollisionStrategy wallCollisionStrategy;
    private Snake snake;

    public Board(WallCollisionStrategy strategy) {
        wallCollisionStrategy = strategy;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Cell getUpCell(Cell cell) {
        return wallCollisionStrategy.getUpCell(cell);
    }

    public Cell getDownCell(Cell cell) {
        return wallCollisionStrategy.getDownCell(cell);
    }

    public Cell getLeftCell(Cell cell) {
        return wallCollisionStrategy.getLeftCell(cell);
    }

    public Cell getRightCell(Cell cell) {
        return wallCollisionStrategy.getRight(cell);
    }

    public Snake getSnake() {
        return snake;
    }
}
