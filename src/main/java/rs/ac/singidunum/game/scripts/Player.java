package rs.ac.singidunum.game.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.jogamp.newt.event.KeyEvent;

import rs.ac.singidunum.engine.Input;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.MeshRenderer;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Color;
import rs.ac.singidunum.engine.util.Material;
import rs.ac.singidunum.engine.util.Mesh;
import rs.ac.singidunum.engine.util.ModelLoader;
import rs.ac.singidunum.engine.util.Vector3;
import rs.ac.singidunum.game.scripts.factories.MaterialFactory;

public class Player extends Behavior {

    private MeshRenderer myRenderer = null;
    private int currentModel = 0;
    private int currentMaterial = 0;
    private GameManager gameManager = null;

    private final List<GameObject> bricks = new CopyOnWriteArrayList<>();

    private void initializeInput() {

        Input.onKeyDown(KeyEvent.VK_A, (args) -> {
            this.getTransform().getPosition().add(new Vector3(-1, 0, 0));
        });

        Input.onKeyDown(KeyEvent.VK_D, (args) -> {
            this.getTransform().getPosition().add(new Vector3(1, 0, 0));
        });

        Input.onKeyDown(KeyEvent.VK_W, (args) -> {
            this.getTransform().getPosition().add(new Vector3(0, 0, -1));
        });

        Input.onKeyDown(KeyEvent.VK_S, (args) -> {
            this.getTransform().getPosition().add(new Vector3(0, 0, 1));
        });

        Input.onKeyDown(KeyEvent.VK_Q, (args) -> {
            this.getTransform().getPosition().add(new Vector3(0, -1.2, 0));
        });

        Input.onKeyDown(KeyEvent.VK_E, (args) -> {
            this.getTransform().getPosition().add(new Vector3(0, 1.2, 0));
        });

        Input.onKeyDown(KeyEvent.VK_R, (args) -> {
            this.getTransform().getRotation().add(new Vector3(0, 90, 0));
        });

        Input.onKeyDown(KeyEvent.VK_1, (args) -> {
            if(currentModel == 0) {
                currentModel = gameManager.getBricks().size() - 1;
            } else {
                currentModel--;
            }
        });

        Input.onKeyDown(KeyEvent.VK_2, (args) -> {
            if(currentModel == gameManager.getBricks().size() - 1) {
                currentModel = 0;
            } else {
                currentModel++;
            }
        });

        Input.onKeyDown(KeyEvent.VK_3, (args) -> {
            if(currentMaterial == 0) {
                currentMaterial = gameManager.getMaterials().size() - 1;
            } else {
                currentMaterial--;
            }
        });

        Input.onKeyDown(KeyEvent.VK_4, (args) -> {
            if(currentMaterial == gameManager.getMaterials().size() - 1) {
                currentMaterial = 0;
            } else {
                currentMaterial++;
            }
        });

        Input.onKeyDown(KeyEvent.VK_SPACE, (args) -> {

            placeCurrentBrick();

        });

        Input.onKeyDown(KeyEvent.VK_BACK_SPACE, (args) -> {

            if(this.bricks.size() == 0) {
                return;
            }

            GameObject brick = this.bricks.get(this.bricks.size() - 1);

            this.bricks.remove(brick);

            brick.destroy();

        });

    }

    private void placeCurrentBrick() {

        Mesh brickMesh = this.gameManager.getBricks().get(currentModel);
        Material placedMaterial = this.gameManager.getMaterials().get(currentMaterial);

        MeshRenderer renderer = new MeshRenderer();
        renderer.setMesh(brickMesh);
        renderer.setMaterial(placedMaterial);

        GameObject brick = new GameObject("Brick:" + bricks.size());
        brick.addComponent(renderer);

        brick.getTransform().setPosition(new Vector3(getTransform().getPosition()));
        brick.getTransform().setRotation(new Vector3(getTransform().getRotation()));

        brick.setParent(GameObject.findGameObject("Plate"));

        this.bricks.add(brick);

    }

    public void reset() {

        this.currentModel = 0;
        this.currentMaterial = 0;

        this.getTransform().setPosition(new Vector3(0, 1.2, 0));

        for (GameObject brick : this.bricks) {
            this.bricks.remove(brick);
            brick.destroy();
        }

    }

    @Override
    public void start() {

        myRenderer = getGameObject().getComponent(MeshRenderer.class);
        Material brickMaterial = MaterialFactory.getDefaultMaterial();
        myRenderer.setMaterial(brickMaterial);

        gameManager = GameObject.findGameObject("Scene").getComponent(GameManager.class);

        initializeInput();

        reset();
    }

    @Override
    public void update(double delta) {

        Mesh mesh = gameManager.getBricks().get(currentModel);
        Color color = new Color(gameManager.getMaterials().get(currentMaterial).getMainColor());
        color.setAlpha(160);
        Material material = MaterialFactory.getDefaultMaterial();
        material.setMainColor(color);

        myRenderer.setMesh(mesh);
        myRenderer.setMaterial(material);

    }

}
