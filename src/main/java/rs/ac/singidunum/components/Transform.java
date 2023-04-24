package rs.ac.singidunum.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.util.Vector3;

import java.util.List;

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

    @Override
    public String toString() {
        return "Transform{" +
                "position=" + position +
                ", rotation=" + rotation +
                ", scale=" + scale +
                '}';
    }

}
