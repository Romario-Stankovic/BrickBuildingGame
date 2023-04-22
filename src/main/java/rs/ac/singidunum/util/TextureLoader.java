package rs.ac.singidunum.util;

import java.io.IOException;
import java.net.URL;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureLoader {
    
    public static Texture loadTexture(String path) {
        
        URL url;
        try {
            url = Object.class.getResource(path);
            return TextureIO.newTexture(url, true, "png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
