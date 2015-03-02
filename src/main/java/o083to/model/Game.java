package o083to.model;

import o083to.model.snake.Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private final WallCollisionStrategy wallCollisionStrategy;
    private boolean isStarted;
    private final Snake snake;
    private final List<Player> players;
    private final ExecutorService executor;

    public Game(WallCollisionStrategy strategy, int snakeLength, int snakeDelay) {
        wallCollisionStrategy = strategy;
        players = new ArrayList<Player>();
        snake = new Snake(snakeLength, snakeDelay);
        players.add(snake);
        executor = Executors.newFixedThreadPool(1); // todo: по количеству объектов
    }

    public void startPlayers() {
        if (isStarted) {
            for (Player player : players) {
                player.start();
            }
        } else {
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

    public Cell getUpCell(Cell cell) {
        return wallCollisionStrategy.getUpCell(cell);
    }

    public Cell getDownCell(Cell cell) {
        return wallCollisionStrategy.getDownCell(cell);
    }

    public Cell getLeftCell(Cell cell) {
        return wallCollisionStrategy.getLeftCell(cell);
    }

    public Cell getRightCell(Cell cell) {
        return wallCollisionStrategy.getRight(cell);
    }

    public Snake getSnake() {
        return snake;
    }
}
