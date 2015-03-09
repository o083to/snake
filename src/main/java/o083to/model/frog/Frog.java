package o083to.model.frog;

import o083to.Game;
import o083to.model.board.Cell;
import o083to.model.Player;
import o083to.model.StateListener;

import java.util.ArrayList;
import java.util.List;

public abstract class Frog extends Player {

    protected volatile Cell position;

    protected Frog(Game game, Cell position, int delay) {
        super(game, delay);
        this.position = position;
    }

    public void catchFrog() {
        die();
        notifyListeners(position);
    }

    public Cell getPosition() {
        return position;
    }

    public abstract int getLengthFactor();

    public abstract int getScoreFactor();

    public abstract boolean isPoisonous();

    @Override
    public void addListener(StateListener stateListener) {
        super.addListener(stateListener);
        notifyListeners(position);
    }

    @Override
    protected void move() {
        Cell oldPosition = position;
        position = game.getBoard().getNextCellForFrog(position);
        if (!position.equals(oldPosition)) {
            List<Cell> changeList = new ArrayList<Cell>(2);
            changeList.add(oldPosition);
            changeList.add(position);
            notifyListeners(changeList);
        }
    }
}
