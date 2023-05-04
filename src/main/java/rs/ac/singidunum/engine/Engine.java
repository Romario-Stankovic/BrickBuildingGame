package rs.ac.singidunum.engine;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import lombok.Getter;
import rs.ac.singidunum.engine.interfaces.IGame;

public class Engine implements GLEventListener {

    private long lastTimestamp;

    @Getter
    private static GLAutoDrawable drawable;

    private IGame game;

    public Engine() {
        lastTimestamp = System.currentTimeMillis();
    }

    public void register(IGame game) {
        this.game = game;
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        Engine.drawable = drawable;
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
