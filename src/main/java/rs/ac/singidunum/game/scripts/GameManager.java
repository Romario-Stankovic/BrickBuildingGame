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

// GameManager components
// Manages the state of the game
public class GameManager extends Behavior {

    // Reference to the player
    private Player player = null;

    // List of available brick Meshes
    @Getter()
    private final List<Mesh> brickMeshes = new ArrayList<>();

    // List of brick outlines
    @Getter()
    private final List<Mesh> brickOutlines = new ArrayList<>();

    // List of available materials for the bricks
    @Getter()
    private final List<Material> materials = new ArrayList<>();

    // Reference to the loaded shape
    private Shape shape = null;

    public void reset() {

        // Check if we have a loaded shape
        if(shape != null) {
            // Destroy the placed GameObjects
            for(Brick brick : shape.getBricks()) {
                brick.getGameObject().destroy();
            }
        }

        // Remove the shape reference
        shape = null;

        // Reset the player
        player.getGameObject().setActive(true);
        player.reset();
    }

    public void newGame() {

        // Reset the gameManager
        reset();

        // Get the path where shapes are stored
        final Path path = Paths.get("shapes");

        // If the folder does not exist, create it
        if (!path.toFile().exists()) {
            path.toFile().mkdir();
        }

        // List that stores the names of the shapes
        final List<String> shapeNames = new ArrayList<>();

        // Try to load the shapes
        try {
            // Go through the files
            Files.walk(path).forEach(file -> {
                // Check if the file is a regular file and ends with .json
                if (Files.isRegularFile(file) && file.getFileName().toString().endsWith(".json")) {
                    // Add the shape to the list
                    shapeNames.add(file.getFileName().toString());
                }
            });
        } catch (Exception e) {
            // If we fail to load the file, throw a runtime exception
            throw new RuntimeException(e);
        }

        // Get a random index from the shapes
        final int idx = new Random().nextInt(shapeNames.size());

        // Load a random file from the list
        final Shape loadedShape = Shape.loadShape(shapeNames.get(idx));

        // Get the bricks from the Shape
        List<Brick> bricks = loadedShape.getBricks();

        // Go through the bricks
        for (int i = 0; i < bricks.size(); i++) {
            // Get the bricks outline mesh
            Mesh mesh = this.brickOutlines.get(bricks.get(i).getBrickId());

            // Get the bricks color
            Color color = new Color(this.materials.get(bricks.get(i).getMaterialId()).getMainColor());
            // Set the transparency for the brick
            color.setAlpha(128);
            // Create a new material for the brick
            Material material = MaterialFactory.getDefaultMaterial();
            // Set the main color for the brick
            material.setMainColor(color);

            // Create a gameObject for the brick
            GameObject gameObject = new GameObject("CorrectBrick:" + i + 1);
            // Create a mesh renderer for the brick
            MeshRenderer renderer = gameObject.addComponent(new MeshRenderer());
            // Set the mesh
            renderer.setMesh(mesh);
            // Set the material
            renderer.setMaterial(material);

            // Set the position and transformation of the mesh
            gameObject.getTransform().setPosition(bricks.get(i).getPosition());
            gameObject.getTransform().setRotation(bricks.get(i).getRotation());
            gameObject.getTransform().setScale(new Vector3(0.99, 0.99, 0.99));
            gameObject.setParent(GameObject.findGameObject("Scene"));

            // Set the gameObject reference for the brick 
            bricks.get(i).setGameObject(gameObject);
        }

        // Set the shape
        shape = loadedShape;

    }

    public void finishGame() {

        // Don't allow the user to finish the game if there is no shape to match with
        if(shape == null){
            return;
        }

        // Count of matched bricks
        int matched = 0;

        // Go through all correct bricks
        for(Brick correctBrick : shape.getBricks()) {

            // Go through players placed bricks
            for(Brick playerBrick : player.getBricks()) {

                // If these two bricks match, count it
                if(correctBrick.equals(playerBrick)) {
                    matched++;
                    break;
                }

            }

        }

        // Get the number of correct bricks
        final int correctCorrect = shape.getBricks().size();
        // Get the number of player placed bricks
        final int totalPlaced = player.getBricks().size();
        // Get the number of misplaced bricks ()
        final int misplaced = (totalPlaced - matched);

        // The total number of bricks that are in the scene (correct and misplaced)
        final int total = (correctCorrect + misplaced);

        // The score of the player
        final int score = (int)(((double)matched / total) * 100);

        // Message to be displayed
        final String message = String.format("Score: %d/100\nCorrect: %d/%d\nMisplaced: %d", score, matched, correctCorrect, misplaced);

        // Show a popup window with the message
        JOptionPane.showMessageDialog(Engine.getInstance().getFrame(), message, null, JOptionPane.INFORMATION_MESSAGE);
 
        // Show a popup for a new game
        int choice = JOptionPane.showConfirmDialog(Engine.getInstance().getFrame(), "Do you wish to start a new game?", "New Game", JOptionPane.YES_OPTION);

        // Start a new game if the player clicks yes
        if(choice == 0) {
            newGame();
        }else {
            // Disable the player once the game ends
            player.getGameObject().setActive(false);
        }

    }

    public void showHelp() {
        // Help message
        final String message = 
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

        // Label that holds the help message (used to format the HTML)
        final JLabel label = new JLabel(message);

        // Show a popup window with the message
        JOptionPane.showMessageDialog(Engine.getInstance().getFrame(), label, "Help", JOptionPane.INFORMATION_MESSAGE);

    }

    public void newEditor() {
        // Create a empty scene by resetting the manager
        reset();
    }

    public void saveShape() {

        // Ask the user for a name for the shape
        final String name = JOptionPane.showInputDialog(Engine.getInstance().getFrame(), "Enter shape name:");

        // If the name is null, return
        if (name == null) {
            return;
        }

        // Path to the shapes folder
        final Path path = Paths.get("shapes");

        // Create the folder if it does not exist
        if (!path.toFile().exists()) {
            path.toFile().mkdir();
        }

        // Create a shape
        final Shape shape = new Shape();

        // Get player placed bricks
        final List<Brick> bricks = player.getBricks();

        // Place the player bricks into the shape
        for (Brick brick : bricks) {
            shape.addBrick(brick);
        }

        // Save the shape
        shape.saveShape(name);

    }

    public void loadShape() {

        // Ask the user to enter the shapes name
        final String name = JOptionPane.showInputDialog(Engine.getInstance().getFrame(), "Enter shape name:");

        // If the shape does not exist, return
        if(name == null) {
            return;
        }

        // Path to the file that needs to be loaded
        final Path path = Paths.get("shapes/" + name + ".json");

        // Check if the file exists
        if(!path.toFile().exists()) {
            // If not, show a message to the user
            JOptionPane.showMessageDialog(Engine.getInstance().getFrame(), "Shape with that name does not exist!");
            return;
        }

        // Reset the game manager
        reset();

        // Load the shape
        final Shape loadedShape = Shape.loadShape(name + ".json");

        // Get the bricks from the loaded shape
        List<Brick> bricks = loadedShape.getBricks();

        // Go through the bricks that are loaded
        for(int i = 0; i < bricks.size(); i++) {
            // Get the corresponding mesh for the brick
            Mesh mesh = this.brickMeshes.get(bricks.get(i).getBrickId());
            // Get the corresponding material for the brick
            Material material = this.materials.get(bricks.get(i).getMaterialId());

            // Create a gameObject for the loaded shape
            GameObject gameObject = new GameObject("Brick:" + i + 1);
            // Create a MeshRenderer for the brick
            MeshRenderer renderer = gameObject.addComponent(new MeshRenderer());
            // Add the mesh and material to the renderer
            renderer.setMesh(mesh);
            renderer.setMaterial(material);

            // Set the position and rotation of the brick
            gameObject.getTransform().setPosition(bricks.get(i).getPosition());
            gameObject.getTransform().setRotation(bricks.get(i).getRotation());
            // Set the parent for the brick
            gameObject.setParent(GameObject.findGameObject("Plate"));

            // Set the reference to the gameObject on the brick
            bricks.get(i).setGameObject(gameObject);
            // Set the loaded bricks on the player
            player.getBricks().add(bricks.get(i));
        }
    }

    @Override
    public void start() {
        // Load the brick meshes
        Mesh brick2x2 = ModelLoader.load("/models/brick_2x2.obj");
        Mesh brick2x4 = ModelLoader.load("/models/brick_2x4.obj");
        Mesh brick2x8 = ModelLoader.load("/models/brick_2x8.obj");

        // Load the outline models
        Mesh brick2x2Outline = ModelLoader.load("/models/brick_2x2_outline.obj");
        Mesh brick2x4Outline = ModelLoader.load("/models/brick_2x4_outline.obj");
        Mesh brick2x8Outline = ModelLoader.load("/models/brick_2x8_outline.obj");

        // Add the loaded meshes to the brick list
        this.brickMeshes.add(brick2x2);
        this.brickMeshes.add(brick2x4);
        this.brickMeshes.add(brick2x8);

        // Add the loaded outlines to the 
        this.brickOutlines.add(brick2x2Outline);
        this.brickOutlines.add(brick2x4Outline);
        this.brickOutlines.add(brick2x8Outline);
        
        // Create the red material
        Material red = MaterialFactory.getDefaultMaterial();
        red.setMainColor(new Color(221, 25, 32));

        // Create the green material
        Material green = MaterialFactory.getDefaultMaterial();
        green.setMainColor(new Color(0, 175, 77));

        // Create the blue material
        Material blue = MaterialFactory.getDefaultMaterial();
        blue.setMainColor(new Color(0, 108, 183));

        // Create the yellow material
        Material yellow = MaterialFactory.getDefaultMaterial();
        yellow.setMainColor(new Color(255, 205, 3));

        // Add the materials to the list
        this.materials.add(red);
        this.materials.add(green);
        this.materials.add(blue);
        this.materials.add(yellow);

        // Find the player
        player = GameObject.findGameObject("Player").getComponent(Player.class);

    }

    @Override
    public void update(double delta) {

    }

}
