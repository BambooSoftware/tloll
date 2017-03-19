package com.bamboo.tloll;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.debug.Logger;
import com.bamboo.tloll.graphics.*;
import com.bamboo.tloll.graphics.structure.WorldMap;
import com.bamboo.tloll.input.Input;
import com.bamboo.tloll.input.KeyboardHandler;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Tloll {

    private long windowId;
    private Unit userHero; //TODO: maybe we can refactor this out somewhere - tbd
    private Input in; //TODO: maybe we can refactor this out somewhere - tbd

    private Logger logger;

    // We need to strongly reference callback instances.
    private GLFWKeyCallback keyCallback;

    public static void main(String[] args) {
        new Tloll().run();
    }

    public void run() {
        System.out.println("Running tloll !");

        try {
            init();
            loop();
            glfwDestroyWindow(windowId);
            keyCallback.free();
        } finally {
            glfwTerminate();
        }
    }

    private void init() {
        GraphicsUtil gu = GraphicsUtil.getInstance();
        gu.initializeGL();
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        windowId = gu.createWindow(Constants.WIDTH, Constants.HEIGHT, Constants.TITLE);


        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(windowId, keyCallback = new KeyboardHandler());


        in = new Input();

        logger = new Logger("DEBUG"); //TODO make me a singelton ?

        glfwMakeContextCurrent(windowId);
        glfwSwapInterval(1);
        glfwShowWindow(windowId);

        GL.createCapabilities(); // Creates bindings for OpenGL to be used.

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Constants.WIDTH, 0, Constants.HEIGHT, 1, -1); // NOTE(map) : This needs the same size as the window you idiot!!!
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //TODO: May be able to further refactor stuff below but unsure atm so just keeping them!
        // Sample squares that probably won't stick around.
        userHero = new Unit(100.0f, 100.0f, 32, 32, 0.5f, 0.0f, 0.0f, 0.0f, Direction.DOWN);
        userHero.addBufferToMap(0, new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + "/Assets/Actors/panda_f_base.png"), userHero.getHeight(), userHero.getWidth(), 4, 4));

	// NOTE(map) : We have this here because it was causing problems having int in drawCurrentScene
	// as it reset the current scene to scene 0 every time.  Maybe move this elswhere???
	WorldMap.getInstance().setCurrentScene(Renderer.loadTileBuffers());
	
    }

    private void loop() {
        while (!glfwWindowShouldClose(windowId)) {
            render();
            process();
            update();
        }
    }

    private void render() {
        // Swaps buffers that will be drawn.
        glfwSwapBuffers(windowId);
    }

    private void process() {
        //Poll for window events. This invokes key callbacks
        glfwPollEvents();
    }

    private void update() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the frame buffer.

        Renderer.drawCurrentScene();
        // Highlight the current tile the player lives on.
        logger.highlightCurrentTile(userHero);
        Renderer.drawAnimatedUnit(userHero, 0, userHero.getColNumber());

        // Print out some debug info on the screen.
        logger.displayPlayerInfo(userHero);
        logger.displayOccupiedTiles(userHero);

        //TODO: should we put input processing on its own thread ?
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        in.pollInput(windowId, userHero);
    	SceneTransition.checkForTransition(userHero);
    }

}
