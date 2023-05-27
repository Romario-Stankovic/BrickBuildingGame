package rs.ac.singidunum.engine.components.base;

import com.jogamp.opengl.GL2;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.util.Color;

public abstract class Light extends Behavior {

    protected int lightNumber;
    protected static int lightCount = 0;

    public Light() {
        lightNumber = lightCount++;
        if(lightNumber > Engine.getMaxLights()) {
            throw new RuntimeException("Too many lights!");
        }
    }

    @Override
    public void start() {

        GL2 gl = Engine.getDrawable().getGL().getGL2();
        gl.glEnable(GL2.GL_LIGHT0 + lightNumber);

    }

    @Override
    public void update(double delta) {

        GL2 gl = Engine.getDrawable().getGL().getGL2();

        float[] position = {
            (float)getTransform().getPosition().getX(),
            (float)getTransform().getPosition().getY(),
            (float)getTransform().getPosition().getZ(),
        };

        int LIGHT = GL2.GL_LIGHT0 + lightNumber;

        gl.glLightfv(LIGHT, GL2.GL_POSITION, position, 0);

    }

}
