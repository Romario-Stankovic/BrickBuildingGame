package rs.ac.singidunum.engine.util;

import lombok.Getter;
import lombok.Setter;


// Vector2 class
// Used to represent a 2D vector
@Getter()
@Setter()
public class Vector2 {

    // X and Y values
    protected double x;
    protected double y;

    // Copy constructor
    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    // XY constructor
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Empty constructor
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    // Add another vector to this one
    public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    // Multiply this vector by a scalar
    public Vector2 mul(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    @Override()
    public String toString() {
        // Print the vector
        return "Vector2 {x=" + this.x + ", y=" + this.y + "}";
    }

}
