package rs.ac.singidunum.engine;

import com.jogamp.opengl.*;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import lombok.Getter;
import rs.ac.singidunum.engine.interfaces.IGame;

import javax.swing.*;

// Engine class
// This class is the main class of the engine and is responsible for initializing the game and rendering it
public class Engine implements GLEventListener {

    // Singleton instance
    @Getter
    private static Engine instance;

    // Engine components
    private GLCanvas canvas;
    private FPSAnimator animator;
    
    // Game instance
    private IGame game;

    // Window frame
    @Getter
    private JFrame frame;

    // Drawable instance
    @Getter
    private GLAutoDrawable drawable;

    // Event manager
    @Getter
    private EventManager eventManager;

    // Maximum number of lights supported by the GPU
    @Getter
    private int maxLights;

    // FPS of the game
    private int FPS;

    // Last timestamp (used for delta time calculation)
    private long lastTimestamp;

    // Engine constructor
    public Engine(JFrame frame, IGame game) {

        // Check if the instance is already initialized
        if(instance != null) {
            // If it is return
            return;
        }

        // Get GL profile and capabilities
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);

        // Configure depth buffer
        capabilities.setDepthBits(24);

        // Configure double buffering
        capabilities.setDoubleBuffered(true);

        // Create canvas
        canvas = new GLCanvas(capabilities);

        // Add event listeners to the canvas
        canvas.addGLEventListener(this);
        canvas.addKeyListener(Input.instance);
        canvas.addMouseListener(Input.instance);
        canvas.addMouseMotionListener(Input.instance);
        canvas.addMouseWheelListener(Input.instance);

        // Set canvas size to the frame size
        canvas.setPreferredSize(frame.getSize());
        // Add canvas to the frame
        frame.add(canvas);
        
        // Create animator
        animator = new FPSAnimator(canvas, FPS);

        // Set the frame and game references
        this.frame = frame;
        this.game = game;

        // Create event manager
        eventManager = new EventManager();

        // Set the maximum number of lights
        maxLights = 0;

        // Set the FPS
        FPS = 60;

        // Initialize the last timestamp
        lastTimestamp = System.currentTimeMillis();

        // Set the singleton instance
        instance = this;

    }

    public void start() {
        // Start the animator
        animator.start();
    }

    public void stop() {
        // Stop the animator
        animator.stop();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        // Get the GL2 instance
        final GL2 gl = drawable.getGL().getGL2();

        // Calculate delta time
        long currentTimestamp = System.currentTimeMillis();
        double delta = (currentTimestamp - lastTimestamp) / 1000d;
        lastTimestamp = currentTimestamp;

        // If the game is not initialized return
        if(game == null) {
            return;
        }

        // Enable lighting
        gl.glEnable(GL2.GL_LIGHTING);

        // Update the game and render it
        game.update(delta);

        // Disable lighting
        gl.glDisable(GL2.GL_LIGHTING);

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        //throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        // Set the static drawable variable
        this.drawable = drawable;

        // Get the GL2 instance
        final GL2 gl = drawable.getGL().getGL2();
        
        // Get the maximum number of lights
        int[] maxLights = new int[1];
        gl.glGetIntegerv(GL2.GL_MAX_LIGHTS, maxLights, 0);
        // Set the maximum number of lights
        this.maxLights = maxLights[0];

        // Disable default ambient light
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, new float[]{0f,0f,0f,0f}, 0);

        // Enable depth testing
        gl.glEnable(GL2.GL_DEPTH_TEST);

        // Enable stencil buffer
        gl.glEnable(GL2.GL_STENCIL_TEST); 

        // Enable alpha blending
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        // Check if the game is null and throw an exception if it is
        if(game == null) {
            throw new RuntimeException("Game not registered");
        }

        // Initialize the game
        game.init();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

    }
}
