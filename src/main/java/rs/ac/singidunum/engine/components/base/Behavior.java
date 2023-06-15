package rs.ac.singidunum.engine.components.base;

import lombok.Getter;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.Transform;

// Base class for all behaviors
// Behaviors are components that are attached to game objects
public abstract class Behavior {

    // Reference to the gameObject that this behavior is attached to
    @Getter
    private GameObject gameObject;

    // Reference to the transform of the gameObject that this behavior is attached to
    @Getter
    private Transform transform;

    // Sets the gameObject and transform references
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        this.transform = gameObject.getTransform();
    }

    // Empty constructor
    public Behavior() {};

    // Start is called once
    public abstract void start();

    // Update is called every frame
    public abstract void update(double delta);

    public void onEnable() {};
    public void onDisable() {};

}
