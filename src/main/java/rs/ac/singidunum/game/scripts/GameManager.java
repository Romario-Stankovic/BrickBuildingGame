package rs.ac.singidunum.game.scripts;

import rs.ac.singidunum.engine.components.GameObject;
import rs.ac.singidunum.engine.components.MeshRenderer;
import rs.ac.singidunum.engine.components.base.Behavior;
import rs.ac.singidunum.engine.util.Material;
import rs.ac.singidunum.engine.util.Mesh;
import rs.ac.singidunum.engine.util.ModelLoader;
import rs.ac.singidunum.engine.util.Vector3;
import rs.ac.singidunum.game.factories.MaterialFactory;

public class GameManager extends Behavior {

    @Override
    public void start() {

    }

    @Override
    public void update(double delta) {

    }

    public void newGame() {

        Mesh brickMesh = ModelLoader.load("/models/brick_2x2.obj");
        Material brickMaterial = MaterialFactory.getDefaultMaterial();

        MeshRenderer brickRenderer = new MeshRenderer();
        brickRenderer.setMesh(brickMesh);
        brickRenderer.setMaterial(brickMaterial);

        GameObject brick = new GameObject("Brick");
        brick.addComponent(brickRenderer);
        brick.getTransform().setPosition(new Vector3(0, 0.8, 0));

        brick.setParent(getGameObject());

    }

}
