package rs.ac.singidunum.game.scripts.factories;

import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.util.Material;

// MaterialFactory generates default materials
public class MaterialFactory {

    public static Material getDefaultMaterial() {
        // Create a new material
        Material material = new Material();
        // Set main color
        material.setMainColor(new Color(200, 200, 200));
        // Set ambient color
        material.setAmbientColor(new Color(128, 128, 128));
        // Set shininess
        material.setShininess(32f);
        // return the material
        return material;
    }

}
