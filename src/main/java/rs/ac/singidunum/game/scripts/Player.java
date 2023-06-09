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
import rs.ac.singidunum.game.factories.MaterialFactory;

public class Player extends Behavior {

    private MeshRenderer myRenderer = null;

    private List<Mesh> models = new ArrayList<>();
    private int currentModel = 0;

    private List<Material> materials = new ArrayList<>(currentModel);
    private int currentMaterial = 0;

    private List<GameObject> bricks = new CopyOnWriteArrayList<>();

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
                currentModel = models.size() - 1;
            } else {
                currentModel--;
            }
        });

        Input.onKeyDown(KeyEvent.VK_2, (args) -> {
            if(currentModel == models.size() - 1) {
                currentModel = 0;
            } else {
                currentModel++;
            }
        });

        Input.onKeyDown(KeyEvent.VK_3, (args) -> {
            if(currentMaterial == 0) {
                currentMaterial = materials.size() - 1;
            } else {
                currentMaterial--;
            }
        });

        Input.onKeyDown(KeyEvent.VK_4, (args) -> {
            if(currentMaterial == materials.size() - 1) {
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

    private void initializeModels() {

        Mesh brick2x2 = ModelLoader.load("/models/brick_2x2.obj");
        Mesh brick2x4 = ModelLoader.load("/models/brick_2x4.obj");

        this.models.add(brick2x2);
        this.models.add(brick2x4);

    }

    private void initializeMaterials() {

        Material red = MaterialFactory.getDefaultMaterial();
        red.setMainColor(new Color(221, 25, 32, 160));

        Material green = MaterialFactory.getDefaultMaterial();
        green.setMainColor(new Color(0, 175, 77, 160));

        Material blue = MaterialFactory.getDefaultMaterial();
        blue.setMainColor(new Color(0, 108, 183, 160));

        Material yellow = MaterialFactory.getDefaultMaterial();
        yellow.setMainColor(new Color(255, 205, 3, 160));

        materials.add(red);
        materials.add(green);
        materials.add(blue);
        materials.add(yellow);

    }

    private void placeCurrentBrick() {

        Mesh brickMesh = this.models.get(currentModel);
        Color color = new Color(myRenderer.getMaterial().getMainColor());
        color.setAlpha(255);

        Material placedMaterial = MaterialFactory.getDefaultMaterial();
        placedMaterial.setMainColor(color);

        MeshRenderer renderer = new MeshRenderer();
        renderer.setMesh(brickMesh);
        renderer.setMaterial(placedMaterial);

        GameObject brick = new GameObject("Brick:" + bricks.size());
        brick.getTransform().setPosition(new Vector3(getTransform().getPosition()));
        brick.getTransform().setRotation(new Vector3(getTransform().getRotation()));

        brick.setParent(GameObject.findGameObject("Plate"));
        brick.addComponent(renderer);

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
        this.getTransform().setScale(new Vector3(1, 1, 1));

        initializeInput();
        initializeModels();
        initializeMaterials();

        reset();
    }

    @Override
    public void update(double delta) {

        myRenderer.setMesh(models.get(currentModel));
        myRenderer.setMaterial(materials.get(currentMaterial));

    }

}
