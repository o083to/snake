package o083to.model.frog;

import o083to.model.Cell;
import o083to.model.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Frog extends Player {

    protected volatile Cell position;

    protected Frog(Cell position, int delay) {
        super(delay);
        this.position = position;
    }

    public Cell getPosition() {
        return position;
    }

    public abstract int getLengthFactor();

    public abstract int getScoreFactor();

    @Override
    protected void move() {
        Cell oldPosition = position;
        position = board.getNewCellForFrog(position);
        if (!position.equals(oldPosition)) {
            List<Cell> changeList = new ArrayList<Cell>(2);
            changeList.add(oldPosition);
            changeList.add(position);
            notifyListeners(changeList);
        }
    }
}
