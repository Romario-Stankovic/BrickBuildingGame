package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.interfaces.IRenderable;
import rs.ac.singidunum.engine.util.Material;
import rs.ac.singidunum.engine.util.Mesh;
import rs.ac.singidunum.engine.util.Vector2;
import rs.ac.singidunum.engine.util.Vector3;

import java.util.List;
import java.util.Stack;

public class MeshRenderer extends Behavior implements IRenderable {

    @Getter
    @Setter
    private Mesh mesh;

    @Getter
    @Setter
    private Material material;

    Stack<Transform> transforms;

    @Override
    public void start() {
        this.transforms = new Stack<>();
    }

    @Override
    public void update(double delta) {
        this.render(Engine.getDrawable());
    }


    @Override
    public void render(GLAutoDrawable drawable) {

        if(this.mesh == null) {
            return;
        }

        GL2 gl = drawable.getGL().getGL2();

        GameObject current = this.getGameObject();
        while(current != null) {
            transforms.push(current.getTransform());
            current = current.getParent();
        }

        int stackSize = transforms.size();

        while(!transforms.empty()) {
            gl.glPushMatrix();
            Transform transform = transforms.pop();
            gl.glTranslated(transform.getPosition().getX(), transform.getPosition().getY(), transform.getPosition().getZ());
            gl.glRotated(transform.getRotation().getX(), 1, 0, 0);
            gl.glRotated(transform.getRotation().getY(), 0, 1, 0);
            gl.glRotated(transform.getRotation().getZ(), 0, 0, 1);
            gl.glScaled(transform.getScale().getX(), transform.getScale().getY(), transform.getScale().getZ());
        }

        List<int[][]> faces = mesh.getFaces();
        List<Vector3> vertices = mesh.getVertices();
        List<Vector3> normals = mesh.getNormals();
        List<Vector2> uvs = mesh.getUvs();

        if(material == null) {
            throw new RuntimeException("There is no material attached to MeshRenderer on GameObject " + this.getGameObject().getName());
        }

        if(material.getTexture() != null) {
            material.getTexture().enable(gl);
            material.getTexture().bind(gl);
        }

        material.apply();

        gl.glBegin(GL2.GL_TRIANGLES);

        for(int[][] face : faces) {
            for(int i = 0; i < 3; i++) {
                // Check if the face has a UV component
                if(face[i][1] >= 0) {
                    gl.glTexCoord2d(uvs.get(face[i][1]).getX(), uvs.get(face[i][1]).getY());
                }

                // Check if the face has a normal component
                if(face[i][2] >= 0) {
                    gl.glNormal3d(normals.get(face[i][2]).getX(), normals.get(face[i][2]).getY(), normals.get(face[i][2]).getZ());
                }

                // Render the vertex
                gl.glVertex3d(vertices.get(face[i][0]).getX(), vertices.get(face[i][0]).getY(), vertices.get(face[i][0]).getZ());
            }
        }
        gl.glEnd();

        // Pop the matrices off the stack
        for(int i = 0; i < stackSize; i++) {
            gl.glPopMatrix();
        }

        if(material.getTexture() != null) {
            material.getTexture().disable(gl);
        }

    }
}
