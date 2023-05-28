package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Light;
import rs.ac.singidunum.engine.util.Color;

public class PointLight extends Light {

    Color diffuse;
    Color specular;

    private float constantAttenuation = 0.0f;
    private float linearAttenuation = 0.09f;
    private float quadraticAttenuation = 0.032f;

    public PointLight(Color diffuse, Color specular) {
        super();
        this.diffuse = diffuse;
        this.specular = specular;
    }

    public PointLight() {
        this(new Color(255, 255, 255), new Color(255, 255, 255));
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        GL2 gl = Engine.getDrawable().getGL().getGL2();
        int LIGHT = GL2.GL_LIGHT0 + lightNumber;

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

        gl.glLightfv(LIGHT, GL2.GL_DIFFUSE, d, 0);
        gl.glLightfv(LIGHT, GL2.GL_SPECULAR, s, 0);

        gl.glLightfv(LIGHT, GL2.GL_CONSTANT_ATTENUATION, new float[]{constantAttenuation}, 0);
        gl.glLightfv(LIGHT, GL2.GL_LINEAR_ATTENUATION, new float[]{linearAttenuation}, 0);
        gl.glLightfv(LIGHT, GL2.GL_QUADRATIC_ATTENUATION, new float[]{quadraticAttenuation}, 0);

    }


}
