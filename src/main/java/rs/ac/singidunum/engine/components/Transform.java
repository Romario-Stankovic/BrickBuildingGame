package rs.ac.singidunum.engine.components;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.util.Vector3;

@Getter()
@Setter()
public class Transform {

    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale;

    @Getter
    private GameObject gameObject;

    public Transform(GameObject gameObject) {
        this.gameObject = gameObject;
        this.position = new Vector3(0, 0, 0);
        this.rotation = new Vector3(0, 0, 0);
        this.scale = new Vector3(1, 1, 1);
    }

    public void rotate(Vector3 rotation) {
        this.rotation.add(rotation);
    }


    //TODO: Implement forward(), right() and up() methods
    // to return the direction vectors of the transform

    @Override
    public String toString() {
        return "Transform {position=" + this.position + ", rotation=" + this.rotation + ", scale=" + this.scale + "}";
    }

}
