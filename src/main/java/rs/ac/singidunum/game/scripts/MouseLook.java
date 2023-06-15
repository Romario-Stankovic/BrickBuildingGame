package rs.ac.singidunum.game.scripts;

import rs.ac.singidunum.engine.Input;
import rs.ac.singidunum.engine.Settings;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.util.Mathf;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Vector3;

import java.awt.event.MouseEvent;

// MouseLook component
// Used to rotate the gameObject when moving the mouse
public class MouseLook extends Behavior {

    // The last known X and Y positions of the mouse
    private int lastX = 0;
    private int lastY = 0;

    // The zoom of the camera 
    private int zoom = -30;

    // Define the speeds of the orbit an zoom
    private double sensitivity = 0.1;
    private double zoomSpeed = 1;

    // Define can the camera orbit
    private boolean canOrbit = false;

    // Reference to the camera gameObject 
    private GameObject camera;

    @Override
    public void start() {

        // Find the camera gameObject
        camera = getGameObject().findChild("Camera");

        // Register onMouseDown event
        Input.onMouseDown(MouseEvent.BUTTON1, (args) -> {
            // If the mouse is clicked, canOrbit is true
            canOrbit = true;
        });

        // Register onMouseUp event
        Input.onMouseUp(MouseEvent.BUTTON1, (args) -> {
            // If the mouse button is not clicked, canOrbit is false
            canOrbit = false;
        });

        // Register onMouseScroll event
        Input.onMouseScrolled((args) -> {
            // Get the scroll direction (-1 or 1)
            zoom -= (int) args[0] * zoomSpeed;
            // Clamp the zoom between -50 and -10
            zoom = Mathf.clamp(zoom, -50, -10);
        });

    }

    @Override
    public void update(double delta) {
        // Get the mouse position
        int x = Input.getMouseX();
        int y = Input.getMouseY();
        // Calculate the delta between the two points
        int dx = x - lastX;
        int dy = y - lastY;
        // Save the new mouse position
        lastX = x;
        lastY = y;

        sensitivity = Settings.get("sensitivity", Double.class);

        // Set the cameras zoom
        camera.getTransform().getPosition().setZ(zoom);

        // If the camera canOrbit
        if(canOrbit) {
            // Set the rotation of the camera based on the mouse movement
            getTransform().getRotation().add(new Vector3(dy * sensitivity, dx * sensitivity, 0));
        }


    }
}
