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

public class Player extends Behavior {

    private MeshRenderer myRenderer = null;
    private int brickId = 0;
    private int materialId = 0;
    private GameManager gameManager = null;

    @Getter
    private final List<Brick> bricks = new LinkedList<>();

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
            if(brickId == 0) {
                brickId = gameManager.getBricks().size() - 1;
            } else {
                brickId--;
            }
        });

        Input.onKeyDown(KeyEvent.VK_2, (args) -> {
            if(brickId == gameManager.getBricks().size() - 1) {
                brickId = 0;
            } else {
                brickId++;
            }
        });

        Input.onKeyDown(KeyEvent.VK_3, (args) -> {
            if(materialId == 0) {
                materialId = gameManager.getMaterials().size() - 1;
            } else {
                materialId--;
            }
        });

        Input.onKeyDown(KeyEvent.VK_4, (args) -> {
            if(materialId == gameManager.getMaterials().size() - 1) {
                materialId = 0;
            } else {
                materialId++;
            }
        });

        Input.onKeyDown(KeyEvent.VK_SPACE, (args) -> {

            placeCurrentBrick();

        });

        Input.onKeyDown(KeyEvent.VK_BACK_SPACE, (args) -> {

            if(this.bricks.size() == 0) {
                return;
            }

            Brick brick = this.bricks.remove(this.bricks.size() - 1);

            brick.getGameObject().destroy();

        });

    }

    private void placeCurrentBrick() {

        Mesh brickMesh = this.gameManager.getBricks().get(brickId);
        Material placedMaterial = this.gameManager.getMaterials().get(materialId);

        MeshRenderer renderer = new MeshRenderer();
        renderer.setMesh(brickMesh);
        renderer.setMaterial(placedMaterial);

        GameObject go = new GameObject("Brick:" + bricks.size());
        go.addComponent(renderer);

        go.getTransform().setPosition(new Vector3(getTransform().getPosition()));
        go.getTransform().setRotation(new Vector3(getTransform().getRotation()));

        go.setParent(GameObject.findGameObject("Plate"));

        Brick brick = new Brick();
        brick.setBrickId(brickId);
        brick.setMaterialId(materialId);
        brick.setPosition(new Vector3(getTransform().getPosition()));
        brick.setRotation(new Vector3(getTransform().getRotation()));
        brick.setGameObject(go);

        this.bricks.add(brick);

    }

    public void reset() {

        this.brickId = 0;
        this.materialId = 0;

        this.getTransform().setPosition(new Vector3(8, 1.2, 8));

        for (Brick brick : this.bricks) {
            brick.getGameObject().destroy();
        }

        this.bricks.clear();

    }

    @Override
    public void start() {

        myRenderer = getGameObject().getComponent(MeshRenderer.class);
        Material brickMaterial = MaterialFactory.getDefaultMaterial();
        myRenderer.setMaterial(brickMaterial);

        gameManager = GameObject.findGameObject("Scene").getComponent(GameManager.class);

        initializeInput();

        this.brickId = 0;
        this.materialId = 0;

        this.getTransform().setPosition(new Vector3(8, 1.2, 8));

    }

    @Override
    public void update(double delta) {

        Mesh mesh = gameManager.getBricks().get(brickId);
        Color color = new Color(gameManager.getMaterials().get(materialId).getMainColor());
        color.setAlpha(160);
        Material material = MaterialFactory.getDefaultMaterial();
        material.setMainColor(color);

        myRenderer.setMesh(mesh);
        myRenderer.setMaterial(material);

    }

}
