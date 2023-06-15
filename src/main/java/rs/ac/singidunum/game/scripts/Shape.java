package rs.ac.singidunum.game.scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

// Shape class defines a shape that consists of bricks
public class Shape {
    // List of bricks that the shape has
    @Getter
    private List<Brick> bricks = new ArrayList<>();

    // Empty constructor
    public Shape() {

    }

    // Add a brick to the list
    public void addBrick(Brick brick) {
        this.bricks.add(brick);
    }

    // Save the shape to a file
    public void saveShape(String name) {
        // Try to save the shape
        try {
            // Open a file for writing
            File file = new File("shapes/" + name + ".json");

            // Map the Shape class to a JSON object and write it to a file
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, this);
        } catch (Exception e) {
            // Throw a runtime exception
            throw new RuntimeException(e);
        }
    }

    // Load a shape from a file
    public static Shape loadShape(String name) {

        // Create a new Object mapper
        ObjectMapper mapper = new ObjectMapper();

        //Try to load the shape
        try {
            // Reference to the file to be loaded
            File file = new File("shapes/" + name);
            // Load the saved JSON into a Shape object
            Shape shape = mapper.readValue(file, Shape.class);
            // Return the shape
            return shape;
        } catch (Exception e) {
            // If file loading fails, throw a runtime exception
            throw new RuntimeException(e);
        }

    }

}
