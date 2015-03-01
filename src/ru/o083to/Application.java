package ru.o083to;

import ru.o083to.model.Board;
import ru.o083to.model.TransparentWallStrategy;
import ru.o083to.model.snake.Snake;
import ru.o083to.view.GUIGameView;

public class Application {

    public static void main(String[] args) {
        Snake snake = new Snake(8);
        Board board = new Board(10, 10, new TransparentWallStrategy(10, 10));
        board.setSnake(snake);
        snake.setBoard(board);
        GUIGameView view = new GUIGameView(10, 10);
        view.setBoard(board);
        snake.addListener(view);
    }
}
