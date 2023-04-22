package rs.ac.singidunum.scripts;

import rs.ac.singidunum.Input;
import rs.ac.singidunum.components.Behavior;
import rs.ac.singidunum.util.Vector3;

import java.awt.event.KeyEvent;

public class RotateCube extends Behavior {

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {
        // Rotate the cube around the Y axis
        getTransform().getRotation().add(new Vector3(0, 10 * delta, 0));
    }

}
