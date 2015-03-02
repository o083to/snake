package o083to.controller.game;

import o083to.model.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseGameController extends Controller implements ActionListener {

    public PauseGameController(Game game) {
        super(game);
    }

    public void actionPerformed(ActionEvent e) {
        game.stopPlayers();
    }
}
