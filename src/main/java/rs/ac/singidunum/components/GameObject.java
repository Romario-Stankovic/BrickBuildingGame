package rs.ac.singidunum.components;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameObject extends Behavior {

    @Getter
    private Transform transform;

    private List<Behavior> components;

    @Getter
    private static List<GameObject> gameObjects = new ArrayList<>();

    public GameObject() {
        this.transform = new Transform();
        this.components = new ArrayList<>();
        gameObjects.add(this);
    }

    @Override
    public void start() {
        for (Behavior component : components) {
            component.start();
        }
    }

    @Override
    public void update(double delta) {
        for (Behavior component : components) {
            component.update(delta);
        }
    }

    public <T extends Behavior> T addComponent(T component) {
        this.components.add(component);
        component.setGameObject(this);
        return component;
    }

    public <T> T getComponent(Class<T> type) {
        for (Behavior component : components) {
            if (type.isInstance(component)) {
                return (T) component;
            }
        }
        return null;
    }

}
