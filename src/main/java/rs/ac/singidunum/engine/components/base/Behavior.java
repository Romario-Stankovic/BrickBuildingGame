package rs.ac.singidunum.engine.components.base;

import lombok.Getter;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.Transform;

public abstract class Behavior {

    @Getter
    private GameObject gameObject;

    @Getter
    private Transform transform;

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        this.transform = gameObject.getTransform();
    }

    public Behavior() {};

    public abstract void start();
    public abstract void update(double delta);
}
