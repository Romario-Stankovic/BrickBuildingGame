package rs.ac.singidunum.game.scripts;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.util.Vector3;

// Brick class
// Represents a brick that is the player places
// Or that is loaded from a file
public class Brick {
    // The ID (idx) of the placed brick
    @Getter
    @Setter
    private int brickId;
    // The ID (idx) of the placed material
    @Getter
    @Setter
    private int materialId;
    // The position of the brick
    @Getter
    @Setter
    private Vector3 position;
    // The rotation of the brick
    @Getter
    @Setter
    private Vector3 rotation;

    // The GameObject of the brick
    @Getter
    @Setter
    @JsonIgnore
    private GameObject gameObject;

    // Empty constructor
    public Brick() {}

    // Constructor with parameters
    public Brick(int brickId, int materialId, Vector3 position, Vector3 rotation) {
        // Set the values
        this.brickId = brickId;
        this.materialId = materialId;
        this.position = position;
        this.rotation = rotation;
    }

    // Check if two bricks are the same
    @Override
    public boolean equals(Object obj) {
        // If the other object is not a Brick, return false
        if(!(obj instanceof Brick)) {
            return false;
        }

        // Cast the object
        Brick brick = (Brick) obj;

        // If the brick does not have the same brick ID, return false
        if(brick.brickId != this.brickId) {
            return false;
        }

        // If the brick does not have the same material ID, return false
        if(brick.materialId != this.materialId) {
            return false;
        }

        // If the placed brick is off by more than 0.1 along the X axis, return false
        if(Math.abs(brick.position.getX() - this.position.getX()) > 0.1) {
            return false;
        }

        // If the placed brick is off by more than 0.1 along the Y axis, return false
        if(Math.abs(brick.position.getY() - this.position.getY()) > 0.1) {
            return false;
        }

        // If the placed brick is off by more than 0.1 along the Z axis, return false
        if(Math.abs(brick.position.getZ() - this.position.getZ()) > 0.1) {
            return false;
        }

        // If the brick is not a 2x2
        if(brick.brickId != 0) {
            // Check if the brick is in the same orientation (vertical or horizontal)
            if((brick.getRotation().getY() / 90) % 2 != (this.getRotation().getY() / 90) % 2) {
                // If not, return false
                return false;
            }
        }

        // Return true
        return true;

    }

}
