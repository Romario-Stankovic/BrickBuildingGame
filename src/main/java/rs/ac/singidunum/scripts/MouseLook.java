package rs.ac.singidunum.scripts;

import javafx.scene.input.KeyCode;
import rs.ac.singidunum.Input;
import rs.ac.singidunum.components.Behavior;
import rs.ac.singidunum.util.Vector3;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MouseLook extends Behavior {

    int lastX = 0;
    int lastY = 0;

    double speed = 10;

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {

        int x = Input.getMouseX();
        int y = Input.getMouseY();
        int dx = x - lastX;
        int dy = y - lastY;
        lastX = x;
        lastY = y;

        if(!Input.isMouseDown(MouseEvent.BUTTON1)) {
            return;
        }

        if(Input.isKeyDown(KeyEvent.VK_W)) {
            getTransform().getPosition().add(new Vector3(0, 0, speed * delta));
        }

        if(Input.isKeyDown(KeyEvent.VK_S)) {
            getTransform().getPosition().add(new Vector3(0, 0, -speed * delta));
        }

        if(Input.isKeyDown(KeyEvent.VK_A)) {
            getTransform().getPosition().add(new Vector3(speed * delta, 0, 0));
        }

        if(Input.isKeyDown(KeyEvent.VK_D)) {
            getTransform().getPosition().add(new Vector3(-speed * delta, 0, 0));
        }

        if(Input.isKeyDown(KeyEvent.VK_Q)) {
            getTransform().getPosition().add(new Vector3(0, speed * delta, 0));
        }

        if(Input.isKeyDown(KeyEvent.VK_E)) {
            getTransform().getPosition().add(new Vector3(0, -speed * delta, 0));
        }

        getTransform().getRotation().add(new Vector3(dy * 0.1f, dx * 0.1f, 0));

    }

}
