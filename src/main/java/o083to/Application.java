package o083to;

import o083to.controller.game.MouseClickController;
import o083to.controller.game.PauseGameController;
import o083to.controller.game.StartGameController;
import o083to.controller.game.StopGameController;
import o083to.model.Game;
import o083to.model.TransparentWallStrategy;
import o083to.view.GUIGameView;
import o083to.view.GameView;

public class Application {

    public static void main(String[] args) {
        Game game = new Game(
                new TransparentWallStrategy(10, 10),
                3,
                1000
        );
        GameView view = new GUIGameView(10, 10);
        view.setGame(game);
        game.getSnake().addListener(view);

        MouseClickController snakeController = new MouseClickController(game);
        view.addMouseClickController(snakeController);

        view.addStartGameController(new StartGameController(game));
        view.addPauseGameController(new PauseGameController(game));
        view.addStopGameController(new StopGameController(game));
    }
}
