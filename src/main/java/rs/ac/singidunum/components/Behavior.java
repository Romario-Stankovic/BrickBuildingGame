package rs.ac.singidunum.components;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class Behavior {

    private GameObject gameObject;

    private Transform transform;

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        this.transform = gameObject.getTransform();
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Transform getTransform() {
        return transform;
    }

    public Behavior() {};

    public abstract void start();
    public abstract void update(double delta);
}
