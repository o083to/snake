package ru.o083to.model;

public class TransparentWallStrategy implements WallCollisionStrategy {

    private final int maxX;
    private final int maxY;

    public TransparentWallStrategy(int boardWidth, int boardHeight) {
        maxX = boardWidth - 1;
        maxY = boardHeight - 1;
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

    public Cell getRight(Cell cell) {
        int oldX = cell.getX();
        return Cell.valueOf(oldX == maxX ? 0 : oldX + 1, cell.getY());
    }
}
