package rs.ac.singidunum.engine.components;

import lombok.Getter;

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
