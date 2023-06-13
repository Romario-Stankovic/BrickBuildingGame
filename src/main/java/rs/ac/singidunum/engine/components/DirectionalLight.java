package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Light;
import rs.ac.singidunum.engine.util.Color;

public class DirectionalLight extends Light {

    @Getter
    @Setter
    private Color diffuseColor;

    @Getter
    @Setter
    private Color specularColor;

    public DirectionalLight(Color diffuseColor, Color specularColor) {
        super();
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
    }

    public DirectionalLight() {
        this(new Color(255, 255, 255), new Color(255, 255, 255));
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        GL2 gl = Engine.getInstance().getDrawable().getGL().getGL2();
        int LIGHT = GL2.GL_LIGHT0 + lightNumber;

        float[] direction = {
                (float)getTransform().getPosition().getX(),
                (float)getTransform().getPosition().getY(),
                (float)getTransform().getPosition().getZ(),
                0.0f
        };

        gl.glLightfv(LIGHT, GL2.GL_POSITION, direction, 0);

        float[] d = {
                diffuseColor.getNormalizedRed(),
                diffuseColor.getNormalizedGreen(),
                diffuseColor.getNormalizedBlue(),
                1.0f
        };

        float[] s = {
                specularColor.getNormalizedRed(),
                specularColor.getNormalizedGreen(),
                specularColor.getNormalizedBlue(),
                1.0f
        };

        gl.glLightfv(LIGHT, GL2.GL_DIFFUSE, d, 0);
        gl.glLightfv(LIGHT, GL2.GL_SPECULAR, s, 0);

    }


}
