package rs.ac.singidunum.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import lombok.Getter;
import rs.ac.singidunum.interfaces.IRenderable;

import java.util.ArrayList;
import java.util.List;

public class GameObject extends Behavior implements IRenderable {

    @Getter
    private Transform transform;

    private List<Behavior> components;

    @Getter
    private GameObject parent;

    @Getter
    private List<GameObject> children;

    public GameObject() {
        this.transform = new Transform();
        this.components = new ArrayList<>();
        parent = null;
        children = new ArrayList<>();
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

    @Override
    public void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glPushMatrix();
        gl.glTranslated(getTransform().getPosition().getX(), getTransform().getPosition().getY(), getTransform().getPosition().getZ());
        gl.glRotated(getTransform().getRotation().getX(), 1, 0, 0);
        gl.glRotated(getTransform().getRotation().getY(), 0, 1, 0);
        gl.glRotated(getTransform().getRotation().getZ(), 0, 0, 1);
        gl.glScaled(getTransform().getScale().getX(), getTransform().getScale().getY(), getTransform().getScale().getZ());

        for (Behavior component : components) {
            if (component instanceof IRenderable) {
                ((IRenderable) component).render(drawable);
            }
        }

        for (GameObject child : children) {
            child.render(drawable);
        }

        gl.glPopMatrix();
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

    public void setParent(GameObject parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = parent;
        parent.children.add(this);
    }

}
