package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Light;
import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.util.Vector3;

// Directional light component
// Represents a light that has a direction
public class DirectionalLight extends Light {

    // Diffuse light color
    @Getter
    @Setter
    private Color diffuseColor;

    // Specular light color
    @Getter
    @Setter
    private Color specularColor;

    @Getter
    @Setter
    private Vector3 direction;

    // Constructor with diffuse and specular color
    public DirectionalLight(Color diffuseColor, Color specularColor) {
        // Call the base class constructor
        super();
        // Set the colors
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        // Set the direction
        this.direction = new Vector3(0.0f, 0.0f, 1.0f);
    }

    // Empty constructor
    public DirectionalLight() {
        // Call the other constructor with white colors
        this(new Color(255, 255, 255), new Color(255, 255, 255));
    }

    @Override
    public void update(double delta) {
        // Update the base class
        super.update(delta);

        // Get GL2 instance
        final GL2 gl = Engine.getInstance().getDrawable().getGL().getGL2();
        // Get the LIGHT constant for the current light
        final int LIGHT = GL2.GL_LIGHT0 + lightNumber;

        // Transform the light position into a direction float array
        final float[] dir = {
                (float)direction.getX(),
                (float)direction.getY(),
                (float)direction.getZ(),
                0.0f
        };

        // Transform the light diffuse color to float array
        final float[] diffuse = {
                diffuseColor.getNormalizedRed(),
                diffuseColor.getNormalizedGreen(),
                diffuseColor.getNormalizedBlue(),
                1.0f
        };

        // Transform the light specular color to float array
        final float[] specular = {
                specularColor.getNormalizedRed(),
                specularColor.getNormalizedGreen(),
                specularColor.getNormalizedBlue(),
                1.0f
        };

        // Set the light direction
        gl.glLightfv(LIGHT, GL2.GL_POSITION, dir, 0);

        // Set the light colors
        gl.glLightfv(LIGHT, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(LIGHT, GL2.GL_SPECULAR, specular, 0);

    }


}
