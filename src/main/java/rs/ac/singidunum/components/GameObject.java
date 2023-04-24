package rs.ac.singidunum.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import lombok.Getter;
import rs.ac.singidunum.interfaces.IRenderable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObject extends Behavior {

    @Getter
    private Transform transform;

    private List<Behavior> components;

    @Getter
    private GameObject parent;

    @Getter
    private List<GameObject> children;

    public GameObject() {
        this.transform = new Transform(this);
        this.components = new CopyOnWriteArrayList<>();
        parent = null;
        children = new CopyOnWriteArrayList<>();
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

}
