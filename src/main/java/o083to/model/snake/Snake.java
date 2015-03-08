package o083to.model.snake;

import o083to.Game;
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
    private final LinkedList<Cell> body;

    public Snake(Game game, int length, int delay) {
        super(game, delay);
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
        Cell newHeadPosition = getNewHeadPosition(body.getFirst());
        List<Cell> changeList = new ArrayList<Cell>(3);
        game.getBoard().releaseCell(body.getLast());
        changeList.add(body.removeLast());
        for (Cell bodyPart : body) {
            if (newHeadPosition.equals(bodyPart)) {
                die();
                game.stopGame();    // todo: На картинке непонятно, что змей зациклился
                return;
            }
        }
        changeList.add(body.getLast());
        changeList.add(body.getFirst());
        body.addFirst(newHeadPosition);
        changeList.add(newHeadPosition);
        notifyListeners(changeList);
    }

    private Cell getNewHeadPosition(Cell oldHeadPosition) {
        Game.Board board = game.getBoard();
        switch (direction) {
            case DOWN:
                return board.getDownCell(oldHeadPosition);
            case RIGHT:
                return board.getRightCell(oldHeadPosition);
            case UP:
                return board.getUpCell(oldHeadPosition);
            case LEFT:
                return board.getLeftCell(oldHeadPosition);
        }
        return oldHeadPosition;
    }
}
