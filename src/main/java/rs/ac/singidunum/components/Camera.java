package rs.ac.singidunum.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import com.jogamp.opengl.glu.GLU;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.util.Color;
import rs.ac.singidunum.util.Vector3;

public class Camera extends Behavior {

    private GLU glu;

    @Getter
    @Setter
    private Color clearColor;

    @Getter
    @Setter
    private double near;

    @Getter
    @Setter
    private double far;

    @Getter
    @Setter
    private double fov = 45;

    public Camera() {
        glu = new GLU();
        near = 0.1;
        far = 1000;
        clearColor = new Color(131, 168, 197);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {

    }

    public void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        double aspect = (double)drawable.getSurfaceWidth() / (double)drawable.getSurfaceHeight();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        gl.glClearColor(clearColor.getNormalizedRed(), clearColor.getNormalizedGreen(), clearColor.getNormalizedBlue(), clearColor.getNormalizedAlpha());

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(fov, aspect, near, far);

        //TODO: Fix this to work with the new GameObject rendering system
        gl.glPushMatrix();
            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glLoadIdentity();

            gl.glRotated(getTransform().getRotation().getX(), 1, 0, 0);
            gl.glRotated(getTransform().getRotation().getY(), 0, 1, 0);
            gl.glRotated(getTransform().getRotation().getZ(), 0, 0, 1);

            gl.glTranslated(getTransform().getPosition().getX(), getTransform().getPosition().getY(), getTransform().getPosition().getZ());
        gl.glPopMatrix();
    }
}
