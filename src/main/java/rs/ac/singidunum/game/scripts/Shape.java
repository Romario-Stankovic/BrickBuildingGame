package rs.ac.singidunum.game.scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import rs.ac.singidunum.engine.util.Vector3;

public class Shape {
    @Getter
    private List<Brick> bricks = new ArrayList<>();

    public Shape() {

    }

    public void addBrick(int brickId, int materialId, Vector3 position, Vector3 rotation) {
        Brick brick = new Brick(brickId, materialId, position, rotation);
        this.bricks.add(brick);
    }

    public void saveShape(String name) {
        try {
            File file = new File("shapes/" + name + ".json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Shape loadShape(String name) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = new File("shapes/" + name + ".json");
            Shape shape = mapper.readValue(file, Shape.class);
            return shape;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
