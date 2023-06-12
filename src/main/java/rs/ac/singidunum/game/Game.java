package rs.ac.singidunum.game;

import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.components.*;
import rs.ac.singidunum.engine.util.*;
import rs.ac.singidunum.engine.interfaces.IGame;
import rs.ac.singidunum.game.scripts.GameManager;
import rs.ac.singidunum.game.scripts.MouseLook;
import rs.ac.singidunum.game.scripts.Player;
import rs.ac.singidunum.game.scripts.factories.MaterialFactory;

public class Game implements IGame {

    private Camera mainCamera;
    private GameObject scene;

    @Override
    public void init() {

        GameManager gameManager = new GameManager();

        // Initialize listeners
        Engine.getEvents().subscribe("newGame", (args) -> {
            gameManager.newGame();
        });

        Engine.getEvents().subscribe("finishGame", (args) -> {
            gameManager.finishGame();
        });

        Engine.getEvents().subscribe("newEmptyScene", (args) -> {
            gameManager.newEmptyScene();
        });

        Engine.getEvents().subscribe("saveShape", (args) -> {
            gameManager.saveShape();
        });

        Engine.getEvents().subscribe("loadShape", (args) -> {
            gameManager.loadShape();
        });

        // Initialize scene
        scene = new GameObject("Scene");
        scene.addComponent(gameManager);

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

        // Initialize Player
        GameObject player = new GameObject("Player");
        player.setActive(false);
        player.setParent(scene);
        player.addComponent(new MeshRenderer());
        player.addComponent(new Player());

    }

    @Override
    public void update(double delta) {
        mainCamera.render(() -> {
            scene.update(delta);
        });
    }


}
