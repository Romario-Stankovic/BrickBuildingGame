package rs.ac.singidunum.components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.singidunum.interfaces.IRenderable;
import rs.ac.singidunum.util.TextureLoader;
import rs.ac.singidunum.util.Vector2;
import rs.ac.singidunum.util.Vector3;

import java.util.ArrayList;
import java.util.List;


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

    public Mesh() {
        vertices = new ArrayList<>();
        normals = new ArrayList<>();
        uvs = new ArrayList<>();
        faces = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        if(texture != null) {
            texture.enable(gl);
            texture.bind(gl);
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


        if(texture != null) {
            texture.disable(gl);
        }

    }
}
