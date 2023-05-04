package rs.ac.singidunum.game.scripts;

import rs.ac.singidunum.engine.Input;
import rs.ac.singidunum.engine.components.Behavior;
import rs.ac.singidunum.engine.util.Vector3;

import java.awt.event.KeyEvent;

public class RotateCube extends Behavior {

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {
        // Rotate the cube around the Y axis
        getTransform().getRotation().add(new Vector3(0, 10 * delta, 0));
        if(Input.isKeyDown(KeyEvent.VK_W)) {
            getTransform().getPosition().add(new Vector3(0, 0, 1 * delta));
        }

        if(Input.isKeyDown(KeyEvent.VK_S)) {
            getTransform().getPosition().add(new Vector3(0, 0, -1 * delta));
        }

        if(Input.isKeyDown(KeyEvent.VK_A)) {
            getTransform().getPosition().add(new Vector3(1 * delta, 0, 0));
        }

        if(Input.isKeyDown(KeyEvent.VK_D)) {
            getTransform().getPosition().add(new Vector3(-1 * delta, 0, 0));
        }

    }

}
