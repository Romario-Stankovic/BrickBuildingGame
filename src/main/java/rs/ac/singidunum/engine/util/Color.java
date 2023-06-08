package rs.ac.singidunum.engine.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Color {

    private int red;
    private int green;
    private int blue;
    private int alpha;

    public Color(Color c) {
        this.red = c.red;
        this.green = c.green;
        this.blue = c.blue;
        this.alpha = c.alpha;
    }

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

    public static Color multiply(Color a, Color b) {
        return new Color(
                (int) (a.getNormalizedRed() * b.getNormalizedRed() * 255),
                (int) (a.getNormalizedGreen() * b.getNormalizedGreen() * 255),
                (int) (a.getNormalizedBlue() * b.getNormalizedBlue() * 255),
                (int) (a.getNormalizedAlpha() * b.getNormalizedAlpha() * 255)
        );
    }

}
