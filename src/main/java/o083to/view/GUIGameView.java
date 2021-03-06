package o083to.view;

import o083to.Game;
import o083to.model.board.Cell;
import o083to.view.board.GameBoard;
import o083to.view.panel.ControlPanel;
import o083to.view.panel.ScorePanel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Collection;

public class GUIGameView extends JFrame implements GameView {
    public static final String TITLE = "Snake";

    private final GameBoard gameBoard;
    private final ControlPanel controlPanel;
    private final ScorePanel scorePanel;

    public GUIGameView(int widthInCells, int heightInCells) {
        super(TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Box layout = Box.createVerticalBox();
        scorePanel = new ScorePanel();
        layout.add(scorePanel);
        gameBoard = new GameBoard(widthInCells, heightInCells);
        layout.add(gameBoard);
        controlPanel = new ControlPanel();
        layout.add(controlPanel);
        getContentPane().add(layout);
        pack();
        setVisible(true);
    }

    public void setGame(Game game) {
        gameBoard.setGame(game);
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

    public void gameOver() {
        controlPanel.getButtonStateController().disableAllButtons();
        scorePanel.writeGameOver();
    }

    public void addMouseClickController(MouseListener controller) {
        gameBoard.addMouseClickListener(controller);
    }

    public void addPauseGameController(ActionListener controller) {
        controlPanel.addPauseButtonListener(controller);
    }

    public void addStartGameController(ActionListener controller) {
        controlPanel.addStartButtonListener(controller);
    }

    public void addStopGameController(ActionListener controller) {
        controlPanel.addStopButtonListener(controller);
    }

    public void updateScore(int value) {
        scorePanel.setScore(value);
    }
}
