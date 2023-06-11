package rs.ac.singidunum.game.scripts.factories;

import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.util.Material;

public class MaterialFactory {

    public static Material getDefaultMaterial() {
        Material material = new Material();
        material.setMainColor(new Color(200, 200, 200));
        material.setAmbientColor(new Color(128, 128, 128));
        material.setShininess(32f);
        return material;
    }

}
