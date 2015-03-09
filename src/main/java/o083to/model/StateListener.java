package o083to.model;

import o083to.model.board.Cell;

import java.util.Collection;

public interface StateListener {

    void update(Collection<Cell> cells);
}
