package rs.ac.singidunum.engine.components;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.components.base.Behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

// GameObject class
// Represents a game object in the scene
public class GameObject {

    // Name of the GameObject
    @Getter
    private String name;

    // Is the GameObject active
    @Getter
    @Setter
    private boolean active;

    // Has the GameObject started 
    private boolean started;

    // Transform of the GameObject
    @Getter
    private Transform transform;

    // List of components attached to the GameObject
    private List<Behavior> components;

    // Parent of the GameObject
    @Getter
    private GameObject parent;

    // Children of the GameObject
    @Getter
    private List<GameObject> children;

    // Map of all GameObjects in the game
    private static Map<String, GameObject> gameObjects = new HashMap<>();

    // GameObject constructor
    public GameObject(String name) {
        // Set the name
        this.name = name;
        // Set the active flag to true
        this.active = true;
        // Set the started flag to false
        this.started = false;
        // Initialize the transform
        this.transform = new Transform(this);
        // Initialize the list of components
        this.components = new CopyOnWriteArrayList<>();
        // Set the parent to null
        parent = null;
        // Initialize the list of children
        children = new CopyOnWriteArrayList<>();
        // Add the GameObject to the map
        gameObjects.put(name, this);
    }

    // Empty constructor
    public GameObject() {
        // Call the other constructor with the default name
        this("GameObject");
    }

    public void start() {

        // Check if the GameObject is active
        if(!active) {
            // If it isn't, return
            return;
        }

        // Start all components attached to the GameObject
        for (Behavior component : components) {
            component.start();
        }

        // Set the started flag to true
        started = true;

    }

    public void update(double delta) {

        // Check if the GameObject is active
        if(!active) {
            // If it isn't, return
            return;
        }

        // Check if the GameObject has started
        if(!started) {
            // If it hasn't, start it
            start();
        }

        // Update all components attached to the GameObject
        for (Behavior component : components) {
            component.update(delta);
        }

        // Update all children
        for(GameObject child : children) {
            child.update(delta);
        }
    }

    // Add a component to the GameObject
    public <T extends Behavior> T addComponent(T component) {
        // Add the component to the list
        this.components.add(component);
        // Set the GameObject reference
        component.setGameObject(this);
        // Return the component
        return component;
    }

    // Get a component of the specified type
    public <T> T getComponent(Class<T> type) {
        // Loop through all components
        for (Behavior component : components) {
            // Check if the component is of the specified type
            if (type.isInstance(component)) {
                // If it is, return it
                return type.cast(component);
            }
        }
        // If no component of the specified type was found, return null
        return null;
    }

    // Remove a component from the GameObject
    public void removeComponent(Behavior component) {
        this.components.remove(component);
    }

    // Set the parent of the GameObject
    public void setParent(GameObject parent) {
        // Check if the gameObject has a parent
        if (this.parent != null) {
            // If it does, remove the GameObject from the parent's children
            this.parent.children.remove(this);
        }
        // Set the parent
        this.parent = parent;
        // Check if the parent is null
        if(parent == null) {
            // If it is, return
            return;
        }

        // Add the GameObject to the parent's children
        parent.children.add(this);
    }

    // Destroy the GameObject
    public void destroy() {
        // Check if the GameObject has a parent
        if (this.parent != null) {
            // If it does, remove the GameObject from the parent's children
            this.parent.children.remove(this);
        }
        // Remove the parent
        this.setParent(null);
        // Remove the GameObject from the map
        gameObjects.remove(this.name);
    }

    // Find a child GameObject by name
    public GameObject findChild(String name) {
        // Loop through all children
        for(GameObject child : children) {
            // Check if the child's name matches the specified name
            if(child.getName().equals(name)) {
                // If it does, return it
                return child;
            }
        }
        // If no child was found, return null
        return null;
    }

    // Find a GameObject by name
    public static GameObject findGameObject(String name) {
        return gameObjects.get(name);
    }

    @Override
    public String toString() {
        // Print the GameObject
        return "GameObject {name=" + this.name + "}";
    }
}
