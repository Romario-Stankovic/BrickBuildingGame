package rs.ac.singidunum.game.scripts;

import rs.ac.singidunum.engine.Input;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.util.Mathf;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Vector3;

import java.awt.event.MouseEvent;

public class MouseLook extends Behavior {

    private int lastX = 0;
    private int lastY = 0;
    private int zoom = -30;

    private double panSpeed = 0.1;
    private double zoomSpeed = 1;

    private boolean canPan = false;
    private GameObject camera;

    @Override
    public void start() {

        camera = getGameObject().findChild("Camera");

        Input.onMouseDown(MouseEvent.BUTTON1, (args) -> {
            canPan = true;
        });

        Input.onMouseUp(MouseEvent.BUTTON1, (args) -> {
            canPan = false;
        });

        Input.onMouseScrolled((args) -> {
            zoom -= (int) args[0] * zoomSpeed;
            zoom = Mathf.clamp(zoom, -50, -10);
        });

    }

    @Override
    public void update(double delta) {
        int x = Input.getMouseX();
        int y = Input.getMouseY();
        int dx = x - lastX;
        int dy = y - lastY;
        lastX = x;
        lastY = y;

        camera.getTransform().getPosition().setZ(zoom);

        if(canPan) {
            getTransform().getRotation().add(new Vector3(dy * panSpeed, dx * panSpeed, 0));
        }


    }
}
