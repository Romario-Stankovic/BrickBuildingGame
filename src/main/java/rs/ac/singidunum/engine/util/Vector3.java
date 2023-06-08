package rs.ac.singidunum.engine.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vector3 extends Vector2{
    private double z;

    public Vector3(Vector3 v) {
        super(v.x, v.y);
        this.z = v.z;
    }

    public Vector3(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public Vector3() {
        super();
        this.z = 0;
    }

    public Vector3 add(Vector3 other) {
        super.add(other);
        this.z += other.z;
        return this;
    }

    public Vector3 mul(double scalar) {
        super.mul(scalar);
        this.z *= scalar;
        return this;
    }

    @Override
    public String toString() {
        return "Vector3 {x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
    }

}
