package o083to.view;

import o083to.Game;
import o083to.model.StateListener;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public interface GameView extends StateListener {

    void setGame(Game game);

    void addMouseClickController(MouseListener controller);

    void addPauseGameController(ActionListener controller);

    void addStartGameController(ActionListener controller);

    void addStopGameController(ActionListener controller);

    void gameOver();

    void updateScore(int value);
}
