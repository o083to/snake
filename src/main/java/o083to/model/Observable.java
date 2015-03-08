package o083to.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Observable {

    private final List<StateListener> listeners = new ArrayList<StateListener>();

    public void addListener(StateListener stateListener) {
        listeners.add(stateListener);
    }

    protected void notifyListeners(Collection<Cell> changedCells) {
        for (StateListener listener : listeners) {
            listener.update(changedCells);
        }
    }

    protected void notifyListeners(Cell cell) {
        List<Cell> changedCells = Collections.singletonList(cell);
        notifyListeners(changedCells);
    }
}
