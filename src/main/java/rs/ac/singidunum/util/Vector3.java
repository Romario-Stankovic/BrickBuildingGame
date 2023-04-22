package rs.ac.singidunum.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Vector3 extends Vector2{
    private double z;

    public Vector3() {
        super();
        this.z = 0;
    }

    public Vector3(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public void add(Vector3 other) {
        super.add(other);
        this.z += other.z;
    }

    public void mul(double scalar) {
        super.mul(scalar);
        this.z *= scalar;
    }

    @Override
    public String toString() {
        return String.format("Vector3(%f, %f, %f)", getX(), getY(), getZ());
    }

}
