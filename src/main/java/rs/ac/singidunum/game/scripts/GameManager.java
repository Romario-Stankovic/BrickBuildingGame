package rs.ac.singidunum.game.scripts;

import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Vector3;

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
        player.getTransform().setPosition(new Vector3(0, 1.2, 0));

    }

}
