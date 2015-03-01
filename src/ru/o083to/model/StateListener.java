package ru.o083to.model;

import java.util.Collection;

public interface StateListener {

    void update(Collection<Cell> cells);
}
