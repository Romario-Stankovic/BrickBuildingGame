package rs.ac.singidunum.game.scripts;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.newt.event.KeyEvent;

import lombok.Getter;
import rs.ac.singidunum.engine.Input;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.MeshRenderer;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.util.Material;
import rs.ac.singidunum.engine.util.Mesh;
import rs.ac.singidunum.engine.util.Vector3;
import rs.ac.singidunum.game.scripts.factories.MaterialFactory;

// Player components
// Defines the player controls
public class Player extends Behavior {

    // Reference to the players mesh renderer
    private MeshRenderer myRenderer = null;
    // The current selected brickID
    private int brickId = 0;
    // The current selected materialId
    private int materialId = 0;
    // Reference to the GameManager
    private GameManager gameManager = null;

    // List of placed bricks
    @Getter
    private final List<Brick> bricks = new LinkedList<>();

    private void initializeInput() {

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_A, (args) -> {
            // If the player cannot move left, return
            if(this.getTransform().getPosition().getX() <= -8) {
                return;
            }
            // Move the player 1 unit to the left
            this.getTransform().move(new Vector3(-1, 0, 0));
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_D, (args) -> {
            // If the player cannot move right, return
            if(this.getTransform().getPosition().getX() >= 8) {
                return;
            }
            // Move the player 1 unit to the right
            this.getTransform().getPosition().add(new Vector3(1, 0, 0));
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_W, (args) -> {
            // If the player cannot move forward (negative Z), return
            if(this.getTransform().getPosition().getZ() <= -8) {
                return;
            }
            // Move the player 1 unit forward
            this.getTransform().getPosition().add(new Vector3(0, 0, -1));
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_S, (args) -> {
            // If the player cannot move backward (positive Z), return
            if(this.getTransform().getPosition().getZ() >= 8) {
                return;
            }
            // Move the player 1 unit backwards
            this.getTransform().getPosition().add(new Vector3(0, 0, 1));
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_Q, (args) -> {
            // If the player cannot move down, return
            if(this.getTransform().getPosition().getY() <= 1.2) {
                return;
            }
            // Move the player 1.2 units down (size of a brick)
            this.getTransform().getPosition().add(new Vector3(0, -1.2, 0));
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_E, (args) -> {
            // If the player cannot move up, return
            if(this.getTransform().getPosition().getY() >= 12) {
                return;
            }
            // Move the player 1.2 units up (size of a brick)
            this.getTransform().getPosition().add(new Vector3(0, 1.2, 0));
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_R, (args) -> {
            // Rotate the player by 90 degrees
            this.getTransform().getRotation().add(new Vector3(0, 90, 0));
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_1, (args) -> {
            if(brickId == 0) {
                // If the current brick is the first, go to the last brick
                brickId = gameManager.getBrickMeshes().size() - 1;
            } else {
                // Go to the previous brick
                brickId--;
            }
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_2, (args) -> {
            if(brickId == gameManager.getBrickMeshes().size() - 1) {
                // If the current brick is the last one, go to the first
                brickId = 0;
            } else {
                // Go to the next brick
                brickId++;
            }
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_3, (args) -> {
            if(materialId == 0) {
                //If the current material is the first, go to the last
                materialId = gameManager.getMaterials().size() - 1;
            } else {
                // Go to the previous material
                materialId--;
            }
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_4, (args) -> {
            if(materialId == gameManager.getMaterials().size() - 1) {
                // If the current material is the last, go to the first
                materialId = 0;
            } else {
                // Go to the next brick
                materialId++;
            }
        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_SPACE, (args) -> {
            // Check if the current gameObject is active
            if(!getGameObject().isActive()) {
                // If not, return
                return;
            }
            // Place the current selected brick
            placeCurrentBrick();

        });

        // Register onKeyDown event
        Input.onKeyDown(KeyEvent.VK_BACK_SPACE, (args) -> {

            // Check if the current gameObject is active
            if(!getGameObject().isActive()) {
                // If not, return
                return;
            }

            // If there are no bricks to delete, return
            if(this.bricks.size() == 0) {
                return;
            }

            // Remove the last brick
            Brick brick = this.bricks.remove(this.bricks.size() - 1);

            // Destroy the gameObject
            brick.getGameObject().destroy();

        });

    }

    private void placeCurrentBrick() {

        // Get the current selected brick mesh
        Mesh brickMesh = this.gameManager.getBrickMeshes().get(brickId);
        // Get the current selected brick material
        Material placedMaterial = this.gameManager.getMaterials().get(materialId);

        // Create a new MeshRenderer and set mesh and material
        MeshRenderer renderer = new MeshRenderer();
        renderer.setMesh(brickMesh);
        renderer.setMaterial(placedMaterial);

        // Create a gameObject for the brick (give it a unique name)
        GameObject go = new GameObject("Brick:" + bricks.size());
        // Add the meshRenderer component
        go.addComponent(renderer);

        // Set the position and rotation of the brick to the players position and rotation
        go.getTransform().setPosition(new Vector3(getTransform().getPosition()));
        go.getTransform().setRotation(new Vector3(getTransform().getRotation()));

        // Set the parent of the brick to be the Plate gameObject
        go.setParent(GameObject.findGameObject("Plate"));

        // Create a new brick instance
        Brick brick = new Brick();
        // Set the brickId and materialId to the current selected brick and material
        brick.setBrickId(brickId);
        brick.setMaterialId(materialId);
        // Set the position and rotation of the Brick to the current players position and rotation 
        brick.setPosition(new Vector3(getTransform().getPosition()));
        brick.setRotation(new Vector3(getTransform().getRotation()));
        // Set the gameObject of the brick
        brick.setGameObject(go);

        // Add the brick to the list of placed bricks
        this.bricks.add(brick);

    }

    public void reset() {

        // Reset the selected brick and material to 0
        this.brickId = 0;
        this.materialId = 0;

        // Reset players position
        this.getTransform().setPosition(new Vector3(8, 1.2, 8));

        // Destroy all placed bricks
        for (Brick brick : this.bricks) {
            brick.getGameObject().destroy();
        }

        // Clear the placed bricks list
        this.bricks.clear();

    }

    @Override
    public void start() {

        // Get the renderer attached to the player component
        myRenderer = getGameObject().getComponent(MeshRenderer.class);

        // Get the GameManager
        gameManager = GameObject.findGameObject("Scene").getComponent(GameManager.class);

        // Initialize the inputs
        initializeInput();

        // Set the selected brick and material Ids to 0
        this.brickId = 0;
        this.materialId = 0;

        // Set the initial player position
        this.getTransform().setPosition(new Vector3(8, 1.2, 8));

    }

    @Override
    public void update(double delta) {

        // Get the current selected brick mesh
        Mesh mesh = gameManager.getBrickMeshes().get(brickId);

        // Get the color of the current selected material
        Color color = new Color(gameManager.getMaterials().get(materialId).getMainColor());

        // Set the alpha channel for the color
        color.setAlpha(180);

        // Get a default material
        Material material = MaterialFactory.getDefaultMaterial();
        // Set the color of the material
        material.setMainColor(color);

        // Set the mesh and material on the renderer
        myRenderer.setMesh(mesh);
        myRenderer.setMaterial(material);

    }

}
