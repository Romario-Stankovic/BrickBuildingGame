package rs.ac.singidunum.engine.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Color {

    int red;
    int green;
    int blue;
    int alpha;

    public Color(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Color(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    public Color() {
        this(0, 0, 0, 255);
    }

    public float getNormalizedRed() {
        return red / 255f;
    }

    public float getNormalizedGreen() {
        return green / 255f;
    }

    public float getNormalizedBlue() {
        return blue / 255f;
    }

    public float getNormalizedAlpha() {
        return alpha / 255f;
    }

}
