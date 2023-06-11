package rs.ac.singidunum.engine.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ModelLoader {

    private static final Map<String, Mesh> loadedModels = new HashMap<>();

    public static Mesh load(String path) {

        if(loadedModels.containsKey(path)) {
            return loadedModels.get(path);
        }

        try {
            Mesh mesh = new Mesh();
            InputStream model = Object.class.getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(model));

            String line;
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split("\\s+");
                switch(tokens[0]) {
                    case "v":
                        double x = Double.parseDouble(tokens[1]);
                        double y = Double.parseDouble(tokens[2]);
                        double z = Double.parseDouble(tokens[3]);
                        mesh.getVertices().add(new Vector3(x, y, z));
                        break;
                    case "vn":
                        double nx = Double.parseDouble(tokens[1]);
                        double ny = Double.parseDouble(tokens[2]);
                        double nz = Double.parseDouble(tokens[3]);
                        mesh.getNormals().add(new Vector3(nx, ny, nz));
                        break;
                    case "vt":
                        double u = Double.parseDouble(tokens[1]);
                        double v = Double.parseDouble(tokens[2]);
                        mesh.getUvs().add(new Vector2(u, v));
                        break;
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
            reader.close();
            loadedModels.put(path, mesh);
            return mesh;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
