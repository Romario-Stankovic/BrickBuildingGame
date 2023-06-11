package rs.ac.singidunum.engine.util;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureLoader {

    private static final Map<String, Texture> loadedTextures = new HashMap<>();

    public static Texture load(String path) {

        if(loadedTextures.containsKey(path)) {
            return loadedTextures.get(path);
        }

        try {
            URL url = Object.class.getResource(path);
            Texture texture = TextureIO.newTexture(url, true, "png");
            loadedTextures.put(path, texture);
            return texture;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
