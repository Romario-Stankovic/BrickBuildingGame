package rs.ac.singidunum.engine.util;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;

// Material class
// Contains material properties for rendering meshes
@Getter
@Setter
public class Material {

    // Material texture
    private Texture texture;

    // Main color of the material
    private Color mainColor;

    // Ambient, diffuse, specular and emission colors of the material (used for lighting)
    private Color ambientColor;
    private Color diffuseColor;
    private Color specularColor;
    private Color emissionColor;

    // Shininess of the material (used for lighting)
    private float shininess;

    // Enables or disables backface culling
    private boolean backfaceCulling;

    public Material() {
        // Initialize the material colors
        mainColor = new Color(255, 255, 255);
        ambientColor = new Color(255, 255, 255);
        diffuseColor = new Color(255, 255, 255);
        specularColor = new Color(255, 255, 255);
        emissionColor = new Color(0, 0, 0);

        // Initialize the shininess
        shininess = 0.0f;
        // Enable backface culling by default
        backfaceCulling = true;
    }

    // Applies the material to the current OpenGL context
    public void apply() {
        final GL2 gl = Engine.getInstance().getDrawable().getGL().getGL2();

        // Multiply the main color with the ambient and diffuse colors
        final Color ambient = Color.multiply(mainColor, ambientColor);
        final Color diffuse = Color.multiply(mainColor, diffuseColor);

        // Transform the ambient color to float array
        final float[] a = {
                ambient.getNormalizedRed(),
                ambient.getNormalizedGreen(),
                ambient.getNormalizedBlue(),
                ambient.getNormalizedAlpha()
        };

        // Transform the diffuse color to float array
        final float[] d = {
                diffuse.getNormalizedRed(),
                diffuse.getNormalizedGreen(),
                diffuse.getNormalizedBlue(),
                diffuse.getNormalizedAlpha()
        };

        // Transform the specular and emission colors to float arrays
        final float[] s = {
                specularColor.getNormalizedRed(),
                specularColor.getNormalizedGreen(),
                specularColor.getNormalizedBlue(),
                specularColor.getNormalizedAlpha()
        };

        // Transform the emission color to float array
        final float[] e = {
                emissionColor.getNormalizedRed(),
                emissionColor.getNormalizedGreen(),
                emissionColor.getNormalizedBlue(),
                emissionColor.getNormalizedAlpha()
        };

        // Transform the alpha value to float array
        float[] alpha = {
                mainColor.getNormalizedAlpha()
        };

        // Set the material color properties
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, a, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, d, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, s, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, e, 0);

        // Set the shininess and alpha values
        gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_ALPHA, alpha, 0);

        // Check if backface culling is enabled
        if(backfaceCulling) {
            // If it is, enable it
            gl.glEnable(GL2.GL_CULL_FACE);
            gl.glCullFace(GL2.GL_BACK);
        } else {
            // If it isn't, disable it
            gl.glDisable(GL2.GL_CULL_FACE);
        }

    }

}
