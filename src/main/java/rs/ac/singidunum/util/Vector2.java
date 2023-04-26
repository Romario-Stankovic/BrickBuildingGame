package rs.ac.singidunum.util;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class Vector2 {

    protected double x;
    protected double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2 mul(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

}
