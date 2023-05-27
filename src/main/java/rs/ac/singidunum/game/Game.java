package rs.ac.singidunum.game;

import rs.ac.singidunum.engine.components.*;
import rs.ac.singidunum.engine.enums.MaterialShading;
import rs.ac.singidunum.engine.util.*;
import rs.ac.singidunum.engine.interfaces.IGame;
import rs.ac.singidunum.game.scripts.MouseLook;
import rs.ac.singidunum.game.scripts.RotateCube;

public class Game implements IGame {

    Camera mainCamera;

    GameObject scene;

    @Override
    public void init() {

        scene = new GameObject("Scene");

        GameObject pivot = new GameObject("Pivot");
        pivot.setParent(scene);
        pivot.addComponent(new MouseLook());

        GameObject camera = new GameObject("Camera");
        mainCamera = camera.addComponent(new Camera());
        camera.addComponent(new Skybox()).setTexture(TextureLoader.loadTexture("/textures/skybox.png"));
        camera.getTransform().setPosition(new Vector3(0, 0, -10));
        camera.setParent(pivot);

        Material material = new Material();
        material.setAmbient(new Color(50, 50, 50));

        GameObject brick = new GameObject("Brick");
        Mesh mesh = ModelLoader.load("/models/brick2x2.obj");
        MeshRenderer mr = brick.addComponent(new MeshRenderer());
        mr.setMesh(mesh);
        mr.setMaterial(material);
        brick.getTransform().setPosition(new Vector3(0, 0, 0));
        brick.addComponent(new RotateCube());
        brick.setParent(scene);

        GameObject brick2 = new GameObject("Brick2");
        MeshRenderer mr2 = brick2.addComponent(new MeshRenderer());
        mr2.setMesh(mesh);
        mr2.setMaterial(material);
        brick2.getTransform().setPosition(new Vector3(-2, 0, -2));
        brick2.getTransform().setScale(new Vector3(0.5, 0.5, 0.5));
        brick2.setParent(brick);

        GameObject ambientLight = new GameObject("Ambient Light");
        ambientLight.addComponent(new AmbientLight());
        ambientLight.setParent(scene);

        GameObject directionalLight = new GameObject("Directional Light");
        directionalLight.addComponent(new DirectionalLight());
        directionalLight.getTransform().setPosition(new Vector3(0, 0, 1));
        directionalLight.setParent(scene);

        /*GameObject pointLight = new GameObject("Point Light");
        pointLight.addComponent(new PointLight());
        pointLight.getTransform().setPosition(new Vector3(0, 5, 0));
        pointLight.setParent(scene);*/

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
