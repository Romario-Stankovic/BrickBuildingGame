package rs.ac.singidunum.game;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

import rs.ac.singidunum.engine.Engine;
import rs.ac.singidunum.engine.Settings;
import rs.ac.singidunum.engine.components.*;
import rs.ac.singidunum.engine.util.*;
import rs.ac.singidunum.engine.interfaces.IGame;
import rs.ac.singidunum.game.scripts.GameManager;
import rs.ac.singidunum.game.scripts.MouseLook;
import rs.ac.singidunum.game.scripts.Player;
import rs.ac.singidunum.game.scripts.factories.MaterialFactory;

public class Game implements IGame {

    // Reference to the main camera
    private Camera mainCamera;
    // Reference to the scene gameObject
    private GameObject scene;

    private void openSettingsMenu() {

        int fov = Settings.get("fov", Integer.class);
        boolean ambientLight = Settings.get("ambientLight", Boolean.class);
        boolean directionalLight = Settings.get("directionalLight", Boolean.class);
        double sensitivity = Settings.get("sensitivity", Double.class);

        final JFrame frame = new JFrame();

        final JPanel fovPanel = new JPanel();
        final JLabel fovLabel = new JLabel("FOV: " + fov);
        final JSlider fovSlider = new JSlider(30, 120, fov);

        final JPanel ambientLightPanel = new JPanel();
        final JCheckBox ambientLightCheckBox = new JCheckBox("Ambient Lights", ambientLight);

        final JPanel directionalLightPanel = new JPanel();
        final JCheckBox directionalLightCheckBox = new JCheckBox("Directional Light", directionalLight);

        final JPanel sensitivityPanel = new JPanel();
        final JLabel sensitivityLabel = new JLabel("Sensitivity: " + (int)(sensitivity * 100));
        final JSlider sensitivitySlider = new JSlider(1, 100, (int)(sensitivity * 100));

        frame.setLocationRelativeTo(Engine.getInstance().getFrame());
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setIconImage(new ImageIcon(Object.class.getResource("/textures/icon.png")).getImage());

        fovPanel.setLayout(new BoxLayout(fovPanel, BoxLayout.X_AXIS));
        fovPanel.add(fovLabel);
        fovPanel.add(fovSlider);

        ambientLightPanel.add(ambientLightCheckBox);
        
        directionalLightPanel.add(directionalLightCheckBox);

        sensitivityPanel.setLayout(new BoxLayout(sensitivityPanel, BoxLayout.X_AXIS));
        sensitivityPanel.add(sensitivityLabel);
        sensitivityPanel.add(sensitivitySlider);

        fovSlider.addChangeListener(e -> {
            Settings.set("fov", fovSlider.getValue());
            fovLabel.setText("FOV: " + fovSlider.getValue());
        });

        ambientLightCheckBox.addChangeListener(e -> {
            Settings.set("ambientLight", ambientLightCheckBox.isSelected());
        });

        directionalLightCheckBox.addChangeListener(e -> {
            Settings.set("directionalLight", directionalLightCheckBox.isSelected());
        });

        sensitivitySlider.addChangeListener(e -> {
            Settings.set("sensitivity", sensitivitySlider.getValue() / 100.0);
            sensitivityLabel.setText("Sensitivity: " + sensitivitySlider.getValue());
        });

        frame.add(fovPanel);
        frame.add(sensitivityPanel);
        frame.add(ambientLightPanel);
        frame.add(directionalLightPanel);

        frame.pack();
        frame.setVisible(true);

    }

    private void initSettings() {

        Settings.set("fov", 60);
        Settings.set("ambientLight", true);
        Settings.set("directionalLight", true);
        Settings.set("sensitivity", 0.1);

    }

    private void initUI() {
        // Initialize the Menu bar
        JMenuBar menu = new JMenuBar();

        // Menu bar items
        JMenu gameItem = new JMenu("Game");
        JMenu editorItem = new JMenu("Editor");

        // Menu -> Game items
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem finishGameItem = new JMenuItem("Finish Game");
        JMenuItem optionsItem = new JMenuItem("Options");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem quitItem = new JMenuItem("Quit");

        // Menu -> Editor items
        JMenuItem newEditorItem = new JMenuItem("New Editor");
        JMenuItem saveShapeItem = new JMenuItem("Save Shape");
        JMenuItem loadShapeItem = new JMenuItem("Load Shape");

        // Add the menu items
        menu.add(gameItem);
        menu.add(editorItem);

        // Add the game items
        gameItem.add(newGameItem);
        gameItem.add(finishGameItem);
        gameItem.add(optionsItem);
        gameItem.add(helpItem);
        gameItem.add(quitItem);

        // Add the editor items
        editorItem.add(newEditorItem);
        editorItem.add(saveShapeItem);
        editorItem.add(loadShapeItem);

        newGameItem.addActionListener(e -> {
            // Emit newGame event
            Engine.getInstance().getEventManager().emit("newGame");
        });

        finishGameItem.addActionListener(e -> {
            // Emit finishGame event
            Engine.getInstance().getEventManager().emit("finishGame");
        });

        helpItem.addActionListener(e -> {
            // Emit showHelp event
            Engine.getInstance().getEventManager().emit("showHelp");
        });

        optionsItem.addActionListener(e -> {
            openSettingsMenu();
        });

        quitItem.addActionListener(e -> {
            // Exit the application
            System.exit(0);
        });

        newEditorItem.addActionListener(e -> {
            // Emit newEditor event
            Engine.getInstance().getEventManager().emit("newEditor");
        });

        saveShapeItem.addActionListener(e -> {
            // Emit saveShape event
            Engine.getInstance().getEventManager().emit("saveShape");
        });

        loadShapeItem.addActionListener(e -> {
            // Emit loadShape event
            Engine.getInstance().getEventManager().emit("loadShape");
        });

        // Add menu bar to the frame
        Engine.getInstance().getFrame().setJMenuBar(menu);
        // Re-render the window
        SwingUtilities.updateComponentTreeUI(Engine.getInstance().getFrame());

    }

    private void initGame() {
        // Create a gameManager
        GameManager gameManager = new GameManager();

        // Initialize newGame listener
        Engine.getInstance().getEventManager().subscribe("newGame", (args) -> {
            gameManager.newGame();
        });

        // Initialize finishGame listener
        Engine.getInstance().getEventManager().subscribe("finishGame", (args) -> {
            gameManager.finishGame();
        });

        // Initialize showHelp listener
        Engine.getInstance().getEventManager().subscribe("showHelp", (args) -> {
            gameManager.showHelp();
        });

        // Initialize newEditor listener
        Engine.getInstance().getEventManager().subscribe("newEditor", (args) -> {
            gameManager.newEditor();
        });

        // Initialize saveShape listener
        Engine.getInstance().getEventManager().subscribe("saveShape", (args) -> {
            gameManager.saveShape();
        });

        // Initialize loadShape listener
        Engine.getInstance().getEventManager().subscribe("loadShape", (args) -> {
            gameManager.loadShape();
        });

        // Initialize scene
        scene = new GameObject("Scene");
        scene.addComponent(gameManager);

        // Initialize Camera

        Skybox skybox = new Skybox();
        skybox.setTexture(TextureLoader.load("/textures/skybox.png"));

        GameObject camera = new GameObject("Camera");
        mainCamera = camera.addComponent(new Camera());
        camera.addComponent(skybox);

        // Initialize lights

        GameObject ambientLight = new GameObject("Ambient Light");
        ambientLight.addComponent(new AmbientLight());
        ambientLight.setParent(scene);

        GameObject directionalLight = new GameObject("Directional Light");
        DirectionalLight light = directionalLight.addComponent(new DirectionalLight());
        light.setDirection(new Vector3(0.5, 1, -1));
        directionalLight.setParent(scene);

        // Initialize pivot
        GameObject pivot = new GameObject("Pivot");
        pivot.setParent(scene);
        pivot.addComponent(new MouseLook());
        pivot.getTransform().getRotation().setX(30);
        camera.setParent(pivot);
        camera.getTransform().setPosition(new Vector3(0, 0, -30));

        // Initialize plate
        Material plateMaterial = MaterialFactory.getDefaultMaterial();
        Mesh plateMesh = ModelLoader.load("/models/plate_18x18.obj");
        MeshRenderer plateRenderer = new MeshRenderer();
        plateRenderer.setMesh(plateMesh);
        plateRenderer.setMaterial(plateMaterial);

        plateMaterial.setMainColor(new Color(63, 155, 11));
        GameObject plate = new GameObject("Plate");
        plate.addComponent(plateRenderer);
        plate.setParent(scene);

        // Initialize Player
        GameObject player = new GameObject("Player");
        player.setActive(false);
        player.setParent(scene);
        player.addComponent(new MeshRenderer());
        player.addComponent(new Player());
    }

    @Override
    public void init() {
        // Initialize settings
        initSettings();
        // Initialize the UI
        initUI();
        // Initialize the Game
        initGame();
    }

    @Override
    public void update(double delta) {

        // Get main camera FOV
        mainCamera.setFov(Settings.get("fov", Integer.class));

        GameObject.findGameObject("Ambient Light").setActive(Settings.get("ambientLight", Boolean.class));
        GameObject.findGameObject("Directional Light").setActive(Settings.get("directionalLight", Boolean.class));

        // Call render on the camera
        mainCamera.render(() -> {
            // Update the scene when camera is ready
            scene.update(delta);
        });
    }


}
