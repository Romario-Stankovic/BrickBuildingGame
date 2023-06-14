package rs.ac.singidunum.engine.util;

// Mathf class
// Math helper class
public class Mathf {

    // Clamp real value between min and max values (inclusive)
    public static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    // Clamp integer value between min and max values (inclusive)
    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

}
