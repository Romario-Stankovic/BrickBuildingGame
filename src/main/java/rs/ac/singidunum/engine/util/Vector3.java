package rs.ac.singidunum.engine.util;

import lombok.Getter;
import lombok.Setter;

// Vector3 class
// Used to represent a 3D vector
@Getter
@Setter
public class Vector3 extends Vector2{

    // Z value
    private double z;

    // Copy constructor
    public Vector3(Vector3 v) {
        super(v.x, v.y);
        this.z = v.z;
    }

    // XYZ constructor
    public Vector3(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    // XY constructor
    public Vector3() {
        super();
        this.z = 0;
    }

    // Add another vector to this one
    public Vector3 add(Vector3 other) {
        super.add(other);
        this.z += other.z;
        return this;
    }

    // Multiply this vector by a scalar
    public Vector3 mul(double scalar) {
        super.mul(scalar);
        this.z *= scalar;
        return this;
    }

    @Override
    public String toString() {
        // Print the vector
        return "Vector3 {x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
    }

}
