package o083to.controller.game;

import o083to.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopGameController extends Controller implements ActionListener {

    public StopGameController(Game game) {
        super(game);
    }

    // todo: Змей сейчас успевает сдвинуться на одну клетку после нажатия кнопки "Стоп"
    public void actionPerformed(ActionEvent e) {
        game.killPlayers();
    }
}
