package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Light;
import rs.ac.singidunum.engine.util.Color;

public class AmbientLight extends Light {

    @Getter
    @Setter
    private Color color;

    public AmbientLight(Color color) {
        super();
        this.color = color;
    }

    public AmbientLight() {
        this(new Color(255, 255, 255));
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        GL2 gl = Engine.getInstance().getDrawable().getGL().getGL2();
        int LIGHT = GL2.GL_LIGHT0 + lightNumber;

        float[] a = {
                color.getNormalizedRed(),
                color.getNormalizedGreen(),
                color.getNormalizedBlue(),
                1.0f
        };

        gl.glLightfv(LIGHT, GL2.GL_AMBIENT, a, 0);

    }


}
