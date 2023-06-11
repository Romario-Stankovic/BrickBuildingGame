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

}
