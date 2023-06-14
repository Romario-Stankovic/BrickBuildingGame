package rs.ac.singidunum.engine.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Model loader class
// Loads models from .obj files
public class ModelLoader {

    // List of loaded models (cache)
    private static final Map<String, Mesh> loadedModels = new HashMap<>();

    // Load a model from a file
    public static Mesh load(String path) {

        // Check if the model is already loaded
        if(loadedModels.containsKey(path)) {
            // If it is, return it
            return loadedModels.get(path);
        }

        // Try to load the model
        try {
            // Create a new mesh
            Mesh mesh = new Mesh();

            // Open the file
            InputStream model = Object.class.getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(model));

            // Read the file line by line
            String line;
            while ((line = reader.readLine()) != null) {

                // Split the line by spaces and check the first token
                String[] tokens = line.split("\\s+");
                switch(tokens[0]) {
                    // Vertex
                    case "v":
                        double x = Double.parseDouble(tokens[1]);
                        double y = Double.parseDouble(tokens[2]);
                        double z = Double.parseDouble(tokens[3]);
                        mesh.getVertices().add(new Vector3(x, y, z));
                        break;
                    // Normal
                    case "vn":
                        double nx = Double.parseDouble(tokens[1]);
                        double ny = Double.parseDouble(tokens[2]);
                        double nz = Double.parseDouble(tokens[3]);
                        mesh.getNormals().add(new Vector3(nx, ny, nz));
                        break;
                    // UV
                    case "vt":
                        double u = Double.parseDouble(tokens[1]);
                        double v = Double.parseDouble(tokens[2]);
                        mesh.getUvs().add(new Vector2(u, v));
                        break;
                    // Face
                    case "f":
                        int[][] face = new int[3][3];
                        for(int i = 0; i < 3; i++) {
                            String[] parts = tokens[i + 1].split("/");

                            face[i][0] = Integer.parseInt(parts[0]) - 1;
                            face[i][1] = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) - 1 : -1;
                            face[i][2] = parts.length > 2 && !parts[2].isEmpty() ? Integer.parseInt(parts[2]) - 1 : -1;
                        }
                        mesh.getFaces().add(face);
                        break;
                }

            }
            // Close the file
            reader.close();
            // Cache the model
            loadedModels.put(path, mesh);
            // Return the model
            return mesh;
        } catch (IOException e) {
            // If an error occurs, throw a runtime exception
            throw new RuntimeException(e);
        }
    }

}
