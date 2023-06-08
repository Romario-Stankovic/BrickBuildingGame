package rs.ac.singidunum.engine;

import lombok.Getter;
import rs.ac.singidunum.engine.interfaces.ICallback;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static final Input instance = new Input();

    @Getter
    private static int mouseX = 0;
    @Getter
    private static int mouseY = 0;

    private static final Events keyEvents = new Events();
    private static final Events mouseEvents = new Events();
    private static final Events mouseWheelEvents = new Events();

    public static void onKeyDown(Short keyCode, ICallback callback) {
        keyEvents.subscribe(keyCode.toString() + "down", callback);
    }

    public static void onKeyUp(Short keyCode, ICallback callback) {
        keyEvents.subscribe(keyCode.toString() + "up", callback);
    }

    public static void onKeyPressed(Short keyCode, ICallback callback) {
        keyEvents.subscribe(keyCode.toString() + "pressed", callback);
    }

    public static void onMouseDown(Integer mouseButton, ICallback callback) {
        mouseEvents.subscribe(mouseButton.toString() + "down", callback);
    }

    public static void onMouseUp(Integer mouseButton, ICallback callback) {
        mouseEvents.subscribe(mouseButton.toString() + "up", callback);
    }

    public static void onMouseClicked(Integer mouseButton, ICallback callback) {
        mouseEvents.subscribe(mouseButton.toString() + "clicked", callback);
    }

    public static void onMouseScrolled(ICallback callback) {
        mouseWheelEvents.subscribe("scrolled", callback);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyEvents.emit(e.getKeyCode() + "pressed", e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyEvents.emit(e.getKeyCode() + "down", e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyEvents.emit(e.getKeyCode() + "up", e.getKeyCode());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseEvents.emit(e.getButton() + "clicked", e.getButton());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseEvents.emit(e.getButton() + "down", e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseEvents.emit(e.getButton() + "up", e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelEvents.emit("scrolled", e.getWheelRotation());
    }
}
