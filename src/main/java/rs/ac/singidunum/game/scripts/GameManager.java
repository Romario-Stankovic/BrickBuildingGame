package rs.ac.singidunum.game.scripts;

import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.base.Behavior;

public class GameManager extends Behavior {

    GameObject player = null;

    @Override
    public void start() {

        player = GameObject.findGameObject("Player");

    }

    @Override
    public void update(double delta) {

    }

    public void newGame() {

        player.setActive(true);
        player.getComponent(Player.class).reset();

    }

}
