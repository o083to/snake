package o083to.controller;

import o083to.model.Direction;
import o083to.model.snake.Snake;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickController implements MouseListener {

    private final Snake snake;

    public MouseClickController(Snake snake) {
        this.snake = snake;
    }

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            snake.turn(Direction.LEFT);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            snake.turn(Direction.RIGHT);
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
