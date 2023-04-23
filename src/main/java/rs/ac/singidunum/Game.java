package rs.ac.singidunum;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import lombok.Getter;
import rs.ac.singidunum.components.Camera;
import rs.ac.singidunum.components.GameObject;
import rs.ac.singidunum.components.Mesh;
import rs.ac.singidunum.components.Skybox;
import rs.ac.singidunum.scripts.MouseLook;
import rs.ac.singidunum.scripts.RotateCube;
import rs.ac.singidunum.util.ModelLoader;
import rs.ac.singidunum.util.TextureLoader;
import rs.ac.singidunum.util.Vector3;

public class Game implements GLEventListener {

    private long lastTimestamp;
    private Camera mainCamera;

    @Getter
    private static GLAutoDrawable drawable;

    private GameObject scene;

    public Game() {
        lastTimestamp = System.currentTimeMillis();
        scene = new GameObject();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        Game.drawable = drawable;

        long currentTimestamp = System.currentTimeMillis();
        double delta = (currentTimestamp - lastTimestamp) / 1000d;
        lastTimestamp = currentTimestamp;

        mainCamera.render();
        scene.update(delta);

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        //throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        GameObject pivot = new GameObject();
        pivot.setParent(scene);
        pivot.addComponent(new MouseLook());

        GameObject camera = new GameObject();
        mainCamera = camera.addComponent(new Camera());
        camera.addComponent(new Skybox()).setTexture(TextureLoader.loadTexture("/textures/skybox.png"));
        camera.getTransform().setPosition(new Vector3(0, 0, -10));
        camera.setParent(pivot);

        GameObject cube = new GameObject();
        Mesh mesh = ModelLoader.loadModel("/models/cube.obj");
        mesh.setTexture(TextureLoader.loadTexture("/textures/cube.png"));
        cube.addComponent(mesh);
        cube.getTransform().setPosition(new Vector3(0, 0, 0));
        cube.addComponent(new RotateCube());
        cube.setParent(scene);

        GameObject cube2 = new GameObject();
        Mesh mesh2 = ModelLoader.loadModel("/models/cube.obj");
        mesh2.setTexture(TextureLoader.loadTexture("/textures/cube.png"));
        cube2.addComponent(mesh2);
        cube2.getTransform().setPosition(new Vector3(-2, 0, -2));
        cube2.getTransform().setScale(new Vector3(0.5, 0.5, 0.5));
        cube2.setParent(cube);

        scene.start();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

    }
}
