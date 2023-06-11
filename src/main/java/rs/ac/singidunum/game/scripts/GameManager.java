package rs.ac.singidunum.game.scripts;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.util.Material;
import rs.ac.singidunum.engine.util.Mesh;
import rs.ac.singidunum.engine.util.ModelLoader;
import rs.ac.singidunum.game.scripts.factories.MaterialFactory;

public class GameManager extends Behavior {

    private GameObject player = null;

    @Getter()
    private final List<Mesh> bricks = new ArrayList<>();
    @Getter()
    private final List<Mesh> brickOutlines = new ArrayList<>();
    @Getter()
    private final List<Material> materials = new ArrayList<>();

    @Override
    public void start() {

        Mesh brick2x2 = ModelLoader.load("/models/brick_2x2.obj");
        Mesh brick2x4 = ModelLoader.load("/models/brick_2x4.obj");

        Mesh brick2x2Outline = ModelLoader.load("/models/brick_2x2_outline.obj");
        Mesh brick2x4Outline = ModelLoader.load("/models/brick_2x4_outline.obj");

        this.bricks.add(brick2x2);
        this.bricks.add(brick2x4);

        this.brickOutlines.add(brick2x2Outline);
        this.brickOutlines.add(brick2x4Outline);

        Material red = MaterialFactory.getDefaultMaterial();
        red.setMainColor(new Color(221, 25, 32));

        Material green = MaterialFactory.getDefaultMaterial();
        green.setMainColor(new Color(0, 175, 77));

        Material blue = MaterialFactory.getDefaultMaterial();
        blue.setMainColor(new Color(0, 108, 183));

        Material yellow = MaterialFactory.getDefaultMaterial();
        yellow.setMainColor(new Color(255, 205, 3));

        this.materials.add(red);
        this.materials.add(green);
        this.materials.add(blue);
        this.materials.add(yellow);

        player = GameObject.findGameObject("Player");

    }

    @Override
    public void update(double delta) {

    }

    public void newGame() {

        player.setActive(true);
        player.getComponent(Player.class).reset();

    }

}
