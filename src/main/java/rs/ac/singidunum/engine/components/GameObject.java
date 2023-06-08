package rs.ac.singidunum.engine.components;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.components.base.Behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObject extends Behavior {

    @Getter
    private String name;

    @Getter
    @Setter
    private boolean active = true;

    private boolean started = false;

    @Getter
    private Transform transform;

    private List<Behavior> components;

    @Getter
    private GameObject parent;

    @Getter
    private List<GameObject> children;

    private static Map<String, GameObject> gameObjects = new HashMap<>();

    public GameObject(String name) {
        this.name = name;
        this.transform = new Transform(this);
        this.components = new CopyOnWriteArrayList<>();
        parent = null;
        children = new CopyOnWriteArrayList<>();
        gameObjects.put(name, this);
    }

    public GameObject() {
        this("GameObject");
    }

    @Override
    public void start() {

        if(!active) {
            return;
        }

        for (Behavior component : components) {
            component.start();
        }
        for(GameObject child : children) {
            child.start();
        }

        started = true;

    }

    @Override
    public void update(double delta) {

        if(!active) {
            return;
        }

        if(!started) {
            start();
        }

        for (Behavior component : components) {
            component.update(delta);
        }
        for(GameObject child : children) {
            child.update(delta);
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
    
    public void removeComponent(Behavior component) {
        this.components.remove(component);
    }

    public void setParent(GameObject parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = parent;
        parent.children.add(this);
    }

    public GameObject findChild(String name) {
        for(GameObject child : children) {
            if(child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    public static GameObject findGameObject(String name) {
        return gameObjects.get(name);
    }

}
