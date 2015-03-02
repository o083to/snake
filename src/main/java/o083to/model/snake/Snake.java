package o083to.model.snake;

import o083to.model.Player;
import o083to.model.Cell;
import o083to.model.Direction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Snake extends Player {

    private static final int INIT_X = 0;

    private int length;
    private Direction direction = Direction.DOWN;
    private LinkedList<Cell> body;

    public Snake(int length, int delay) {
        super(delay);
        this.length = length;
        body = new LinkedList<Cell>();
        for (int y = 0; y < length; y++) {
            body.addFirst(Cell.valueOf(INIT_X, y));
        }
    }

    public void turn(Direction turnDirection) {
        direction = direction.turn(turnDirection);
    }

    public LinkedList<Cell> getBody() {
        return body;
    }

    @Override
    protected void move() {
        // todo: пока что просто движется вперёд
        List<Cell> changeList = new ArrayList<Cell>(3);
        changeList.add(body.removeLast());
        changeList.add(body.getLast());
        changeList.add(body.getFirst());
        // todo: проверка столкновения со стеной
        Cell newHeadPosition = getNewHeadPosition(body.getFirst());
        body.addFirst(newHeadPosition);
        changeList.add(newHeadPosition);
        notifyListeners(changeList);
    }

    private Cell getNewHeadPosition(Cell oldHeadPosition) {
        switch (direction) {
            case DOWN:
                return game.getDownCell(oldHeadPosition);
            case RIGHT:
                return game.getRightCell(oldHeadPosition);
            case UP:
                return game.getUpCell(oldHeadPosition);
            case LEFT:
                return game.getLeftCell(oldHeadPosition);
        }
        return oldHeadPosition;
    }
}
