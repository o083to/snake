package o083to;

import o083to.controller.MouseClickController;
import o083to.model.Board;
import o083to.model.TransparentWallStrategy;
import o083to.model.snake.Snake;
import o083to.view.GUIGameView;
import o083to.view.GameView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) {
        Snake snake = new Snake(3, 1000);
        Board board = new Board(10, 10, new TransparentWallStrategy(10, 10));
        board.setSnake(snake);
        snake.setBoard(board);
        GameView view = new GUIGameView(10, 10);
        view.setBoard(board);
        snake.addListener(view);
        MouseClickController snakeController = new MouseClickController(snake);
        view.addMouseClickController(snakeController);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(snake);
    }
}
