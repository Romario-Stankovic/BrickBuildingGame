package rs.ac.singidunum.game;

import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.*;
import rs.ac.singidunum.engine.util.*;
import rs.ac.singidunum.engine.interfaces.IGame;
import rs.ac.singidunum.game.factories.MaterialFactory;
import rs.ac.singidunum.game.scripts.MouseLook;

public class Game implements IGame {

    Camera mainCamera;
    GameObject scene;

    @Override
    public void init() {

        // Initialize listeners
        Engine.getEvents().subscribe("newGame", (args) -> {
           //TODO: Add new game logic
            System.out.println("New game started!");
        });

        // Initialize scene
        scene = new GameObject("Scene");

        // Initialize Camera

        Skybox skybox = new Skybox();
        skybox.setTexture(TextureLoader.load("/textures/skybox.png"));

        GameObject camera = new GameObject("Camera");
        mainCamera = camera.addComponent(new Camera());
        camera.addComponent(skybox);

        // Initialize lights

        GameObject ambientLight = new GameObject("Ambient Light");
        ambientLight.addComponent(new AmbientLight());
        ambientLight.setParent(scene);

        GameObject directionalLight = new GameObject("Directional Light");
        directionalLight.addComponent(new DirectionalLight());
        directionalLight.getTransform().setPosition(new Vector3(0, 1, -1));
        directionalLight.setParent(scene);

        // Initialize pivot
        GameObject pivot = new GameObject("Pivot");
        pivot.setParent(scene);
        pivot.addComponent(new MouseLook());
        pivot.getTransform().getRotation().setX(30);
        camera.setParent(pivot);
        camera.getTransform().setPosition(new Vector3(0, 0, -30));

        // Initialize plate
        Material plateMaterial = MaterialFactory.getDefaultMaterial();
        Mesh plateMesh = ModelLoader.load("/models/plate_18x18.obj");
        MeshRenderer plateRenderer = new MeshRenderer();
        plateRenderer.setMesh(plateMesh);
        plateRenderer.setMaterial(plateMaterial);

        plateMaterial.setMainColor(new Color(63, 155, 11));
        GameObject plate = new GameObject("Plate");
        plate.addComponent(plateRenderer);
        plate.setParent(scene);

    }

    @Override
    public void start() {
        scene.start();
    }

    @Override
    public void update(double delta) {
        mainCamera.render(() -> {
            scene.update(delta);
        });
    }


}
