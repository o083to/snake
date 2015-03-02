package o083to.model;

public abstract class Player extends Observable implements Runnable {

    protected Game game;
    private final int delay;

    private volatile boolean isMoving = true;
    private volatile boolean isAlive = true;

    protected Player(int delay) {
        this.delay = delay;
    }

    public void setGame(Game game) {
        this.game = game;
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
                if (isMoving) {
                    move();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
