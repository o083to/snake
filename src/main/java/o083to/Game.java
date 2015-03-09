package o083to;

import o083to.controller.game.MouseClickController;
import o083to.controller.game.PauseGameController;
import o083to.controller.game.StartGameController;
import o083to.controller.game.StopGameController;
import o083to.model.board.Board;
import o083to.model.board.Cell;
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

    private static final int FROG_DELAY_MULTIPLIER = 2;
    private static final double BLUE_FROGS_PERCENT = 0.25;

    private final int frogsCount;
    private final int frogDelay;
    private final int frogStartDelay;

    private final GameView view;
    private final Board board;
    private final Snake snake;
    private final List<Frog> frogs;

    private ExecutorService executor;
    private final Random random = new Random();

    private int score;
    private boolean isStarted;
    private boolean isFinished;
    private int blueFrogsCount;
    private final int maxBlueFrogsCount;

    public static void main(String[] args) {
        UserInputValidator validator = new UserInputValidator();
        validator.validate(args);
        new Game(
                validator.getN(),
                validator.getM(),
                validator.getSnakeLength(),
                validator.getFrogsCount(),
                validator.getSnakeDelay()
        );
    }

    Game(int n, int m, int snakeLength, int frogsCount, int snakeDelay) {
        this.frogsCount = frogsCount;
        frogStartDelay = snakeDelay / frogsCount;
        frogDelay = snakeDelay * FROG_DELAY_MULTIPLIER;

        maxBlueFrogsCount = (int)Math.ceil(frogsCount * BLUE_FROGS_PERCENT);

        view = new GUIGameView(n, m);
        view.setGame(this);

        board = new Board(this, n, m);
        snake = new Snake(this, snakeLength, snakeDelay);
        snake.addListener(view);
        board.markCellsAsBusy(snake.getBody());

        frogs = createFrogs(frogsCount, frogDelay);
        setControllers();
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
                Thread.sleep(frogStartDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frog.start();
        }
    }

    private void start() {
        executor = Executors.newFixedThreadPool(frogsCount + 1);
        executor.submit(snake);
        for (Frog frog : frogs) {
            try {
                Thread.sleep(frogStartDelay);
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
            Frog newFrog = createRandomFrog(frogDelay);
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
        List<Frog> result = new CopyOnWriteArrayList<Frog>();
        for (int i = 0; i < count; i++) {
            Frog frog = createRandomFrog(delay);
            result.add(frog);
        }
        return result;
    }

    private Frog createRandomFrog(int delay) {
        Frog frog;
        int i = random.nextInt();
        if (i % 5 == 0 && frogsCount > 1 && blueFrogsCount < maxBlueFrogsCount) {
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
