package rs.ac.singidunum;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;

import java.awt.event.*;
import java.util.HashMap;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

    public static final Input instance = new Input();

    @Getter
    private static int mouseX = 0;
    @Getter
    private static int mouseY = 0;

    private static HashMap<Integer, Boolean> keys = new HashMap<>();
    private static HashMap<Integer, Boolean> mouseButtons = new HashMap<>();

    public static boolean isKeyDown(int keyCode) {
        if(keys.containsKey(keyCode)) {
            return keys.get(keyCode);
        }
        return false;
    }

    public static boolean isKeyUp(int keyCode) {
        if(keys.containsKey(keyCode)) {
            return !keys.get(keyCode);
        }
        return true;
    }

    public static boolean isMouseDown(int mouseButton) {
        if(mouseButtons.containsKey(mouseButton)) {
            return mouseButtons.get(mouseButton);
        }
        return false;
    }

    public static boolean isMouseUp(int mouseButton) {
        if(mouseButtons.containsKey(mouseButton)) {
            return !mouseButtons.get(mouseButton);
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.put(e.getKeyCode(), false);
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

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseButtons.put(e.getButton(), true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseButtons.put(e.getButton(), false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
