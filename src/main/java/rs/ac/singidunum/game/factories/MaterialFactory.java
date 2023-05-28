package rs.ac.singidunum.game.factories;

import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.util.Material;

public class MaterialFactory {

    public static Material getDefaultMaterial() {
        Material material = new Material();
        material.setMainColor(new Color(255, 255, 255));
        material.setAmbientColor(new Color(128, 128, 128));
        material.setShininess(32f);
        return material;
    }

}
