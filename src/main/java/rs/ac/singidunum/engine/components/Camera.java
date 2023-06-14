package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import com.jogamp.opengl.glu.GLU;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.interfaces.OnRenderCallback;

import java.util.ArrayDeque;
import java.util.Queue;

// Camera component
// Represents a camera in the scene
// Renders the scene from the camera's perspective to the screen
public class Camera extends Behavior {

    // GLU instance
    private final GLU glu;

    // Clear color of the camera (background color)
    @Getter
    @Setter
    private Color clearColor;

    // Near clipping plane distance
    @Getter
    @Setter
    private double near;

    // Far clipping plane distance
    @Getter
    @Setter
    private double far;

    // Field of view
    @Getter
    @Setter
    private double fov;

    // Queue of transforms
    Queue<Transform> transforms;

    // Empty constructor
    public Camera() {
        // Initialize the GLU instance
        glu = new GLU();
        // Initialize the clear color
        clearColor = new Color(131, 168, 197);
        // Initialize the queue
        transforms = new ArrayDeque<>();
        // Initialize the near and far clipping plane distances
        near = 0.1;
        far = 1000;
        // Initialize the field of view
        fov = 45;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {

    }

    public void render(OnRenderCallback callback) {
        // Get the Drawable instance
        final GLAutoDrawable drawable = Engine.getInstance().getDrawable();
        // Get the GL2 instance
        final GL2 gl = drawable.getGL().getGL2();

        // Get the aspect ratio
        final double aspect = (double)drawable.getSurfaceWidth() / (double)drawable.getSurfaceHeight();

        // Get the transforms of the camera and all of its parents
        GameObject current = getGameObject();
        while (current != null) {
            transforms.add(current.getTransform());
            current = current.getParent();
        }

        // Get the size of the queue
        final int queueSize = transforms.size();

        // Clear the buffers
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_STENCIL_BUFFER_BIT);

        // Set the clear color
        gl.glClearColor(clearColor.getNormalizedRed(), clearColor.getNormalizedGreen(), clearColor.getNormalizedBlue(), clearColor.getNormalizedAlpha());

        // Set the viewport
        gl.glMatrixMode(GL2.GL_PROJECTION);
        // Load the identity matrix
        gl.glLoadIdentity();
        // Set the perspective
        glu.gluPerspective(fov, aspect, near, far);
        
        // Switch to the model view matrix
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        // Load the identity matrix
        gl.glLoadIdentity();

        // Push a new matrix
        gl.glPushMatrix();
        // Set camera position and rotation using gluLookAt
        glu.gluLookAt(
                0, 0, 0,
                    0, 0, -1,
                    0, 1, 0);

        // Go through the queue of transforms and apply them
        while (!transforms.isEmpty()) {
            // Get the transform that is first in the queue
            Transform transform = transforms.remove();
            // Push a new matrix
            gl.glPushMatrix();
                // Apply the rotation
                gl.glRotated(transform.getRotation().getX(), 1, 0, 0);
                gl.glRotated(transform.getRotation().getY(), 0, 1, 0);
                gl.glRotated(transform.getRotation().getZ(), 0, 0, 1);

                // Apply the translation
                gl.glTranslated(transform.getPosition().getX(), transform.getPosition().getY(), transform.getPosition().getZ());
        }

        // Call the callback function to render the scene
        callback.callback();

        // Pop the matrices
        for(int i=0; i<queueSize; i++) {
            gl.glPopMatrix();
        }

        // Pop the camera matrix
        gl.glPopMatrix();

    }
}
