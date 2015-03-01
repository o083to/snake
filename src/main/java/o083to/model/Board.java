package o083to.model;

import o083to.model.snake.Snake;

public class Board {

    private final int width;
    private final int height;
    private final WallCollisionStrategy wallCollisionStrategy;

    private Snake snake;

    public Board(int width, int height, WallCollisionStrategy strategy) {
        this.width = width;
        this.height = height;
        wallCollisionStrategy = strategy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Snake getSnake() {
        return snake;
    }
}
