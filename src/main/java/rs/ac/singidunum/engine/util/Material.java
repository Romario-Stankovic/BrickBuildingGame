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

    private Color ambient;

    private Color diffuse;

    private Color specular;

    private float shininess;

    private boolean backfaceCulling;

    public Material() {
        ambient = new Color(255, 255, 255);
        diffuse = new Color(255, 255, 255);
        specular = new Color(255, 255, 255);
        shininess = 0.0f;
        backfaceCulling = true;
    }

    public void apply() {
        GL2 gl = Engine.getDrawable().getGL().getGL2();

        float[] a = {
                ambient.getNormalizedRed(),
                ambient.getNormalizedGreen(),
                ambient.getNormalizedBlue(),
                1.0f
        };

        float[] d = {
                diffuse.getNormalizedRed(),
                diffuse.getNormalizedGreen(),
                diffuse.getNormalizedBlue(),
                1.0f
        };

        float[] s = {
                specular.getNormalizedRed(),
                specular.getNormalizedGreen(),
                specular.getNormalizedBlue(),
                1.0f
        };

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, a, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, d, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, s, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shininess);

        if(backfaceCulling) {
            gl.glEnable(GL2.GL_CULL_FACE);
            gl.glCullFace(GL2.GL_BACK);
        } else {
            gl.glDisable(GL2.GL_CULL_FACE);
        }

    }

}
