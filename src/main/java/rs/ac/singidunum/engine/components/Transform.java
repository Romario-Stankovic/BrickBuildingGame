package rs.ac.singidunum.engine.components;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.util.Vector3;

// Transform component
// Represents the position, rotation and scale of a GameObject
public class Transform {

    // Position of the transform
    @Getter
    @Setter
    private Vector3 position;

    // Rotation of the transform
    @Getter
    @Setter
    private Vector3 rotation;

    // Scale of the transform
    @Getter
    @Setter
    private Vector3 scale;

    // Reference to the GameObject this transform is attached to
    @Getter
    private GameObject gameObject;

    // Transform constructor
    // Transforms are always attached to a GameObject
    public Transform(GameObject gameObject) {
        // Set the reference to the GameObject
        this.gameObject = gameObject;
        // Initialize the position, rotation and scale
        this.position = new Vector3(0, 0, 0);
        this.rotation = new Vector3(0, 0, 0);
        this.scale = new Vector3(1, 1, 1);
    }

    // Move the transform in the specified direction by the specified amount
    public void move(Vector3 direction) {
        this.position.add(direction);
    }

    // Rotate the transform in the specified direction by the specified amount
    public void rotate(Vector3 rotation) {
        this.rotation.add(rotation);
    }

    //TODO: Implement forward(), right() and up() methods

    @Override
    public String toString() {
        // Print the transform
        return "Transform {position=" + this.position + ", rotation=" + this.rotation + ", scale=" + this.scale + "}";
    }

}
