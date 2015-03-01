package ru.o083to.model;

public abstract class Animal extends Observable {

    protected Board board;

    public void setBoard(Board board) {
        this.board = board;
    }

    protected abstract void move();
}
