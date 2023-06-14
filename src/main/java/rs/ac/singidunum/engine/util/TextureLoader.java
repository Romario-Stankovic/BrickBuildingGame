package rs.ac.singidunum.engine.util;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

// Texture loader class
public class TextureLoader {

    // Loaded textures (cache)
    private static final Map<String, Texture> loadedTextures = new HashMap<>();

    // Load method
    public static Texture load(String path) {

        // Check if the texture is already loaded
        if(loadedTextures.containsKey(path)) {
            // If it is, return it
            return loadedTextures.get(path);
        }

        // Try to load the texture
        try {
            // Load the texture
            URL url = Object.class.getResource(path);
            Texture texture = TextureIO.newTexture(url, true, "png");
            // Add it to the cache
            loadedTextures.put(path, texture);
            // Return it
            return texture;
        } catch (IOException e) {
            // If the texture can't be loaded, throw an exception
            throw new RuntimeException(e);
        }

    }

}
