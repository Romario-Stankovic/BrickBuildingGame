package rs.ac.singidunum.util;

import rs.ac.singidunum.components.Mesh;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {

    public static Mesh loadModel(String path) {

        BufferedReader reader = null;
        Mesh mesh = new Mesh();

        try {
            InputStream model = Object.class.getResourceAsStream(path);
            reader = new BufferedReader(new InputStreamReader(model));

            String line;
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split("\\s+");
                switch(tokens[0]) {

                    case "v":
                        double x = Double.parseDouble(tokens[1]);
                        double y = Double.parseDouble(tokens[2]);
                        double z = Double.parseDouble(tokens[3]);
                        Vector3 vertex = new Vector3(x, y, z);
                        mesh.getVertices().add(vertex);
                        break;
                    case "vn":
                        double nx = Double.parseDouble(tokens[1]);
                        double ny = Double.parseDouble(tokens[2]);
                        double nz = Double.parseDouble(tokens[3]);
                        Vector3 normal = new Vector3(nx, ny, nz);
                        mesh.getNormals().add(normal);
                        break;
                    case "vt":
                        double u = Double.parseDouble(tokens[1]);
                        double v = Double.parseDouble(tokens[2]);
                        Vector2 uv = new Vector2(u, v);
                        mesh.getUvs().add(uv);
                        break;
                    case "f":
                        List<Vector3> face = new ArrayList<>();

                        for(int i = 0; i < 3; i++) {
                            String[] parts = tokens[i + 1].split("/");

                            face.add(new Vector3(
                                    Integer.parseInt(parts[0]) - 1,
                                    parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) - 1 : -1,
                                    parts.length > 2 && !parts[2].isEmpty() ? Integer.parseInt(parts[2]) - 1 : -1
                            ));


                        }
                        mesh.getFaces().add(face);
                        break;
                }

            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return mesh;
    }

}
