package rs.ac.singidunum.engine.util;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Mesh {

    private final List<Vector3> vertices;
    private final List<Vector3> normals;
    private final List<Vector2> uvs;
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
