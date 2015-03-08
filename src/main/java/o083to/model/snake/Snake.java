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

    private final int initialLength;
    private int length;
    private Direction direction = Direction.DOWN;
    private final LinkedList<Cell> body;
    private Cell oldTailPosition;

    public Snake(Game game, int length, int delay) {
        super(game, delay);
        this.length = length;
        initialLength = length;
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
        oldTailPosition = body.getLast();
        changeList.add(body.removeLast());

        for (Cell bodyPart : body) {
            if (newHeadPosition.equals(bodyPart)) {
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

    public void grow() {
        length++;
        game.getBoard().markCellAsBusy(oldTailPosition);
        List<Cell> changeList = new ArrayList<Cell>(2);
        // todo: дичь не должна попадать туда, где может появиться хвост
        changeList.add(body.getLast());
        changeList.add(oldTailPosition);
        body.addLast(oldTailPosition);
        notifyListeners(changeList);
    }

    public void reduce() {
        if (length > initialLength) {
            length--;
            List<Cell> changeList = new ArrayList<Cell>(2);
            game.getBoard().releaseCell(body.getLast());
            changeList.add(body.removeLast());
            changeList.add(body.getLast());
            notifyListeners(changeList);
        }
    }

    private Cell getNewHeadPosition(Cell oldHeadPosition) {
        Game.Board board = game.getBoard();
        switch (direction) {
            case DOWN:
                return board.snakeGoDown(oldHeadPosition);
            case RIGHT:
                return board.snakeGoRight(oldHeadPosition);
            case UP:
                return board.snakeGoUp(oldHeadPosition);
            case LEFT:
                return board.snakeGoLeft(oldHeadPosition);
        }
        return oldHeadPosition;
    }
}
