package o083to;

import o083to.controller.game.MouseClickController;
import o083to.controller.game.PauseGameController;
import o083to.controller.game.StartGameController;
import o083to.controller.game.StopGameController;
import o083to.model.Board;
import o083to.model.Direction;
import o083to.model.Player;
import o083to.model.TransparentWallStrategy;
import o083to.model.snake.Snake;
import o083to.view.GUIGameView;
import o083to.view.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private GameView view;
    private final Board board;
    private final Snake snake;
    private final List<Player> players;
    private boolean isStarted;
    private ExecutorService executor;

    public static void main(String[] args) {
        Game game = new Game();
        game.setView();
        game.setControllers();
    }

    Game() {
        board = new Board(new TransparentWallStrategy(10, 10));
        snake = new Snake(3, 1000);
        snake.setBoard(board);
        board.setSnake(snake);
        players = new ArrayList<Player>(1);
        players.add(snake);
    }

    public Snake getSnake() {
        return snake;
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

    private void setView() {
        view = new GUIGameView(10, 10);
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
