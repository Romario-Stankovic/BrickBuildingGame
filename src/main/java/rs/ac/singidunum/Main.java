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

        // Create a new game instance
        Engine engine = new Engine();

        // Create a new game instance
        IGame game = new Game();

        // Register the game
        engine.register(game);

        // Initialize the Menu bar
        JMenuBar menu = new JMenuBar();
        // Initialize the Game menu
        JMenu gameItem = new JMenu("Game");
        JMenu editorItem = new JMenu("Editor");
        // Add Exit option to the Game menu
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem finishGameItem = new JMenuItem("Finish Game");
        JMenuItem optionsItem = new JMenuItem("Options");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem quitItem = new JMenuItem("Quit");

        JMenuItem newEmptyScene = new JMenuItem("New Editor");
        JMenuItem saveShapeItem = new JMenuItem("Save Shape");
        JMenuItem loadShapeItem = new JMenuItem("Load Shape");

        newGameItem.addActionListener(e -> {
            Engine.getEvents().emit("newGame");
        });

        finishGameItem.addActionListener(e -> {
            Engine.getEvents().emit("finishGame");
        });

        quitItem.addActionListener(e -> {
            System.exit(0);
        });

        newEmptyScene.addActionListener(e -> {
            Engine.getEvents().emit("newEmptyScene");
        });

        saveShapeItem.addActionListener(e -> {
            Engine.getEvents().emit("saveShape");
        });

        loadShapeItem.addActionListener(e -> {
            Engine.getEvents().emit("loadShape");
        });


        editorItem.add(newEmptyScene);
        editorItem.add(saveShapeItem);
        editorItem.add(loadShapeItem);

        gameItem.add(newGameItem);
        gameItem.add(finishGameItem);
        gameItem.add(optionsItem);
        gameItem.add(helpItem);
        gameItem.add(quitItem);

        menu.add(gameItem);
        menu.add(editorItem);

        // Initialize the window
        JFrame frame = new JFrame("Game");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menu);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.stop();
            }
        });

        // Attach engine canvas to the window
        engine.attach(frame);

        frame.pack();
        frame.setVisible(true);

        // Start the engine
        engine.start();
    }
}
