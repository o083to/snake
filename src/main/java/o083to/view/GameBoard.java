package o083to.view;

import o083to.Game;
import o083to.model.Cell;
import o083to.model.frog.BlueFrog;
import o083to.model.frog.Frog;
import o083to.model.frog.GreenFrog;
import o083to.model.frog.RedFrog;
import o083to.model.snake.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class GameBoard extends JPanel {

    public static final int CELL_SIZE = 32;
    public static final int SNAKE_HEAD_DIAMETER = 32;
    public static final int SNAKE_BODY_DIAMETER = 24;
    public static final int SNAKE_TAIL_DIAMETER = 16;
    public static final int FROG_DIAMETER = 24;

    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color SNAKE_COLOR = new Color(0xffd700);
    private Game game;
    private final static Map<Class, Color> frogColors = new HashMap<Class, Color>(3);
    static {
        frogColors.put(GreenFrog.class, new Color(0x6b8e23));
        frogColors.put(RedFrog.class, new Color(0xb22222));
        frogColors.put(BlueFrog.class, new Color(0x008b8b));
    }

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
        Graphics2D g2d = (Graphics2D) graphics;
        if (game.getFrogs() != null) paintFrogs(g2d, game.getFrogs());
        if (game.getSnake() != null) paintSnake(g2d, game.getSnake());
    }

    private static void paintSnake(Graphics2D g2d, Snake snake) {
        LinkedList<Cell> body = snake.getBody();
        paintCircle(g2d, body.getFirst(), SNAKE_HEAD_DIAMETER, SNAKE_COLOR);
        for (int i = 1; i < body.size() - 1; i++) {
            paintCircle(g2d, body.get(i), SNAKE_BODY_DIAMETER, SNAKE_COLOR);
        }
        paintCircle(g2d, body.getLast(), SNAKE_TAIL_DIAMETER, SNAKE_COLOR);
    }

    private static void paintFrogs(Graphics2D g2d, List<Frog> frogs) {
        for (Frog frog : frogs) {
            paintCircle(g2d, frog.getPosition(), FROG_DIAMETER, frogColors.get(frog.getClass()));
        }
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
