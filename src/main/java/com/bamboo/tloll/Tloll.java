package com.bamboo.tloll;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.debug.Logger;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.graphics.structure.WorldMap;
import com.bamboo.tloll.input.Input;
import com.bamboo.tloll.input.KeyboardHandler;
import com.bamboo.tloll.player.Player;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Tloll {

    private GLFWKeyCallback keyCallback; // We need to strongly reference callback instances.

    public static void main(String[] args) {
        new Tloll().run();
    }

    public void run() {
        try {
            init();
            loop();
            glfwDestroyWindow(Config.getInstance().getWindowId());
            keyCallback.free();
        } finally {
            glfwTerminate();
        }
    }

    private void init() {
        GraphicsUtil gu = GraphicsUtil.getInstance();
        gu.initializeGL();
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        Config.getInstance().setWindowId(gu.createWindow(Constants.WIDTH, Constants.HEIGHT, Constants.TITLE));

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(Config.getInstance().getWindowId(), keyCallback = new KeyboardHandler());

        glfwMakeContextCurrent(Config.getInstance().getWindowId());
        glfwSwapInterval(0); //This is V-SYNC Enabled - 1 Disabled - 0
        glfwShowWindow(Config.getInstance().getWindowId());

        GL.createCapabilities(); // Creates bindings for OpenGL to be used.

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Constants.WIDTH, 0, Constants.HEIGHT, 1, -1); // NOTE(map) : This needs the same size as the window you idiot!!!
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // NOTE(map) : We have this here because it was causing problems having int in drawCurrentScene
        // as it reset the current scene to scene 0 every time.  Maybe move this elswhere???
        WorldMap.getInstance().setCurrentScene(Renderer.loadTileBuffers());
	Logger.getInstance().setAlphabetSprites(Renderer.loadAlphabetSprites());
    }

    private void loop() {
        while (!glfwWindowShouldClose(Config.getInstance().getWindowId())) {
            render();
            process();
            update();
        }
    }

    private void render() {
        // Swaps buffers that will be drawn.
        glfwSwapBuffers(Config.getInstance().getWindowId());
    }

    private void process() {
        //Poll for window events. This invokes key callbacks
        glfwPollEvents();
    }

    private void update() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the frame buffer.

        Renderer.drawCurrentScene();

        //Handles all player movement
        for(int playerId: Config.getInstance().getPlayers().keySet()) {
            Player player = Config.getInstance().getPlayers().get(playerId);
            player.getUnit().draw();
            player.processMovement();
            SceneTransition.checkForTransition(player.getUnit()); //What to do  when we have multi-units ?
        }

        Input.getInstance().poll();
        Logger.getInstance().calculateAndDisplayFps();

    }

}
