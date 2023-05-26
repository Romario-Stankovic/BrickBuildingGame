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

        GameObject brick = new GameObject();
        Mesh mesh = ModelLoader.load("/models/brick2x2.obj");
        MeshRenderer mr = brick.addComponent(new MeshRenderer());
        mr.setMesh(mesh);
        brick.getTransform().setPosition(new Vector3(0, 0, 0));
        brick.addComponent(new RotateCube());
        brick.setParent(scene);

        GameObject brick2 = new GameObject();
        MeshRenderer mr2 = brick2.addComponent(new MeshRenderer());
        mr2.setMesh(mesh);
        brick2.getTransform().setPosition(new Vector3(-2, 0, -2));
        brick2.getTransform().setScale(new Vector3(0.5, 0.5, 0.5));
        brick2.setParent(brick);
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
