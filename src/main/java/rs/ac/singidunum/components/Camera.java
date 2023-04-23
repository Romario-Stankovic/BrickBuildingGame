package rs.ac.singidunum.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import com.jogamp.opengl.glu.GLU;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.Game;
import rs.ac.singidunum.util.Color;

import java.util.Stack;

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

    Stack<Transform> transforms;

    public Camera() {
        glu = new GLU();
        near = 0.1;
        far = 1000;
        clearColor = new Color(131, 168, 197);
        transforms = new Stack<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {

    }

    public void render() {
        GLAutoDrawable drawable = Game.getDrawable();
        GL2 gl = drawable.getGL().getGL2();

        double aspect = (double)drawable.getSurfaceWidth() / (double)drawable.getSurfaceHeight();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        gl.glClearColor(clearColor.getNormalizedRed(), clearColor.getNormalizedGreen(), clearColor.getNormalizedBlue(), clearColor.getNormalizedAlpha());

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(fov, aspect, near, far);

        GameObject current = getGameObject();
        while (current != null) {
            transforms.add(current.getTransform());
            current = current.getParent();
        }

        int stackSize = transforms.size();

        // TODO: Fix this to work with the new GameObject rendering system
        // Test why is the pivot at the same position as the camera on start
        // Even though the camera is at 0, 0, -10 and the pivot is at 0, 0, 0
        while(!transforms.isEmpty()) {
            gl.glPushMatrix();
            Transform transform = transforms.pop();

            gl.glTranslated(transform.getPosition().getX(), transform.getPosition().getY(), transform.getPosition().getZ());

            gl.glRotated(transform.getRotation().getX(), 1, 0, 0);
            gl.glRotated(transform.getRotation().getY(), 0, 1, 0);
            gl.glRotated(transform.getRotation().getZ(), 0, 0, 1);

            if(transform.equals(getTransform())) {
                gl.glMatrixMode(GL2.GL_MODELVIEW);
                gl.glLoadIdentity();
            }

        }

        for(int i = 0; i < stackSize; i++) {
            gl.glPopMatrix();
        }
        //gl.glLoadIdentity();
    }
}
