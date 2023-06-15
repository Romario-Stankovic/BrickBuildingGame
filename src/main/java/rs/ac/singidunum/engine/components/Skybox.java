package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;

import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.interfaces.IRenderable;
import rs.ac.singidunum.engine.util.Material;

import java.util.Stack;

// Skybox component
// Renders a skybox around the camera
public class Skybox extends Behavior implements IRenderable {

    // Reference to the camera
    private Camera camera;

    // Stack of transforms
    private Stack<Transform> transforms;

    // Material
    private Material material;

    // Empty constructor
    public Skybox() {
        // Initialize the stack
        this.transforms = new Stack<>();
        // Initialize the material
        material = new Material();
    }

    // Sets the skybox texture
    public void setTexture(Texture texture) {
        this.material.setTexture(texture);
    }

    @Override
    public void start() {
        // Check if the skybox is attached to a GameObject with a Camera component
        if (getGameObject().getComponent(Camera.class) == null) {
            throw new RuntimeException("Skybox must be attached to a GameObject with a Camera component.");
        }
        // Get the camera component
        camera = getGameObject().getComponent(Camera.class);
    }

    @Override
    public void update(double delta) {
        // Render the skybox
        this.render(Engine.getInstance().getDrawable());
    }

    @Override
    public void render(GLAutoDrawable drawable) {

        // Check if the material has a texture
        if (material.getTexture() == null) {
            // If not, don't render the skybox
            return;
        }

        // Get GL2 instance
        final GL2 gl = drawable.getGL().getGL2();

        // Push the transforms to the stack
        GameObject current = this.getGameObject();
        while (current != null) {
            transforms.push(current.getTransform());
            current = current.getParent();
        }
        // Get the stack size
        final int stackSize = transforms.size();

        // Get the value of alpha blending
        boolean blending = gl.glIsEnabled(GL2.GL_BLEND);

        // Disable alpha blending for the skybox
        gl.glDisable(GL2.GL_BLEND);

        // Apply the material
        material.apply();

        // Enable the texture
        material.getTexture().enable(gl);
        material.getTexture().bind(gl);
        // Set the texture parameters
        gl.glTexParameteri(GL2.GL_TEXTURE, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL2.GL_TEXTURE, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);

        // Push the camera transform to the stack
        while (!transforms.empty()) {
            // Push a matrix
            gl.glPushMatrix();
            // Get the last added transform
            Transform transform = transforms.pop();
            // Translate the skybox in the opposite direction of the camera
            // This is done so that the skybox is always centered around the camera
            gl.glTranslated(-transform.getPosition().getX(), -transform.getPosition().getY(), -transform.getPosition().getZ());
        }

        // Scale the skybox
        gl.glScaled(camera.getFar() / 2, camera.getFar() / 2, camera.getFar() / 2);

        // Render the skybox
        gl.glBegin(GL2.GL_QUADS);
            // Top face
            gl.glTexCoord2d(1.0 / 4.0, 2.0 / 3.0);
            gl.glVertex3d(-1, 1, -1);

            gl.glTexCoord2d(2.0 / 4.0, 2. / 3.);
            gl.glVertex3d(1, 1, -1);

            gl.glTexCoord2d(2.0 / 4.0, 1.0);
            gl.glVertex3d(1, 1, 1);

            gl.glTexCoord2d(1.0 / 4.0, 1.0);
            gl.glVertex3d(-1, 1, 1);

            // Left face
            gl.glTexCoord2d(0.0, 2.0 / 3.0);
            gl.glVertex3d(-1, 1, 1);

            gl.glTexCoord2d(0.0, 1.0 / 3.0);
            gl.glVertex3d(-1, -1, 1);

            gl.glTexCoord2d(1.0 / 4.0, 1.0 / 3.0);
            gl.glVertex3d(-1, -1, -1);

            gl.glTexCoord2d(1.0 / 4.0, 2.0 / 3.0);
            gl.glVertex3d(-1, 1, -1);

            // Back face
            gl.glTexCoord2d(1.0 / 4.0, 2.0 / 3.0);
            gl.glVertex3d(-1, 1, -1);

            gl.glTexCoord2d(1.0 / 4.0, 1.0 / 3.0);
            gl.glVertex3d(-1, -1, -1);

            gl.glTexCoord2d(2.0 / 4.0, 1.0 / 3.0);
            gl.glVertex3d(1, -1, -1);

            gl.glTexCoord2d(2.0 / 4.0, 2.0 / 3.0);
            gl.glVertex3d(1, 1, -1);

            // Right face
            gl.glTexCoord2d(2.0 / 4.0, 2.0 / 3.0);
            gl.glVertex3d(1, 1, -1);

            gl.glTexCoord2d(2.0 / 4.0, 1.0 / 3.0);
            gl.glVertex3d(1, -1, -1);

            gl.glTexCoord2d(3.0 / 4.0, 1.0 / 3.0);
            gl.glVertex3d(1, -1, 1);

            gl.glTexCoord2d(3.0 / 4.0, 2.0 / 3.0);
            gl.glVertex3d(1, 1, 1);

            // Front face
            gl.glTexCoord2d(3.0 / 4.0, 2.0 / 3.0);
            gl.glVertex3d(1, 1, 1);

            gl.glTexCoord2d(3.0 / 4.0, 1.0 / 3.0);
            gl.glVertex3d(1, -1, 1);

            gl.glTexCoord2d(1.0, 1.0 / 3.0);
            gl.glVertex3d(-1, -1, 1);

            gl.glTexCoord2d(1.0, 2.0 / 3.0);
            gl.glVertex3d(-1, 1, 1);


            // Bottom face
            gl.glTexCoord2d(1.0 / 4.0, 1.0 / 3.0);
            gl.glVertex3d(-1, -1, -1);

            gl.glTexCoord2d(1.0 / 4.0, 0.0);
            gl.glVertex3d(-1, -1, 1);

            gl.glTexCoord2d(2.0 / 4.0, 0.0);
            gl.glVertex3d(1, -1, 1);

            gl.glTexCoord2d(2.0 / 4.0, 1.0 / 3.0);
            gl.glVertex3d(1, -1, -1);
        gl.glEnd();

        // Pop the matrices from the stack
        for(int i = 0; i < stackSize; i++) {
            gl.glPopMatrix();
        }

        // Disable the texture
        material.getTexture().disable(gl);

        // Re-enable blending
        if(blending) {
            gl.glEnable(GL2.GL_BLEND);
        }

    }

}
