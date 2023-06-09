package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;

import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.interfaces.IRenderable;
import rs.ac.singidunum.engine.util.Material;

import java.util.Stack;

public class Skybox extends Behavior implements IRenderable {

    private Camera camera;

    private Stack<Transform> transforms;

    private Material material;

    public Skybox() {
        this.transforms = new Stack<>();
        material = new Material();
    }

    public void setTexture(Texture texture) {
        this.material.setTexture(texture);
    }

    @Override
    public void start() {
        if (getGameObject().getComponent(Camera.class) == null) {
            throw new RuntimeException("Skybox must be attached to a GameObject with a Camera component.");
        }
        camera = getGameObject().getComponent(Camera.class);
    }

    @Override
    public void update(double delta) {
        this.render(Engine.getDrawable());
    }

    @Override
    public void render(GLAutoDrawable drawable) {

        if (material.getTexture() == null) {
            return;
        }

        GL2 gl = drawable.getGL().getGL2();

        GameObject current = this.getGameObject();
        while (current != null) {
            transforms.push(current.getTransform());
            current = current.getParent();
        }
        int stackSize = transforms.size();

        material.apply();

        material.getTexture().enable(gl);
        material.getTexture().bind(gl);
        gl.glTexParameteri(GL2.GL_TEXTURE, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL2.GL_TEXTURE, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);

        while (!transforms.empty()) {
            gl.glPushMatrix();
            Transform transform = transforms.pop();
            gl.glTranslated(-transform.getPosition().getX(), -transform.getPosition().getY(), -transform.getPosition().getZ());
        }

        gl.glScaled(camera.getFar() / 2, camera.getFar() / 2, camera.getFar() / 2);

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
        gl.glPopMatrix();

        for(int i = 0; i < stackSize; i++) {
            gl.glPopMatrix();
        }

        material.getTexture().disable(gl);

    }

}
