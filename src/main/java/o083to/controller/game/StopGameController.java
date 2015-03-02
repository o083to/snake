package o083to.controller.game;

import o083to.model.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopGameController extends Controller implements ActionListener {

    public StopGameController(Game game) {
        super(game);
    }

    public void actionPerformed(ActionEvent e) {
        game.killPlayers();
    }
}
