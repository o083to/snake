package o083to;

import o083to.controller.game.MouseClickController;
import o083to.controller.game.PauseGameController;
import o083to.controller.game.StartGameController;
import o083to.controller.game.StopGameController;
import o083to.model.Board;
import o083to.model.Cell;
import o083to.model.Direction;
import o083to.model.frog.BlueFrog;
import o083to.model.frog.Frog;
import o083to.model.frog.GreenFrog;
import o083to.model.frog.RedFrog;
import o083to.model.snake.Snake;
import o083to.view.GUIGameView;
import o083to.view.GameView;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private static final int N = 10;
    private static final int M = 10;
    private static final int LENGTH = 6;
    private static final int SNAKE_DELAY = 1000;
    private static final int DELAY = 150;
    private static final int FROG_DELAY_MULTIPLIER = 2;
    private static final int FROGS_COUNT = 8;
    private static final double BLUE_FROGS_PERCENT = 0.25;

    private int score;
    private final GameView view;
    private final Board board;
    private final Snake snake;
    private final List<Frog> frogs;
    private boolean isStarted;
    private boolean isFinished;
    private ExecutorService executor;
    private final Random random = new Random();
    private int blueFrogsCount;
    private final int maxBlueFrogsCount;

    public static void main(String[] args) {
        Game game = new Game();
        game.setControllers();
    }

    Game() {
        maxBlueFrogsCount = (int)Math.ceil(FROGS_COUNT * BLUE_FROGS_PERCENT);

        view = new GUIGameView(N, M);
        view.setGame(this);

        board = new Board(this, N, M);
        snake = new Snake(this, LENGTH, SNAKE_DELAY);
        snake.addListener(view);
        board.markCellsAsBusy(snake.getBody());

        frogs = createFrogs(FROGS_COUNT, SNAKE_DELAY * FROG_DELAY_MULTIPLIER);
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Frog> getFrogs() {
        return frogs;
    }

    public Board getBoard() {
        return board;
    }

    public void startPlayers() {
        if (isStarted) {
            resume();
        } else {
            start();
        }
    }

    private void resume() {
        snake.start();
        for (Frog frog : frogs) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frog.start();
        }
    }

    private void start() {
        executor = Executors.newFixedThreadPool(FROGS_COUNT + 1);
        executor.submit(snake);
        for (Frog frog : frogs) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.submit(frog);
        }
        isStarted = true;
    }

    public void stopPlayers() {
        snake.stay();
        for (Frog frog : frogs) {
            frog.stay();
        }
    }

    public void stopGame() {
        isFinished = true;
        view.gameOver();
        snake.die();
        for (Frog frog : frogs) {
            frog.die();
        }
    }

    public void turnSnake(Direction direction) {
        snake.turn(direction);
    }

    public void onSnakeCaughtFrog(Frog frog) {
        frogs.remove(frog);
        if (!isFinished) {
            Frog newFrog = createRandomFrog(SNAKE_DELAY * FROG_DELAY_MULTIPLIER);
            frogs.add(newFrog);
            executor.submit(newFrog);
        }
    }

    public void checkMove(Cell cell) {
        for (Frog frog : frogs) {
            if (cell.equals(frog.getPosition())) {
                if (frog.isPoisonous()) {
                    stopGame();
                } else {
                    if (frog.getLengthFactor() > 0) {
                        snake.grow();
                    } else {
                        snake.reduce();
                    }
                    score += frog.getScoreFactor();
                    view.updateScore(score);
                }
                frog.catchFrog();
                onSnakeCaughtFrog(frog);
                break;
            }
        }
    }

    private List<Frog> createFrogs(int count, int delay) {
        List<Frog> result = new CopyOnWriteArrayList<Frog>(); // todo: Здесь точно должен быть CopyOnWriteArrayList?
        for (int i = 0; i < count; i++) {
            Frog frog = createRandomFrog(delay);
            result.add(frog);
        }
        return result;
    }

    private Frog createRandomFrog(int delay) {
        Frog frog;
        int i = random.nextInt();
        if (i % 5 == 0 && FROGS_COUNT > 1 && blueFrogsCount < maxBlueFrogsCount) {
            blueFrogsCount++;
            frog = new BlueFrog(this, board.getFreeCell(), delay);
        } else if (i % 3 == 0) {
            frog = new RedFrog(this, board.getFreeCell(), delay);
        } else {
            frog = new GreenFrog(this, board.getFreeCell(), delay);
        }
        frog.addListener(view);
        return frog;
    }

    private void setControllers() {
        view.addMouseClickController(new MouseClickController(this));
        view.addStartGameController(new StartGameController(this));
        view.addPauseGameController(new PauseGameController(this));
        view.addStopGameController(new StopGameController(this));
    }
}
