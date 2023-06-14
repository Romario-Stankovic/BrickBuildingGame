package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Light;
import rs.ac.singidunum.engine.util.Color;

// Ambient light component
// Represents a light that is always on and has no direction
public class AmbientLight extends Light {

    // Ambient light color
    @Getter
    @Setter
    private Color ambientColor;

    // Constructor with color
    public AmbientLight(Color ambientColor) {
        // Call the base class constructor
        super();
        // Set the color
        this.ambientColor = ambientColor;
    }

    // Empty constructor
    public AmbientLight() {
        // Call the other constructor with white color
        this(new Color(255, 255, 255));
    }

    @Override
    public void start() {
        // Start the base class
        super.start();
    }

    @Override
    public void update(double delta) {
        // Update the base class
        super.update(delta);

        // Get GL2 instance
        final GL2 gl = Engine.getInstance().getDrawable().getGL().getGL2();

        // Get the LIGHT constant for the current light
        final int LIGHT = GL2.GL_LIGHT0 + lightNumber;

        // Transform the light color to float array
        final float[] ambient = {
                ambientColor.getNormalizedRed(),
                ambientColor.getNormalizedGreen(),
                ambientColor.getNormalizedBlue(),
                1.0f
        };

        // Set the ambient light color
        gl.glLightfv(LIGHT, GL2.GL_AMBIENT, ambient, 0);

    }


}
