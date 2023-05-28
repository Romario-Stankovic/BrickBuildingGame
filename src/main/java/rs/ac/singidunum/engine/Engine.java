package rs.ac.singidunum.engine;

import com.jogamp.opengl.*;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import lombok.Getter;
import rs.ac.singidunum.engine.interfaces.IGame;

import javax.swing.*;

public class Engine implements GLEventListener {

    private final GLCanvas canvas;
    private final FPSAnimator animator;

    @Getter
    private static GLAutoDrawable drawable;

    @Getter
    private static int maxLights = 0;

    private static final int FPS = 60;

    private long lastTimestamp;

    private IGame game;

    @Getter
    private static final Events events = new Events();

    public Engine() {

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

        lastTimestamp = System.currentTimeMillis();
    }

    public void attach(JFrame frame) {
        canvas.setPreferredSize(frame.getSize());
        frame.add(canvas);
    }

    public void register(IGame game) {
        this.game = game;
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
        Engine.drawable = drawable;

        // Get the maximum number of lights
        int[] maxLights = new int[1];
        GL2 gl = drawable.getGL().getGL2();
        gl.glGetIntegerv(GL2.GL_MAX_LIGHTS, maxLights, 0);
        Engine.maxLights = maxLights[0];

        // Disable default ambient light
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, new float[]{0f,0f,0f,0f}, 0);

        // Enable depth testing
        gl.glEnable(GL2.GL_DEPTH_TEST);

        // Enable alpha blending
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        if(game == null) {
            throw new RuntimeException("Game not registered");
        }

        game.init();

        game.start();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

    }
}
