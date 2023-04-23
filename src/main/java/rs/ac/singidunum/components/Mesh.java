package rs.ac.singidunum.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.Game;
import rs.ac.singidunum.interfaces.IRenderable;
import rs.ac.singidunum.util.Vector2;
import rs.ac.singidunum.util.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Mesh extends Behavior implements IRenderable {
    @Getter
    private List<Vector3> vertices;
    @Getter
    private List<Vector3> normals;
    @Getter
    private List<Vector2> uvs;
    @Getter
    private List<List<Vector3>> faces;

    @Getter
    @Setter
    private Texture texture;

    Stack<Transform> transforms;

    public Mesh() {
        vertices = new ArrayList<>();
        normals = new ArrayList<>();
        uvs = new ArrayList<>();
        faces = new ArrayList<>();
        transforms = new Stack<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {
        this.render(Game.getDrawable());
    }

    @Override
    public void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Get all transforms from the parent GameObjects
        GameObject current = this.getGameObject();
        while(current != null) {
            transforms.push(current.getTransform());
            current = current.getParent();
        }
        // Save the size of the stack
        int stackSize = transforms.size();

        // Render the mesh
        if(texture != null) {
            texture.enable(gl);
            texture.bind(gl);
        }

        while(!transforms.empty()) {
            gl.glPushMatrix();
            Transform transform = transforms.pop();
            gl.glTranslated(transform.getPosition().getX(), transform.getPosition().getY(), transform.getPosition().getZ());
            gl.glRotated(transform.getRotation().getX(), 1, 0, 0);
            gl.glRotated(transform.getRotation().getY(), 0, 1, 0);
            gl.glRotated(transform.getRotation().getZ(), 0, 0, 1);
            gl.glScaled(transform.getScale().getX(), transform.getScale().getY(), transform.getScale().getZ());
        }

        gl.glBegin(GL2.GL_TRIANGLES);
        for(List<Vector3> face : faces) {
                for(int i = 0; i < 3; i++) {
                    // Check if the face has a UV component
                    if(face.get(i).getY() >= 0) {
                        gl.glTexCoord2d(uvs.get((int) face.get(i).getY()).getX(), uvs.get((int) face.get(i).getY()).getY());
                    }

                    // Check if the face has a normal component
                    if(face.get(i).getZ() >= 0) {
                        gl.glNormal3d(normals.get((int) face.get(i).getZ()).getX(), normals.get((int) face.get(i).getZ()).getY(), normals.get((int) face.get(i).getZ()).getZ());
                    }

                    // Render the vertex
                    gl.glVertex3d(vertices.get((int) face.get(i).getX()).getX(), vertices.get((int) face.get(i).getX()).getY(), vertices.get((int) face.get(i).getX()).getZ());
                }
        }
        gl.glEnd();

        // Pop the matrices off the stack
        for(int i = 0; i < stackSize; i++) {
            gl.glPopMatrix();
        }

        if(texture != null) {
            texture.disable(gl);
        }

    }

}
