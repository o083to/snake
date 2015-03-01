package ru.o083to.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Observable {

    private List<StateListener> listeners = new ArrayList<StateListener>();

    public void addListener(StateListener stateListener) {
        listeners.add(stateListener);
    }

    protected void notifyListeners(Collection<Cell> changedCells) {
        for (StateListener listener : listeners) {
            listener.update(changedCells);
        }
    }
}
