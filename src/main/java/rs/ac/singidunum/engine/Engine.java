package rs.ac.singidunum.engine;

import com.jogamp.opengl.*;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import lombok.Getter;
import rs.ac.singidunum.engine.interfaces.IGame;

import javax.swing.*;

public class Engine implements GLEventListener {

    private final GLProfile profile;
    private final GLCapabilities capabilities;
    private final GLCanvas canvas;
    private final FPSAnimator animator;

    private static final int FPS = 60;

    private long lastTimestamp;

    @Getter
    private static GLAutoDrawable drawable;

    private IGame game;

    public Engine() {

        profile = GLProfile.getDefault();
        capabilities = new GLCapabilities(profile);
        canvas = new GLCanvas(capabilities);
        animator = new FPSAnimator(canvas, FPS);

        canvas.addGLEventListener(this);
        canvas.addKeyListener(Input.instance);
        canvas.addMouseListener(Input.instance);
        canvas.addMouseMotionListener(Input.instance);

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

        game.update(delta);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        //throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        Engine.drawable = drawable;

        if(game == null) {
            return;
        }

        game.init();

        game.start();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

    }
}
