package rs.ac.singidunum.engine;

import com.jogamp.opengl.*;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import lombok.Getter;
import rs.ac.singidunum.engine.interfaces.IGame;

import javax.swing.*;

public class Engine implements GLEventListener {

    @Getter
    private static Engine instance;

    private GLCanvas canvas;
    private FPSAnimator animator;
    private IGame game;

    @Getter
    private JFrame frame;

    @Getter
    private GLAutoDrawable drawable;
    @Getter
    private EventManager eventManager = new EventManager();
    @Getter
    private int maxLights = 0;

    private int FPS = 60;

    private long lastTimestamp;

    public Engine(JFrame frame, IGame game) {

        if(instance != null) {
            return;
        }

        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);

        // Set the depth buffer size
        capabilities.setDepthBits(24);
        capabilities.setDoubleBuffered(true);

        canvas = new GLCanvas(capabilities);
        animator = new FPSAnimator(canvas, FPS);

        canvas.addGLEventListener(this);
        canvas.addKeyListener(Input.instance);
        canvas.addMouseListener(Input.instance);
        canvas.addMouseMotionListener(Input.instance);
        canvas.addMouseWheelListener(Input.instance);

        canvas.setPreferredSize(frame.getSize());
        frame.add(canvas);
        this.frame = frame;
        this.game = game;

        lastTimestamp = System.currentTimeMillis();

        instance = this;

    }

    public void start() {
        animator.start();
    }

    public void stop() {
        animator.stop();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();

        long currentTimestamp = System.currentTimeMillis();
        double delta = (currentTimestamp - lastTimestamp) / 1000d;
        lastTimestamp = currentTimestamp;

        if(game == null) {
            return;
        }

        gl.glEnable(GL2.GL_LIGHTING);

        game.update(delta);

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

        // Get the maximum number of lights
        int[] maxLights = new int[1];
        GL2 gl = drawable.getGL().getGL2();
        gl.glGetIntegerv(GL2.GL_MAX_LIGHTS, maxLights, 0);
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

        if(game == null) {
            throw new RuntimeException("Game not registered");
        }

        game.init();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

    }
}
