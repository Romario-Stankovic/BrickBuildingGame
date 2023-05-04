package rs.ac.singidunum.engine.util;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import lombok.Getter;
import lombok.Setter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.interfaces.IRenderable;
import rs.ac.singidunum.engine.util.Vector2;
import rs.ac.singidunum.engine.util.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Mesh {

    @Getter
    private final List<Vector3> vertices;
    @Getter
    private final List<Vector3> normals;
    @Getter
    private final List<Vector2> uvs;
    @Getter
    private final List<int[][]> faces;

    public Mesh() {
        vertices = new ArrayList<>();
        normals = new ArrayList<>();
        uvs = new ArrayList<>();
        faces = new ArrayList<>();
    }

    public Mesh(List<Vector3> vertices, List<Vector3> normals, List<Vector2> uvs, List<int[][]> faces) {
        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        this.faces = faces;
    }

}
