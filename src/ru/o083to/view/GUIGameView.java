package ru.o083to.view;

import ru.o083to.model.Board;
import ru.o083to.model.Cell;
import ru.o083to.model.StateListener;

import javax.swing.*;
import java.util.Collection;

public class GUIGameView extends JFrame implements StateListener {
    public static final String TITLE = "Snake";

    private final GameBoard gameBoard;

    public GUIGameView(int widthInCells, int heightInCells) {
        super(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameBoard = new GameBoard(widthInCells, heightInCells);
        getContentPane().add(gameBoard);
        pack();
        setVisible(true);
    }

    public void setBoard(Board board) {
        gameBoard.setBoard(board);
    }

    public void update(Collection<Cell> cells) {
        for (Cell cell : cells) {
            gameBoard.repaint(
                    cell.getX() * GameBoard.CELL_SIZE,
                    cell.getY() * GameBoard.CELL_SIZE,
                    GameBoard.CELL_SIZE, GameBoard.CELL_SIZE
            );
        }
    }
}
