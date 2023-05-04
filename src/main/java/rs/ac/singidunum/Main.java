package rs.ac.singidunum;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.Input;
import rs.ac.singidunum.engine.interfaces.IGame;
import rs.ac.singidunum.game.Game;

public class Main 
{
    private static GLProfile profile;
    private static GLCapabilities capabilities;
    private static GLCanvas canvas;
    private static FPSAnimator animator;

    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 562;
    private static final int FPS = 60;

    public static void main( String[] args )
    {
        // Initialize OpenGL
        profile = GLProfile.getDefault();
        capabilities = new GLCapabilities(profile);
        canvas = new GLCanvas(capabilities);
        animator = new FPSAnimator(canvas, FPS);

        // Create a new game instance
        Engine engine = new Engine();

        IGame game = new Game();

        engine.register(game);

        // Add the game to the canvas
        canvas.addGLEventListener(engine);
        canvas.addKeyListener(Input.instance);
        canvas.addMouseListener(Input.instance);
        canvas.addMouseMotionListener(Input.instance);

        // Initialize the Menu bar
        JMenuBar menu = new JMenuBar();
        // Initialize the Game menu
        JMenu gameItem = new JMenu("Game");
        // Add Exit option to the Game menu
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem optionsItem = new JMenuItem("Options");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem quitItem = new JMenuItem("Quit");

        quitItem.addActionListener(e -> {
            System.exit(0);
        });

        gameItem.add(newGameItem);
        gameItem.add(optionsItem);
        gameItem.add(helpItem);
        gameItem.add(quitItem);
        menu.add(gameItem);

        // Initialize the window
        JFrame frame = new JFrame("Game");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menu);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animator.stop();
            }
        });

        // Make canvas fill the window
        canvas.setPreferredSize(frame.getSize());

        // Add the canvas to the window and show it
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);

        // Start the animation
        animator.start();
    }
}
