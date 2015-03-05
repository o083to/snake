package o083to;

import o083to.controller.game.MouseClickController;
import o083to.controller.game.PauseGameController;
import o083to.controller.game.StartGameController;
import o083to.controller.game.StopGameController;
import o083to.model.Board;
import o083to.model.Direction;
import o083to.model.Player;
import o083to.model.frog.Frog;
import o083to.model.frog.GreenFrog;
import o083to.model.snake.Snake;
import o083to.view.GUIGameView;
import o083to.view.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private static final int N = 10;
    private static final int M = 10;
    private static final int LENGTH = 3;
    private static final int SNAKE_DELAY = 1000;
    private static final int FROG_DELAY_MULTIPLIER = 2;
    private static final int FROGS_COUNT = 8;

    private GameView view;
    private final Board board;
    private final Snake snake;
    private final List<Player> players = new ArrayList<Player>();
    private final List<Frog> frogs;
    private boolean isStarted;
    private ExecutorService executor;

    public static void main(String[] args) {
        Game game = new Game();
        game.setView();
        game.setControllers();
    }

    Game() {
        board = new Board(N, M);
        snake = new Snake(LENGTH, SNAKE_DELAY);
        snake.setBoard(board);
        board.setSnake(snake);
        players.add(snake);
        frogs = createFrogs(FROGS_COUNT, SNAKE_DELAY * FROG_DELAY_MULTIPLIER);
        players.addAll(frogs);
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Frog> getFrogs() {
        return frogs;
    }

    public void startPlayers() {
        if (isStarted) {
            for (Player player : players) {
                player.start();
            }
        } else {
            executor = Executors.newFixedThreadPool(1);
            for (Player player : players) {
                executor.submit(player);
            }
            isStarted = true;
        }
    }

    public void stopPlayers() {
        for (Player player : players) {
            player.stay();
        }
    }

    public void killPlayers() {
        for (Player player : players) {
            player.die();
        }
    }

    public void turnSnake(Direction direction) {
        snake.turn(direction);
    }

    private List<Frog> createFrogs(int count, int delay) {
        List<Frog> result = new ArrayList<Frog>(count);
        for (int i = 0; i < count; i++) {
            //todo: дичь должна быть разных видов
            Frog frog = new GreenFrog(board.getFreeCell(), delay);
            result.add(frog);
            frog.setBoard(board);
            board.addFrog(frog);
            System.out.println(frog.getBody().getX() + " " + frog.getBody().getY());
        }
        return result;
    }

    private void setView() {
        view = new GUIGameView(N, M);
        view.setGame(this);
        snake.addListener(view);
    }

    private void setControllers() {
        view.addMouseClickController(new MouseClickController(this));
        view.addStartGameController(new StartGameController(this));
        view.addPauseGameController(new PauseGameController(this));
        view.addStopGameController(new StopGameController(this));
    }
}
