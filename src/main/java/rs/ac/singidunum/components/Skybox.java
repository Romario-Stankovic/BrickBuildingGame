package rs.ac.singidunum.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;

import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.interfaces.IRenderable;

public class Skybox extends Behavior implements IRenderable {

    @Getter
    @Setter
    private Texture texture;

    private Camera camera;

    public Skybox() {
        this.texture = null;
    }

    @Override
    public void start() {
        if(getGameObject().getComponent(Camera.class) == null) {
            throw new RuntimeException("Skybox must be attached to a GameObject with a Camera component.");
        }
        camera = getGameObject().getComponent(Camera.class);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(GLAutoDrawable drawable) {

        if(texture == null) {
            return;
        }

        GL2 gl = drawable.getGL().getGL2();

        texture.enable(gl);
        texture.bind(gl);

        // Disable depth test so skybox is always drawn first.

        gl.glPushMatrix();
            gl.glLoadIdentity();
            gl.glScaled(camera.getFar()/2, camera.getFar()/2, camera.getFar()/2);

            gl.glRotated(getTransform().getRotation().getX(), 1, 0, 0);
            gl.glRotated(getTransform().getRotation().getY(), 0, 1, 0);
            gl.glRotated(getTransform().getRotation().getZ(), 0, 0, 1);

            gl.glBegin(GL2.GL_QUADS);
                // Top face
                    gl.glTexCoord2d(1.0/4.0, 2.0/3.0);
                    gl.glVertex3d(-1, 1, -1);

                    gl.glTexCoord2d(1.0/4.0, 1.0);
                    gl.glVertex3d(-1, 1, 1);

                    gl.glTexCoord2d(2.0/4.0, 1.0);
                    gl.glVertex3d(1, 1, 1);

                    gl.glTexCoord2d(2.0/4.0, 2./3.);
                    gl.glVertex3d(1, 1, -1);

                // Left face
                    gl.glTexCoord2d(0.0, 2.0/3.0);
                    gl.glVertex3d(-1, 1, 1);

                    gl.glTexCoord2d(0.0, 1.0/3.0);
                    gl.glVertex3d(-1, -1, 1);

                    gl.glTexCoord2d(1.0/4.0, 1.0/3.0);
                    gl.glVertex3d(-1, -1, -1);

                    gl.glTexCoord2d(1.0/4.0, 2.0/3.0);
                    gl.glVertex3d(-1, 1, -1);

                // Back face
                    gl.glTexCoord2d(1.0/4.0, 2.0/3.0);
                    gl.glVertex3d(-1, 1, -1);

                    gl.glTexCoord2d(1.0/4.0, 1.0/3.0);
                    gl.glVertex3d(-1, -1, -1);

                    gl.glTexCoord2d(2.0/4.0, 1.0/3.0);
                    gl.glVertex3d(1, -1, -1);

                    gl.glTexCoord2d(2.0/4.0, 2.0/3.0);
                    gl.glVertex3d(1, 1, -1);

                // Right face
                    gl.glTexCoord2d(2.0/4.0, 2.0/3.0);
                    gl.glVertex3d(1, 1, -1);

                    gl.glTexCoord2d(2.0/4.0, 1.0/3.0);
                    gl.glVertex3d(1, -1, -1);

                    gl.glTexCoord2d(3.0/4.0, 1.0/3.0);
                    gl.glVertex3d(1, -1, 1);

                    gl.glTexCoord2d(3.0/4.0, 2.0/3.0);
                    gl.glVertex3d(1, 1, 1);

                // Front face
                    gl.glTexCoord2d(3.0/4.0, 2.0/3.0);
                    gl.glVertex3d(1, 1, 1);

                    gl.glTexCoord2d(3.0/4.0, 1.0/3.0);
                    gl.glVertex3d(1, -1, 1);

                    gl.glTexCoord2d(1.0, 1.0/3.0);
                    gl.glVertex3d(-1, -1, 1);

                    gl.glTexCoord2d(1.0, 2.0/3.0);
                    gl.glVertex3d(-1, 1, 1);


                // Bottom face
                    gl.glTexCoord2d(1.0/4.0, 1.0/3.0);
                    gl.glVertex3d(-1, -1, -1);

                    gl.glTexCoord2d(1.0/4.0, 0.0);
                    gl.glVertex3d(-1, -1, 1);

                    gl.glTexCoord2d(2.0/4.0, 0.0);
                    gl.glVertex3d(1, -1, 1);

                    gl.glTexCoord2d(2.0/4.0, 1.0/3.0);
                    gl.glVertex3d(1, -1, -1);
            gl.glEnd();

        gl.glPopMatrix();

        texture.disable(gl);

    }

}
