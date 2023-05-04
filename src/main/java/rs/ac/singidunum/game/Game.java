package rs.ac.singidunum.game;

import rs.ac.singidunum.engine.components.Camera;
import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.MeshRenderer;
import rs.ac.singidunum.engine.util.Mesh;
import rs.ac.singidunum.engine.components.Skybox;
import rs.ac.singidunum.engine.interfaces.IGame;
import rs.ac.singidunum.engine.util.ModelLoader;
import rs.ac.singidunum.engine.util.TextureLoader;
import rs.ac.singidunum.engine.util.Vector3;
import rs.ac.singidunum.game.scripts.MouseLook;
import rs.ac.singidunum.game.scripts.RotateCube;

import java.awt.*;

public class Game implements IGame {

    Camera mainCamera;

    GameObject scene;


    @Override
    public void init() {

        scene = new GameObject();

        GameObject pivot = new GameObject();
        pivot.setParent(scene);
        pivot.addComponent(new MouseLook());

        GameObject camera = new GameObject();
        mainCamera = camera.addComponent(new Camera());
        camera.addComponent(new Skybox()).setTexture(TextureLoader.loadTexture("/textures/skybox.png"));
        camera.getTransform().setPosition(new Vector3(0, 0, -10));
        camera.setParent(pivot);

        Mesh cubeMesh = ModelLoader.load("/models/cube.obj");

        GameObject cube = new GameObject();
        Mesh mesh = ModelLoader.load("/models/cube.obj");
        MeshRenderer mr = cube.addComponent(new MeshRenderer());
        mr.setMesh(cubeMesh);
        mr.setTexture(TextureLoader.loadTexture("/textures/cube.png"));
        cube.getTransform().setPosition(new Vector3(0, 0, 0));
        cube.addComponent(new RotateCube());
        cube.setParent(scene);

        GameObject cube2 = new GameObject();
        MeshRenderer mr2 = cube2.addComponent(new MeshRenderer());
        mr2.setMesh(mesh);
        mr2.setTexture(TextureLoader.loadTexture("/textures/cube.png"));
        cube2.getTransform().setPosition(new Vector3(-2, 0, -2));
        cube2.getTransform().setScale(new Vector3(0.5, 0.5, 0.5));
        cube2.setParent(cube);
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
