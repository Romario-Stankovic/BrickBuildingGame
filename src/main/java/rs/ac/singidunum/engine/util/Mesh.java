package rs.ac.singidunum.engine.util;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

// Mesh class
// Holds mesh data
@Getter
public class Mesh {

    // Mesh data
    private final List<Vector3> vertices;
    private final List<Vector3> normals;
    private final List<Vector2> uvs;
    private final List<int[][]> faces;

    // Empty constructor
    public Mesh() {
        // Initialize the lists
        vertices = new ArrayList<>();
        normals = new ArrayList<>();
        uvs = new ArrayList<>();
        faces = new ArrayList<>();
    }

    // Constructor with parameters
    public Mesh(List<Vector3> vertices, List<Vector3> normals, List<Vector2> uvs, List<int[][]> faces) {
        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        this.faces = faces;
    }

}
