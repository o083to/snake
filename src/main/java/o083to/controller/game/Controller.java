package o083to.controller.game;

import o083to.Game;

public abstract class Controller {

    protected Game game;

    protected Controller(Game game) {
        this.game = game;
    }
}
