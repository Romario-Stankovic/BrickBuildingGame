package rs.ac.singidunum.engine.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
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

// MeshRenderer component
// Renders a mesh with a material
public class MeshRenderer extends Behavior implements IRenderable {

    // Mesh to render
    @Getter
    @Setter
    private Mesh mesh;

    // Material to use
    @Getter
    @Setter
    private Material material;

    // Stack of transforms
    Stack<Transform> transforms;

    // Empty constructor
    public MeshRenderer() {
        // Initialize the stack
        this.transforms = new Stack<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {
        // Render the mesh
        this.render(Engine.getInstance().getDrawable());
    }


    @Override
    public void render(GLAutoDrawable drawable) {

        // Check if the mesh is null
        if(this.mesh == null) {
            // If it is, return
            return;
        }

        // Get the GL2 instance
        final GL2 gl = drawable.getGL().getGL2();

        // Push the transforms onto the stack
        GameObject current = this.getGameObject();
        while(current != null) {
            transforms.push(current.getTransform());
            current = current.getParent();
        }

        // Get the size of the stack
        final int stackSize = transforms.size();

        // Transform the mesh using the transforms from the stack
        while(!transforms.empty()) {
            // Push the matrix
            gl.glPushMatrix();
            // Get the last added transform
            Transform transform = transforms.pop();
            // Apply translation, rotation and scale
            gl.glTranslated(transform.getPosition().getX(), transform.getPosition().getY(), transform.getPosition().getZ());
            gl.glRotated(transform.getRotation().getX(), 1, 0, 0);
            gl.glRotated(transform.getRotation().getY(), 0, 1, 0);
            gl.glRotated(transform.getRotation().getZ(), 0, 0, 1);
            gl.glScaled(transform.getScale().getX(), transform.getScale().getY(), transform.getScale().getZ());
        }

        // Get the mesh data
        List<int[][]> faces = mesh.getFaces(); // 3 vertices, 3 uvs, 3 normals
        List<Vector3> vertices = mesh.getVertices();
        List<Vector3> normals = mesh.getNormals();
        List<Vector2> uvs = mesh.getUvs();

        // Check if the material is null
        if(material == null) {
            throw new RuntimeException("There is no material attached to MeshRenderer on GameObject " + this.getGameObject().getName());
        }

        // Check if the material has a texture
        if(material.getTexture() != null) {
            // Enable and bind the texture
            material.getTexture().enable(gl);
            material.getTexture().bind(gl);
        }

        // Apply the material
        material.apply();

        // Render the mesh
        gl.glBegin(GL2.GL_TRIANGLES);

        // Loop through all the faces
        for(int[][] face : faces) {

            // Loop through all the vertices
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

        // End the rendering
        gl.glEnd();

        // Pop the matrices off the stack
        for(int i = 0; i < stackSize; i++) {
            gl.glPopMatrix();
        }

        // Disable the texture if it exists
        if(material.getTexture() != null) {
            material.getTexture().disable(gl);
        }

    }
}
