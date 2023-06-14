package rs.ac.singidunum.engine.util;

import lombok.Getter;
import lombok.Setter;

// Color class
// Used to represent a color in RGBA format
@Getter
@Setter
public class Color {

    // Red, green, blue and alpha values
    private int red;
    private int green;
    private int blue;
    private int alpha;

    // Copy constructor
    public Color(Color c) {
        // Copy the values
        this.red = c.red;
        this.green = c.green;
        this.blue = c.blue;
        this.alpha = c.alpha;
    }

    // RGBA constructor
    public Color(int red, int green, int blue, int alpha) {
        // Set the values
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    // RGB constructor
    public Color(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    // Empty constructor
    public Color() {
        this(0, 0, 0, 255);
    }

    // Get the normalized red value [0, 1]
    public float getNormalizedRed() {
        return red / 255f;
    }

    // Get the normalized green value [0, 1]
    public float getNormalizedGreen() {
        return green / 255f;
    }

    // Get the normalized blue value [0, 1]
    public float getNormalizedBlue() {
        return blue / 255f;
    }

    // Get the normalized alpha value [0, 1]
    public float getNormalizedAlpha() {
        return alpha / 255f;
    }

    // Multiply two colors
    public static Color multiply(Color a, Color b) {
        return new Color(
                (int) (a.getNormalizedRed() * b.getNormalizedRed() * 255),
                (int) (a.getNormalizedGreen() * b.getNormalizedGreen() * 255),
                (int) (a.getNormalizedBlue() * b.getNormalizedBlue() * 255),
                (int) (a.getNormalizedAlpha() * b.getNormalizedAlpha() * 255)
        );
    }

}
