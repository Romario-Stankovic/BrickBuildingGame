package rs.ac.singidunum;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.interfaces.IGame;
import rs.ac.singidunum.game.Game;

public class Main 
{
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 562;

    public static void main( String[] args )
    {
        // Initialize the window
        JFrame frame = new JFrame("Game");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new game instance
        IGame game = new Game();

        // Initialize the engine
        Engine engine = new Engine(frame, game);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.stop();
            }
        });

        frame.pack();
        frame.setVisible(true);

        // Start the engine
        engine.start();
    }
}
