package o083to.controller.game;

import o083to.Game;
import o083to.model.Direction;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickController extends Controller implements MouseListener {

    public MouseClickController(Game game) {
        super(game);
    }

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            game.turnSnake(Direction.LEFT);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            game.turnSnake(Direction.RIGHT);
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
