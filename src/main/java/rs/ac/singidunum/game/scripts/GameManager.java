package rs.ac.singidunum.game.scripts;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lombok.Getter;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.MeshRenderer;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.util.Material;
import rs.ac.singidunum.engine.util.Mesh;
import rs.ac.singidunum.engine.util.ModelLoader;
import rs.ac.singidunum.game.scripts.factories.MaterialFactory;

public class GameManager extends Behavior {

    private Player player = null;

    @Getter()
    private final List<Mesh> bricks = new ArrayList<>();
    @Getter()
    private final List<Mesh> brickOutlines = new ArrayList<>();
    @Getter()
    private final List<Material> materials = new ArrayList<>();

    public void newGame() {

        player.getGameObject().setActive(true);
        player.reset();

    }

    public void newEmptyScene() {

        player.getGameObject().setActive(true);
        player.reset();

    }

    public void saveShape() {

        JFrame frame = new JFrame("Shape name");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String name = JOptionPane.showInputDialog(frame, "Enter shape name:");
        frame.dispose();

        if (name == null) {
            return;
        }

        Path path = Paths.get("shapes");

        if (!path.toFile().exists()) {
            path.toFile().mkdir();
        }

        Shape shape = new Shape();

        List<GameObject> bricks = player.getBricks();

        for (GameObject brick : bricks) {
            Mesh mesh = brick.getComponent(MeshRenderer.class).getMesh();
            int meshId = this.bricks.indexOf(mesh);
            Material material = brick.getComponent(MeshRenderer.class).getMaterial();
            int materialId = this.materials.indexOf(material);

            shape.addBrick(
                meshId,
                materialId,
                brick.getTransform().getPosition(),
                brick.getTransform().getRotation());
        }

        shape.saveShape(name);

    }

    public void loadShape() {

        JFrame frame = new JFrame("Shape name");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String name = JOptionPane.showInputDialog(frame, "Enter shape name:");
        frame.dispose();

        Path path = Paths.get("shapes/" + name + ".json");

        if(!Files.exists(path)) {
            JOptionPane.showMessageDialog(null, "Shape with that name does not exist!");
            return;
        }

        Shape loadedShape = Shape.loadShape(name);

        List<Brick> bricks = loadedShape.getBricks();

        for(int i = 0; i < bricks.size(); i++) {
            Mesh mesh = this.bricks.get(bricks.get(i).getBrickId());
            Material material = this.materials.get(bricks.get(i).getMaterialId());

            GameObject gameObject = new GameObject("Brick:" + i + 1);
            MeshRenderer renderer = gameObject.addComponent(new MeshRenderer());
            renderer.setMesh(mesh);
            renderer.setMaterial(material);

            gameObject.getTransform().setPosition(bricks.get(i).getPosition());
            gameObject.getTransform().setRotation(bricks.get(i).getRotation());
            gameObject.setParent(GameObject.findGameObject("Plate"));
            player.getBricks().add(gameObject);
        }

        player.getGameObject().setActive(true);

    }

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

        player = GameObject.findGameObject("Player").getComponent(Player.class);

    }

    @Override
    public void update(double delta) {

    }

}
