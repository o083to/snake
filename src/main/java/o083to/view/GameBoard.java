package o083to.view;

import o083to.model.Game;
import o083to.model.Cell;
import o083to.model.snake.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class GameBoard extends JPanel {

    public static final int CELL_SIZE = 32;
    public static final int SNAKE_HEAD_DIAMETER = 32;
    public static final int SNAKE_BODY_DIAMETER = 24;
    public static final int SNAKE_TAIL_DIAMETER = 16;

    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color SNAKE_COLOR = Color.YELLOW;
    private Game game;

    public GameBoard(int widthInCells, int heightInCells) {
        setPreferredSize(new Dimension(widthInCells * CELL_SIZE, heightInCells * CELL_SIZE));
        setBackground(BACKGROUND_COLOR);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void addMouseClickListener(MouseListener controller) {
        addMouseListener(controller);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        if (game != null) {
            Graphics2D g2d = (Graphics2D) graphics;
            paintSnake(g2d, game.getSnake());
        }
    }

    private static void paintSnake(Graphics2D g2d, Snake snake) {
        LinkedList<Cell> body = snake.getBody();
        paintCircle(g2d, body.getFirst(), SNAKE_HEAD_DIAMETER, SNAKE_COLOR);
        for (int i = 1; i < body.size() - 1; i++) {
            paintCircle(g2d, body.get(i), SNAKE_BODY_DIAMETER, SNAKE_COLOR);
        }
        paintCircle(g2d, body.getLast(), SNAKE_TAIL_DIAMETER, SNAKE_COLOR);
    }

    private static void paintCircle(Graphics2D g2d, Cell cell, int diameter, Color color) {
        int x = cell.getX() * CELL_SIZE;
        int y = cell.getY() * CELL_SIZE;
        int indent = (CELL_SIZE - diameter) / 2;

        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);

        g2d.setColor(color);
        g2d.fillOval(x + indent, y + indent, diameter, diameter);
    }
}
