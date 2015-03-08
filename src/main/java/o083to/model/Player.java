package o083to.model;

import o083to.Game;

public abstract class Player extends Observable implements Runnable {

    protected final Game game;
    private final int delay;

    private volatile boolean isMoving = true;
    private volatile boolean isAlive = true;

    protected Player(Game game, int delay) {
        this.game = game;
        this.delay = delay;
    }

    protected abstract void move();

    public void stay() {
        isMoving = false;
    }

    public void start() {
        isMoving = true;
    }

    public void die() {
        isAlive = false;
    }

    public void run() {
        while (isAlive) {
            try {
                Thread.sleep(delay);
                if (isMoving && isAlive) {
                    move();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
