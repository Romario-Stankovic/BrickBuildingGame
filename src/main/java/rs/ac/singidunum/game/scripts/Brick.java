package rs.ac.singidunum.game.scripts;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.util.Vector3;

@Getter
@Setter
public class Brick {

    private int brickId;
    private int materialId;
    private Vector3 position;
    private Vector3 rotation;

    public Brick() {

    }

    public Brick(int brickId, int materialId, Vector3 position, Vector3 rotation) {
        this.brickId = brickId;
        this.materialId = materialId;
        this.position = position;
        this.rotation = rotation;
    }

}
