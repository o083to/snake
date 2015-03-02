package o083to.controller.game;

import o083to.model.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGameController extends Controller implements ActionListener {

    public StartGameController(Game game) {
        super(game);
    }

    public void actionPerformed(ActionEvent e) {
        game.startPlayers();
    }
}
