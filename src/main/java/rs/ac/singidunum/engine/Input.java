package rs.ac.singidunum.engine;

import lombok.Getter;
import rs.ac.singidunum.engine.interfaces.ICallback;

import java.awt.event.*;

// Input class
// Used to manage input from the user (keyboard, mouse, etc.)
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    // Singleton instance
    public static final Input instance = new Input();

    // Mouse position
    @Getter
    private static int mouseX = 0;
    @Getter
    private static int mouseY = 0;

    // Keyboard and mouse event managers
    private static final EventManager keyEvents = new EventManager();
    private static final EventManager mouseEvents = new EventManager();
    private static final EventManager mouseWheelEvents = new EventManager();

    // Subscribe to key down events
    public static void onKeyDown(Short keyCode, ICallback callback) {
        keyEvents.subscribe(keyCode.toString() + "down", callback);
    }

    // Subscribe to key up events
    public static void onKeyUp(Short keyCode, ICallback callback) {
        keyEvents.subscribe(keyCode.toString() + "up", callback);
    }

    // Subscribe to key pressed events
    public static void onKeyPressed(Short keyCode, ICallback callback) {
        keyEvents.subscribe(keyCode.toString() + "pressed", callback);
    }

    // Subscribe to mouse down events
    public static void onMouseDown(Integer mouseButton, ICallback callback) {
        mouseEvents.subscribe(mouseButton.toString() + "down", callback);
    }

    // Subscribe to mouse up events
    public static void onMouseUp(Integer mouseButton, ICallback callback) {
        mouseEvents.subscribe(mouseButton.toString() + "up", callback);
    }

    // Subscribe to mouse clicked events
    public static void onMouseClicked(Integer mouseButton, ICallback callback) {
        mouseEvents.subscribe(mouseButton.toString() + "clicked", callback);
    }

    // Subscribe to mouse wheel events
    public static void onMouseScrolled(ICallback callback) {
        mouseWheelEvents.subscribe("scrolled", callback);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Emit key pressed event
        keyEvents.emit(e.getKeyCode() + "pressed", e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Emit key down event
        keyEvents.emit(e.getKeyCode() + "down", e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Emit key up event
        keyEvents.emit(e.getKeyCode() + "up", e.getKeyCode());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Update mouse position when dragged
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Update mouse position when moved
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Emit mouse clicked event
        mouseEvents.emit(e.getButton() + "clicked", e.getButton());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Emit mouse down event
        mouseEvents.emit(e.getButton() + "down", e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Emit mouse up event
        mouseEvents.emit(e.getButton() + "up", e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Emit mouse wheel event
        mouseWheelEvents.emit("scrolled", e.getWheelRotation());
    }
}
