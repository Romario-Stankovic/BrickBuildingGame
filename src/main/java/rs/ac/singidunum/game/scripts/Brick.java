package rs.ac.singidunum.game.scripts;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.util.Vector3;

public class Brick {
    @Getter
    @Setter
    private int brickId;
    @Getter
    @Setter
    private int materialId;
    @Getter
    @Setter
    private Vector3 position;
    @Getter
    @Setter
    private Vector3 rotation;

    @Getter
    @Setter
    @JsonIgnore
    private GameObject gameObject;

    public Brick() {

    }

    public Brick(int brickId, int materialId, Vector3 position, Vector3 rotation) {
        this.brickId = brickId;
        this.materialId = materialId;
        this.position = position;
        this.rotation = rotation;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Brick)) {
            return false;
        }

        Brick brick = (Brick) obj;

        if(brick.brickId != this.brickId) {
            return false;
        }

        if(brick.materialId != this.materialId) {
            return false;
        }

        if(Math.abs(brick.position.getX() - this.position.getX()) > 0.1) {
            return false;
        }

        if(Math.abs(brick.position.getY() - this.position.getY()) > 0.1) {
            return false;
        }

        if(Math.abs(brick.position.getZ() - this.position.getZ()) > 0.1) {
            return false;
        }

        if(brick.brickId != 0) {
            if((brick.getRotation().getY() / 90) % 2 != (this.getRotation().getY() / 90) % 2) {
                return false;
            }
        }

        return true;

    }

}
