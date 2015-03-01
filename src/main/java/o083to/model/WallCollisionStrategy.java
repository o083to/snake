package o083to.model;

public interface WallCollisionStrategy {

    Cell getUpCell(Cell cell);

    Cell getDownCell(Cell cell);

    Cell getLeftCell(Cell cell);

    Cell getRight(Cell cell);
}
