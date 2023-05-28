package rs.ac.singidunum.engine.util;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;

@Getter
@Setter
public class Material {

    private Texture texture;

    private Color mainColor;

    private Color ambientColor;
    private Color diffuseColor;
    private Color specularColor;
    private Color emissionColor;

    private float shininess;

    private boolean backfaceCulling;

    public Material() {
        mainColor = new Color(255, 255, 255);
        ambientColor = new Color(255, 255, 255);
        diffuseColor = new Color(255, 255, 255);
        specularColor = new Color(255, 255, 255);
        emissionColor = new Color(0, 0, 0);

        shininess = 0.0f;
        backfaceCulling = true;
    }

    public void apply() {
        GL2 gl = Engine.getDrawable().getGL().getGL2();

        Color ambient = Color.multiply(mainColor, ambientColor);
        Color diffuse = Color.multiply(mainColor, diffuseColor);

        float[] a = {
                ambient.getNormalizedRed(),
                ambient.getNormalizedGreen(),
                ambient.getNormalizedBlue(),
                ambient.getNormalizedAlpha()
        };

        float[] d = {
                diffuse.getNormalizedRed(),
                diffuse.getNormalizedGreen(),
                diffuse.getNormalizedBlue(),
                diffuse.getNormalizedAlpha()
        };

        float[] s = {
                specularColor.getNormalizedRed(),
                specularColor.getNormalizedGreen(),
                specularColor.getNormalizedBlue(),
                specularColor.getNormalizedAlpha()
        };

        float[] e = {
                emissionColor.getNormalizedRed(),
                emissionColor.getNormalizedGreen(),
                emissionColor.getNormalizedBlue(),
                emissionColor.getNormalizedAlpha()
        };

        float[] alpha = {
                mainColor.getNormalizedAlpha()
        };

        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, a, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, d, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, s, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, e, 0);

        gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_ALPHA, alpha, 0);

        if(backfaceCulling) {
            gl.glEnable(GL2.GL_CULL_FACE);
            gl.glCullFace(GL2.GL_BACK);
        } else {
            gl.glDisable(GL2.GL_CULL_FACE);
        }

    }

}
