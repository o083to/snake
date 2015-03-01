package o083to.model;

public abstract class Animal extends Observable implements Runnable {

    protected Board board;
    private final int delay;

    protected Animal(int delay) {
        this.delay = delay;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    protected abstract void move();

    public void run() {
        while (true) {
            try {
                Thread.sleep(delay);
                move();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
