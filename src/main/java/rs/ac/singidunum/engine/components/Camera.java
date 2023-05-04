package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import com.jogamp.opengl.glu.GLU;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.interfaces.OnRenderCallback;

import java.util.ArrayDeque;
import java.util.Queue;

public class Camera extends Behavior {

    private final GLU glu;

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

    Queue<Transform> transforms;

    public Camera() {
        glu = new GLU();
        near = 0.1;
        far = 1000;
        clearColor = new Color(131, 168, 197);
        transforms = new ArrayDeque<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {

    }

    public void render(OnRenderCallback callback) {
        GLAutoDrawable drawable = Engine.getDrawable();
        GL2 gl = drawable.getGL().getGL2();

        double aspect = (double)drawable.getSurfaceWidth() / (double)drawable.getSurfaceHeight();

        GameObject current = getGameObject();
        while (current != null) {
            transforms.add(current.getTransform());
            current = current.getParent();
        }
        int stackSize = transforms.size();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        gl.glClearColor(clearColor.getNormalizedRed(), clearColor.getNormalizedGreen(), clearColor.getNormalizedBlue(), clearColor.getNormalizedAlpha());

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(fov, aspect, near, far);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glPushMatrix();
        glu.gluLookAt(
                0, 0, 0,
                    0, 0, -1,
                    0, 1, 0);

        while (!transforms.isEmpty()) {
            Transform transform = transforms.remove();
            gl.glPushMatrix();
                gl.glRotated(transform.getRotation().getX(), 1, 0, 0);
                gl.glRotated(transform.getRotation().getY(), 0, 1, 0);
                gl.glRotated(transform.getRotation().getZ(), 0, 0, 1);

                gl.glTranslated(transform.getPosition().getX(), transform.getPosition().getY(), transform.getPosition().getZ());
        }

        callback.callback();

        for(int i=0; i<stackSize; i++) {
            gl.glPopMatrix();
        }

        gl.glPopMatrix();

    }
}
