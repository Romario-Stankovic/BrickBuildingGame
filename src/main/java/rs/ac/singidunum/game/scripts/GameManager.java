package rs.ac.singidunum.game.scripts;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import lombok.Getter;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.MeshRenderer;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.util.Material;
import rs.ac.singidunum.engine.util.Mesh;
import rs.ac.singidunum.engine.util.ModelLoader;
import rs.ac.singidunum.engine.util.Vector3;
import rs.ac.singidunum.game.scripts.factories.MaterialFactory;

public class GameManager extends Behavior {

    private Player player = null;

    @Getter()
    private final List<Mesh> bricks = new ArrayList<>();
    @Getter()
    private final List<Mesh> brickOutlines = new ArrayList<>();
    @Getter()
    private final List<Material> materials = new ArrayList<>();

    private final List<Brick> correctBricks = new ArrayList<>();

    public void reset() {

        for(Brick brick : correctBricks) {
            brick.getGameObject().destroy();
        }

        correctBricks.clear();
        player.getGameObject().setActive(true);
        player.reset();
    }

    public void newGame() {

        reset();

        Path path = Paths.get("shapes");

        if (!path.toFile().exists()) {
            path.toFile().mkdir();
        }

        List<String> shapeNames = new ArrayList<>();

        try {
            Files.walk(path).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    shapeNames.add(filePath.getFileName().toString().replace(".json", ""));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        int idx = new Random().nextInt(shapeNames.size());

        Shape loadedShape = Shape.loadShape(shapeNames.get(idx));

        List<Brick> bricks = loadedShape.getBricks();

        for (int i = 0; i < bricks.size(); i++) {
            Mesh mesh = this.brickOutlines.get(bricks.get(i).getBrickId());
            Color color = new Color(this.materials.get(bricks.get(i).getMaterialId()).getMainColor());
            color.setAlpha(128);
            Material material = MaterialFactory.getDefaultMaterial();
            material.setMainColor(color);

            GameObject gameObject = new GameObject("CorrectBrick:" + i + 1);
            MeshRenderer renderer = gameObject.addComponent(new MeshRenderer());
            renderer.setMesh(mesh);
            renderer.setMaterial(material);

            gameObject.getTransform().setPosition(bricks.get(i).getPosition());
            gameObject.getTransform().setRotation(bricks.get(i).getRotation());
            gameObject.getTransform().setScale(new Vector3(0.99, 0.99, 0.99));
            gameObject.setParent(GameObject.findGameObject("Scene"));
            bricks.get(i).setGameObject(gameObject);
            this.correctBricks.add(bricks.get(i));
        }

    }

    public void finishGame() {

        List<Brick> playerBricks = player.getBricks();

        int matched = 0;

        for(Brick correctBrick : correctBricks) {

            for(Brick playerBrick : playerBricks) {

                if(correctBrick.equals(playerBrick)) {
                    matched++;
                    break;
                }

            }

        }

        int totalCorrect = correctBricks.size();
        int totalPlaced = playerBricks.size();
        int misplaced = (totalPlaced - matched);

        int total = (totalCorrect + misplaced);
        double score = ((double)matched / total) * 100;

        String message = String.format("Score: %d/100\nCorrect: %d/%d\nMisplaced: %d", (int)score, matched, totalCorrect, misplaced);

        JOptionPane.showMessageDialog(Engine.getInstance().getFrame(), message, null, JOptionPane.INFORMATION_MESSAGE);
 
        player.getGameObject().setActive(false);

    }

    public void showHelp() {

        String message = 
        "<html>"
        +   "<body>"
        +       "<div width='250px'>"
        +           "<p align='center'>----- Computer Graphics Final Exam -----</p><br>"
        +           "<p>Created by: Romario Stanković - 2020230210<p><br>"
        +           "<br>"
        +           "<p align='center'>----- HOW TO PLAY -----<p>"
        +           "<p>W/A/S/D - Move the player left/right/forward/backward</p>"
        +           "<p>Q/E - Move the player up/down</p>"
        +           "<p>1/2 - Switch between bricks</p>"
        +           "<p>3/4 - Switch between colors</p>"
        +           "<p>R - Rotate the shape by 90\u00B0</p>"
        +           "<p>Backspace - Remove the last placed brick</p>"
        +           "<p>Space - Place the current selected brick</p>"
        +           "<p>LMB + Mouse - Orbit the camera around the scene</p>"
        +           "<p>Mouse Wheel - Zoom in/out</p>"
        +       "</div>"
        +   "</body>"
        +"</html>";

        JLabel label = new JLabel(message);

        JOptionPane.showMessageDialog(Engine.getInstance().getFrame(), label, "Help", JOptionPane.INFORMATION_MESSAGE);

    }

    public void newEmptyScene() {
        reset();
    }

    public void saveShape() {

        String name = JOptionPane.showInputDialog(Engine.getInstance().getFrame(), "Enter shape name:");

        if (name == null) {
            return;
        }

        Path path = Paths.get("shapes");

        if (!path.toFile().exists()) {
            path.toFile().mkdir();
        }

        Shape shape = new Shape();

        List<Brick> bricks = player.getBricks();

        for (Brick brick : bricks) {
            shape.addBrick(brick);
        }

        shape.saveShape(name);

    }

    public void loadShape() {

        String name = JOptionPane.showInputDialog(Engine.getInstance().getFrame(), "Enter shape name:");

        if(name == null) {
            return;
        }

        Path path = Paths.get("shapes/" + name + ".json");

        if(!path.toFile().exists()) {
            JOptionPane.showMessageDialog(Engine.getInstance().getFrame(), "Shape with that name does not exist!");
            return;
        }

        reset();

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

            bricks.get(i).setGameObject(gameObject);
            player.getBricks().add(bricks.get(i));
        }
    }

    @Override
    public void start() {

        Mesh brick2x2 = ModelLoader.load("/models/brick_2x2.obj");
        Mesh brick2x4 = ModelLoader.load("/models/brick_2x4.obj");
        Mesh brick2x8 = ModelLoader.load("/models/brick_2x8.obj");

        Mesh brick2x2Outline = ModelLoader.load("/models/brick_2x2_outline.obj");
        Mesh brick2x4Outline = ModelLoader.load("/models/brick_2x4_outline.obj");
        Mesh brick2x8Outline = ModelLoader.load("/models/brick_2x8_outline.obj");

        this.bricks.add(brick2x2);
        this.bricks.add(brick2x4);
        this.bricks.add(brick2x8);

        this.brickOutlines.add(brick2x2Outline);
        this.brickOutlines.add(brick2x4Outline);
        this.brickOutlines.add(brick2x8Outline);

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
