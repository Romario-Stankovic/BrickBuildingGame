package rs.ac.singidunum.engine.components.base;

import com.jogamp.opengl.GL2;
import rs.ac.singidunum.engine.Engine;

// Base class for all lights
public abstract class Light extends Behavior {

    // Light number represents the current light
    protected final int lightNumber;
    // Light count keeps track of the number of lights that have been created
    protected static int lightCount = 0;

    public Light() {
        // When a new light is created, increment the light count and set the light number
        lightNumber = lightCount++;
        // If the light number is greater than the maximum number of lights allowed, throw an exception
        if(lightNumber > Engine.getInstance().getMaxLights()) {
            throw new RuntimeException("Too many lights!");
        }
    }

    @Override
    public void start() {

        // Get GL2 instance
        final GL2 gl = Engine.getInstance().getDrawable().getGL().getGL2();

        // Enable the light
        gl.glEnable(GL2.GL_LIGHT0 + lightNumber);
    }

    @Override
    public void update(double delta) {
        
        // Get GL2 instance
        final GL2 gl = Engine.getInstance().getDrawable().getGL().getGL2();

        // Transform the light position to float array
        final float[] position = {
            (float)getTransform().getPosition().getX(),
            (float)getTransform().getPosition().getY(),
            (float)getTransform().getPosition().getZ(),
        };

        // Get the LIGHT constant for the current light
        final int LIGHT = GL2.GL_LIGHT0 + lightNumber;

        // Set the light position
        gl.glLightfv(LIGHT, GL2.GL_POSITION, position, 0);

    }

    @Override
    public void onEnable() {
        final GL2 gl = Engine.getInstance().getDrawable().getGL().getGL2();
        // Enable the light
        gl.glEnable(GL2.GL_LIGHT0 + lightNumber);
    }

    @Override
    public void onDisable() {

        final GL2 gl = Engine.getInstance().getDrawable().getGL().getGL2();
        // Enable the light
        gl.glDisable(GL2.GL_LIGHT0 + lightNumber);

    }

}
