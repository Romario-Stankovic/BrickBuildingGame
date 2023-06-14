package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Light;
import rs.ac.singidunum.engine.util.Color;

//TODO: Fix this
// Point light component
// Represents a light that has a position and shines in all directions
// The light intensity decreases with distance
public class PointLight extends Light {

    // Diffuse light color
    @Getter
    @Setter
    Color diffuseColor;

    // Specular light color
    @Getter
    @Setter
    Color specularColor;

    // Attenuation values
    private float constantAttenuation;
    private float linearAttenuation;
    private float quadraticAttenuation;

    // Constructor with diffuse and specular color
    public PointLight(Color diffuse, Color specular) {
        // Call the base class constructor
        super();
        // Set the colors
        this.diffuseColor = diffuse;
        this.specularColor = specular;
        // Set the attenuation values
        this.constantAttenuation = 0.0f;
        this.linearAttenuation = 0.09f;
        this.quadraticAttenuation = 0.032f;
    }

    // Empty constructor
    public PointLight() {
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

        // Transform the diffuse color into a float array
        final float[] diffuse = {
                diffuseColor.getNormalizedRed(),
                diffuseColor.getNormalizedGreen(),
                diffuseColor.getNormalizedBlue(),
                1.0f
        };

        // Transform specular color into a float array
        final float[] specular = {
                specularColor.getNormalizedRed(),
                specularColor.getNormalizedGreen(),
                specularColor.getNormalizedBlue(),
                1.0f
        };

        // Set the colors of the light
        gl.glLightfv(LIGHT, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(LIGHT, GL2.GL_SPECULAR, specular, 0);

        // Set the attenuation values
        gl.glLightfv(LIGHT, GL2.GL_CONSTANT_ATTENUATION, new float[]{constantAttenuation}, 0);
        gl.glLightfv(LIGHT, GL2.GL_LINEAR_ATTENUATION, new float[]{linearAttenuation}, 0);
        gl.glLightfv(LIGHT, GL2.GL_QUADRATIC_ATTENUATION, new float[]{quadraticAttenuation}, 0);

    }


}
