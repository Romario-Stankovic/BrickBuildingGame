package rs.ac.singidunum.engine.components;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.components.base.Behavior;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObject extends Behavior {

    @Getter
    private String name;

    @Getter
    @Setter
    private boolean acvtive = true;

    @Getter
    private Transform transform;

    private List<Behavior> components;

    @Getter
    private GameObject parent;

    @Getter
    private List<GameObject> children;

    public GameObject(String name) {
        this.name = name;
        this.transform = new Transform(this);
        this.components = new CopyOnWriteArrayList<>();
        parent = null;
        children = new CopyOnWriteArrayList<>();
    }

    public GameObject() {
        this("GameObject");
    }

    @Override
    public void start() {
        for (Behavior component : components) {
            component.start();
        }
        for(GameObject child : children) {
            child.start();
        }
    }

    @Override
    public void update(double delta) {

        if(!acvtive) {
            return;
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

}
