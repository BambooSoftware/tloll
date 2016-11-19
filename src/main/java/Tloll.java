package com.game.mygame;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import java.util.ArrayList;

import com.game.mygame.Square;

import org.lwjgl.system.Platform;

public class Tloll
{

    private long windowId;
    private static int WIDTH = 512;
    private static int HEIGHT = 512;
    private static String TITLE = "The Legend of Ling Ling";
    private double secsPerFrame = 1 / 30;
    private static boolean isRunning = true;
    private static boolean right = true;
    private static int animateSpriteFrame = 1;
    private static int frameSkip = 2;
    private static String currentDir = System.getProperty("user.dir");

    private static ArrayList<BufferTuple> bufferList = new ArrayList<BufferTuple>();

    // Initialize some stuff here.
    public void initializeGL()
    {

     System.err.println("Forcing toolkit");
     // init AWT before glfwInit
     if( Platform.get() == Platform.MACOSX ) java.awt.Toolkit.getDefaultToolkit();

	if (!glfwInit())
	    {
		System.err.println("Error initializing GLFW");
		System.exit(1);
	    }
    }

    // Creating a window and returning the ID as the handle.
    public long createWindow(int width, int height, String title)
    {
	long windowID = glfwCreateWindow(width, height, title, NULL, NULL);

	if (windowID == NULL)
	    {
		System.err.println("Error creating a window");
		System.exit(1);
	    }

	return windowID;
    }

    // Game initializer.
    public void initializeGame()
    {
	System.out.println("Game is being initialized");
    }

    // Bind all key presses here to do something.
    public void bindKeys(Square player)
    {
	if (glfwGetKey(windowId, GLFW_KEY_A) == 1 && glfwGetKey(windowId, GLFW_KEY_S) == 1)
	    {
		System.out.println("A + S Combo Key");
		player.setSpeedX(player.getSpeedX() - player.getAcceleration());
		player.setSpeedY(player.getSpeedY() - player.getAcceleration());
		if (player.getSpeedX() < -10.0f)
		    {
			player.setSpeedX(-10.0f);
		    }
		if (player.getSpeedY() < -10.0f)
		    {
			player.setSpeedY(-10.0f);
		    }
		movePlayer(player, player.getSpeedX(), player.getSpeedY());
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_A) == 1 && glfwGetKey(windowId, GLFW_KEY_W) == 1)
	    {
		System.out.println("A + W Combo Key");
		player.setSpeedX(player.getSpeedX() - player.getAcceleration());
		player.setSpeedY(player.getSpeedY() + player.getAcceleration());
		if (player.getSpeedX() < -10.0f)
		    {
			player.setSpeedX(-10.0f);
		    }
		if (player.getSpeedY() > 10.0f)
		    {
			player.setSpeedY(10.0f);
		    }
		movePlayer(player, player.getSpeedX(), player.getSpeedY());

	    }
	else if (glfwGetKey(windowId, GLFW_KEY_D) == 1 && glfwGetKey(windowId, GLFW_KEY_S) == 1)
	    {
		System.out.println("D + S Combo Key");
		player.setSpeedX(player.getSpeedX() + player.getAcceleration());
		player.setSpeedY(player.getSpeedY() - player.getAcceleration());
		if (player.getSpeedX() > 10.0f)
		    {
			player.setSpeedX(10.0f);
		    }
		if (player.getSpeedY() < -10.0f)
		    {
			player.setSpeedY(-10.0f);
		    }
		movePlayer(player, player.getSpeedX(), player.getSpeedY());

	    }
	else if (glfwGetKey(windowId, GLFW_KEY_D) == 1 && glfwGetKey(windowId, GLFW_KEY_W) == 1)
	    {
		System.out.println("D + W Combo Key");
		player.setSpeedX(player.getSpeedX() + player.getAcceleration());
		player.setSpeedY(player.getSpeedY() + player.getAcceleration());
		if (player.getSpeedX() > 10.0f)
		    {
			player.setSpeedX(10.0f);
		    }
		if (player.getSpeedY() > 10.0f)
		    {
			player.setSpeedY(10.0f);
		    }
		movePlayer(player, player.getSpeedX(), player.getSpeedY());

	    }
	else if (glfwGetKey(windowId, GLFW_KEY_A) == 1)
	    {
		System.out.println("A Key");
		player.setSpeedX(player.getSpeedX() - player.getAcceleration());
		if (player.getSpeedX() < -10.0f)
		    {
			player.setSpeedX(-10.0f);
		    }
		movePlayer(player, player.getSpeedX(), 0.0f);
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_D) == 1)
	    {
		System.out.println("D Key");
		player.setSpeedX(player.getSpeedX() + player.getAcceleration());
		if (player.getSpeedX() > 10.0f)
		    {
			player.setSpeedX(10.0f);
		    }
		movePlayer(player, player.getSpeedX(), 0.0f);
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_S) == 1)
	    {
		System.out.println("S Key");
		player.setSpeedY(player.getSpeedY() - player.getAcceleration());
		if (player.getSpeedY() < -10.0f)
		    {
			player.setSpeedY(-10.0f);
		    }
		movePlayer(player, 0.0f, player.getSpeedY());
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_W) == 1)
	    {
		System.out.println("W Key");
		player.setSpeedY(player.getSpeedY() + player.getAcceleration());
		if (player.getSpeedY() > 10.0f)
		    {
			player.setSpeedY(10.0f);
		    }
		movePlayer(player, 0.0f, player.getSpeedY());
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_ESCAPE) == 1)
	    {
		System.out.println("Escape Key");
		isRunning = false;
	    }
    }

    public void decceleratePlayer(Square player)
    {
	// Check if horzontal keys are not being pressed.
	// Not pressed, then we check for horizontal speed.
	// Horzontal speed not 0.0, apply decceleartaion.
	if (glfwGetKey(windowId, GLFW_KEY_A) == 0 && glfwGetKey(windowId, GLFW_KEY_D) == 0)
	    {
		if (player.getSpeedX() < 0.0f)
		    {
			player.setSpeedX(player.getSpeedX() + player.getAcceleration());
			movePlayer(player, player.getSpeedX(), 0.0f);
		    }
		else if (player.getSpeedX() > 0.0f)
		    {
			player.setSpeedX(player.getSpeedX() - player.getAcceleration());
			movePlayer(player, player.getSpeedX(), 0.0f);
		    }
	    }

	// Vertical decceleartion.
	if (glfwGetKey(windowId, GLFW_KEY_W) == 0 && glfwGetKey(windowId, GLFW_KEY_S) == 0)
	    {
		if (player.getSpeedY() < 0.0f)
		    {
			player.setSpeedY(player.getSpeedY() + player.getAcceleration());
			movePlayer(player, 0.0f, player.getSpeedY());
		    }
		else if (player.getSpeedY() > 0.0f)
		    {
			player.setSpeedY(player.getSpeedY() - player.getAcceleration());
			movePlayer(player, 0.0f, player.getSpeedY());
		    }
	    }
	
    }
    
    public void movePlayer(Square player, float deltaX, float deltaY)
    {
	player.setPosX(player.getPosX() + isOutOfBoundsX(player, deltaX));
	player.setPosY(player.getPosY() + isOutOfBoundsY(player, deltaY));

	System.out.println("Player X: " + player.getPosX());
	System.out.println("Player Y: " + player.getPosY());
	System.out.println("Center coords: " + player.getCenterX() + "," + player.getCenterY());
    }

    // Bind the player to the X dimension of the board.
    public float isOutOfBoundsX(Square player, float deltaX)
    {
	if (player.getCenterX() + deltaX > WIDTH)
	    {
		return 0.0f;
	    }
	else if (player.getCenterX() + deltaX < 0)
	    {
		return 0.0f;
	    }
	player.setCenterX(player.getCenterX() + deltaX);
	return deltaX;
    }

    // Bind the palyer to the Y dimension of the board.
    public float isOutOfBoundsY(Square player, float deltaY)
    {
	if (player.getCenterY() + deltaY > HEIGHT)
	    {
		return 0.0f;
	    }
	else if (player.getCenterY()+ deltaY < 0)
	    {
		return 0.0f;
	    }
	player.setCenterY(player.getCenterY() + deltaY);
	return deltaY;
    }

    public void patrolEnemy(Square enemy, float deltaX)
    {
	// If enemy should move right
	// check that we are not out of bounds (IE > width)
	// if we are don't move and switch right to false
	// If should move left
	// multiply delta by -1
	// check that we are not out of bounds (IE > 0)
	// if we are don't move and switch right to true.
	if (right)
	    {
		if (enemy.getCenterX() + deltaX > WIDTH)
		    {
			right = false;
		    }
		else
		    {
			enemy.setPosX(enemy.getPosX() + deltaX);
			enemy.setCenterX(enemy.getCenterX() + deltaX);
		    }
	    }
	else
	    {
		deltaX = deltaX * -1;
		if (enemy.getCenterX() + deltaX < 0)
		    {
			right = true;
		    }
		else
		    {
			enemy.setPosX(enemy.getPosX() + deltaX);
			enemy.setCenterX(enemy.getCenterX() + deltaX);
		    }
	    }
    }

    // Method designed to increment the rgba and reset at 1.0f.
    public void changeBackground(Background aBackground)
    {
	if (aBackground.getRed() < 1.1)
	    {
		aBackground.setRed(aBackground.getRed() + 0.01f);
	    }
        if (aBackground.getRed() > 1.1)
	    {
		aBackground.setGreen(aBackground.getGreen() + 0.01f);
	    }
	if (aBackground.getGreen() > 1.1)
	    {
		aBackground.setBlue(aBackground.getBlue() + 0.01f);
	    }
	if (aBackground.getBlue() > 1.1)
	    {
		aBackground.setRed(0.0f);
		aBackground.setGreen(0.0f);
		aBackground.setBlue(0.0f);
	    }
    }

    public void drawGradientRectangle(int bufferId, ByteBuffer buffer)
    {
	IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
	IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
	glfwGetWindowSize(windowId, widthBuffer, heightBuffer);
	int width = widthBuffer.get(0);
	int height = heightBuffer.get(0);
	glBindTexture(GL_TEXTURE_2D, bufferId);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	glDrawPixels(width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
    }

    public void drawSquare(Square aSquare, int bufferId, float posX, float posY, ByteBuffer buffer)
    {
	glBindTexture(GL_TEXTURE_2D, bufferId);
	glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, (int)aSquare.getWidth(), (int)aSquare.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	glBegin(GL_QUADS);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(posX, posY, 0.0f);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(posX + aSquare.getWidth(), posY, 0.0f);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(posX + aSquare.getWidth(), posY + aSquare.getHeight(), 0.0f);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(posX, posY + aSquare.getHeight(), 0.0f);
	glEnd();
    }

    public void drawSquareSprite(Square aSquare, int bufferId, float posX, float posY, ByteBuffer buffer, int animateSpriteFrame)
    {
	if (animateSpriteFrame > 8 || animateSpriteFrame == 9)
	    {
		this.animateSpriteFrame = 1;
	    }
	float xValMin = 0.125f * (animateSpriteFrame - 1);
	float xValMax = 0.125f * animateSpriteFrame;
	glBindTexture(GL_TEXTURE_2D, bufferId);
	glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 864, 280, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	glBegin(GL_QUADS);
	glTexCoord2f(xValMin, 0.5f);
	glVertex3f(posX, posY, 0.0f);
	glTexCoord2f(xValMax, 0.5f);
	glVertex3f(posX + aSquare.getWidth(), posY, 0.0f);
	glTexCoord2f(xValMax, 0.0f);
	glVertex3f(posX + aSquare.getWidth(), posY + aSquare.getHeight(), 0.0f);
	glTexCoord2f(xValMin, 0.0f);
	glVertex3f(posX, posY + aSquare.getHeight(), 0.0f);
	glEnd();
    }

    public void loadGradientRectangle(ArrayList<BufferTuple> bufferList)
    {
	ByteBuffer buffer = BufferUtils.createByteBuffer(WIDTH*HEIGHT*4);

	int red = 0;
	int green = 255;
	int blue = 0;
	int alpha = 255;

	for (int i = 0; i < WIDTH; i++)
	    {
		for (int j = 0; j < HEIGHT; j++)
		    {
			buffer.put((byte) red);
			buffer.put((byte) green);
			buffer.put((byte) blue);
			buffer.put((byte) alpha);

			blue++;
			if (blue > 255)
			    {
				blue = 0;
			    }
		    }
		green--;
		if (green < 0)
		    {
			green = 255;
		    }
		blue = 0;
	    }

        buffer.flip();

	int bufferId = glGenTextures();
	System.out.println("Buffer ID: " + bufferId);

	BufferTuple bufferTuple = new BufferTuple(buffer, bufferId);
	bufferList.add(bufferTuple);
    }

    public void loadTexture(ArrayList<BufferTuple> bufferList, String imagePath)
    {
	BufferedImage img = null;
	try
	    {
		img = ImageIO.read(new File(imagePath));
	    }
	catch (Exception e)
	    {
		System.out.println("Failed to load image...");
		e.printStackTrace();
	    }

	ByteBuffer buffer = BufferUtils.createByteBuffer(img.getHeight()*img.getWidth()*4);
	System.out.println("Image type: " + img.getType());

	// Minus 1 from image height because we are decrementing.  See about incrementing to draw going updwards.
	for(int y = 0; y < img.getHeight(); y++)
	    {
		for(int x = 0; x < img.getWidth(); x++)
		    {
			int pixel = img.getRGB(x, y);
			buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red
			buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green
			buffer.put((byte) (pixel & 0xFF)); // Blue 
			buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha 
		    }
	    }

	
        buffer.flip(); // Flip to render the texture.

	int bufferId = glGenTextures(); // Get the texture ID.
	System.out.println("Buffer ID: " + bufferId);

	BufferTuple bufferTuple = new BufferTuple(buffer, bufferId);
	bufferList.add(bufferTuple);
    }

    public ByteBuffer updateGradient(long windowId, ByteBuffer buffer)
    {
	IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
	IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
	glfwGetWindowSize(windowId, widthBuffer, heightBuffer);
	int width = widthBuffer.get(0);
	int height = heightBuffer.get(0);

	buffer.clear();
	buffer = BufferUtils.createByteBuffer(width*height*4);

	int red = 0;
	int green = 255;
	int blue = 0;
	int alpha = 255;

	for (int i = 0; i < width; i++)
	    {
		for (int j = 0; j < height; j++)
		    {
			buffer.put((byte) red);
			buffer.put((byte) green);
			buffer.put((byte) blue);
			buffer.put((byte) alpha);

			blue++;
			if (blue > 255)
			    {
				blue = 0;
			    }
		    }
		green--;
		if (green < 0)
		    {
			green = 255;
		    }
		blue = 0;
	    }

        buffer.flip();
	return buffer;
    }

    // Main method to run.
    public static void main(String[] args)
    {
	// Sample squares that probably won't stick around.
	Square player = new Square(0.0f, 1.0f, 0.0f, 0.0f, 256.0f, 256.0f, 68, 100);
	Square enemy = new Square(0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 200.0f, 128, 128);
	Square enemySprite = new Square(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 350.0f, 108, 140);

	// More realistic sized squares for heroes and enemies.
	Square hero1 = new Square(0.0f, 0.0f, 0.0f, 0.0f, 64.0f, 64.0f, 32, 64);
	Square hero2 = new Square(0.0f, 0.0f, 0.0f, 0.0f, 128.0f, 64.0f, 32, 64);
	Square hero3 = new Square(0.0f, 0.0f, 0.0f, 0.0f, 192.0f, 64.0f, 32, 64);
	Square enemy1 = new Square(0.0f, 0.0f, 0.0f, 0.0f, 64.0f, 128.0f, 32, 64);
	Square enemy2 = new Square(0.0f, 0.0f, 0.0f, 0.0f, 128.0f, 128.0f, 32, 64);
	Square enemy3 = new Square(0.0f, 0.0f, 0.0f, 0.0f, 192.0f, 128.0f, 32, 64);

	// Terrain squares.
	Square terrainGrass = new Square(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 64, 64);
	Square terrainWater = new Square(0.0f, 0.0f, 0.0f, 0.0f, 64.0f, 0.0f, 64, 64);
	Square terrainTree = new Square(0.0f, 0.0f, 0.0f, 0.0f, 256.0f, 0.0f, 64, 128);
	Background background = new Background();
	Tloll tloll = new Tloll();

	tloll.initializeGL();
	glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
	tloll.windowId = tloll.createWindow(WIDTH, HEIGHT, TITLE);
	glfwMakeContextCurrent(tloll.windowId);
	glfwSwapInterval(1);
	glfwShowWindow(tloll.windowId);

	GL.createCapabilities(); // Creates bindings for OpenGL to be used.

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0, 500, 0, 500, 1, -1);
	glMatrixMode(GL_MODELVIEW);
	glEnable(GL_TEXTURE_2D);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	// BufferID = bufferList.lenght - 1
	tloll.loadGradientRectangle(tloll.bufferList);
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/player.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/enemy.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/enemy_left.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/grass.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/water.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/tree.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/hero1.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/hero2.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/hero3.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/enemy1.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/enemy2.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/enemy3.png");
	tloll.loadTexture(tloll.bufferList, currentDir + "/Assets/Images/SpriteSheet.png");

	while (isRunning)
	    {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the frame buffer.
		tloll.bufferList.get(0).setBuffer(tloll.updateGradient(tloll.windowId, tloll.bufferList.get(0).getBuffer()));
		tloll.drawGradientRectangle(tloll.bufferList.get(0).getBufferId(), tloll.bufferList.get(0).getBuffer());

		// Draw oversized characters.
		tloll.drawSquare(player, tloll.bufferList.get(1).getBufferId(), player.getPosX(), player.getPosY(), tloll.bufferList.get(1).getBuffer());
		if (right)
		    {
			tloll.drawSquare(enemy, tloll.bufferList.get(2).getBufferId(), enemy.getPosX(), enemy.getPosY(), tloll.bufferList.get(2).getBuffer());
		    }
		else
		    {
			tloll.drawSquare(enemy, tloll.bufferList.get(3).getBufferId(), enemy.getPosX(), enemy.getPosY(), tloll.bufferList.get(3).getBuffer());
		    }

		tloll.drawSquareSprite(enemySprite, tloll.bufferList.get(13).getBufferId(), enemySprite.getPosX(), enemySprite.getPosY(), tloll.bufferList.get(13).getBuffer(), animateSpriteFrame);
		if (frameSkip < 0)
		    {
			animateSpriteFrame++;
			frameSkip = 2;
		    }
		frameSkip--;

		// Draw terrains.
		tloll.drawSquare(terrainGrass, tloll.bufferList.get(4).getBufferId(), terrainGrass.getPosX(), terrainGrass.getPosY(), tloll.bufferList.get(4).getBuffer());
		tloll.drawSquare(terrainWater, tloll.bufferList.get(5).getBufferId(), terrainWater.getPosX(), terrainWater.getPosY(), tloll.bufferList.get(5).getBuffer());
		tloll.drawSquare(terrainTree, tloll.bufferList.get(6).getBufferId(), terrainTree.getPosX(), terrainTree.getPosY(), tloll.bufferList.get(6).getBuffer());

		// Draw normal sized heroes.
		tloll.drawSquare(hero1, tloll.bufferList.get(7).getBufferId(), hero1.getPosX(), hero1.getPosY(), tloll.bufferList.get(7).getBuffer());
		tloll.drawSquare(hero2, tloll.bufferList.get(8).getBufferId(), hero2.getPosX(), hero2.getPosY(), tloll.bufferList.get(8).getBuffer());
		tloll.drawSquare(hero3, tloll.bufferList.get(9).getBufferId(), hero3.getPosX(), hero3.getPosY(), tloll.bufferList.get(9).getBuffer());
		tloll.drawSquare(enemy1, tloll.bufferList.get(10).getBufferId(), enemy1.getPosX(), enemy1.getPosY(), tloll.bufferList.get(10).getBuffer());
		tloll.drawSquare(enemy2, tloll.bufferList.get(11).getBufferId(), enemy2.getPosX(), enemy2.getPosY(), tloll.bufferList.get(11).getBuffer());
		tloll.drawSquare(enemy3, tloll.bufferList.get(12).getBufferId(), enemy3.getPosX(), enemy3.getPosY(), tloll.bufferList.get(12).getBuffer());

		// Set enemies to patrol.
		tloll.patrolEnemy(enemy, 10.0f);

		// Sample code that can set the background for any color desired with
		// background object that developer can create.
		// glClearColor(background.getRed(), background.getGreen(), background.getBlue(), background.getAlpha());

		tloll.bindKeys(player); // Allow movement with keyboard.
		tloll.decceleratePlayer(player);

		glfwSwapBuffers(tloll.windowId); // Swaps buffers that will be drawn.

		glfwPollEvents(); // Continuosuly polls.

		// Check window close was called.
		if (glfwWindowShouldClose(tloll.windowId))
		    {
			isRunning = false;
		    }
	    }
    }
}


    class BufferTuple
    {
	private ByteBuffer buffer;
	private int bufferId;

	BufferTuple()
	{
	    // Do nothing in this base constructor.  Always should have this tuple.
	}

	BufferTuple(ByteBuffer buffer, int bufferId)
	{
	    this.buffer = buffer;
	    this.bufferId = bufferId;
	}

	public ByteBuffer getBuffer()
	{
	    return buffer;
	}

	public void setBuffer(ByteBuffer buffer)
	{
	    this.buffer = buffer;
	}

	public int getBufferId()
	{
	    return bufferId;
	}
    }

    class Background
    {
	private float red;
	private float green;
	private float blue;
	private float alpha;

	Background()
	{
	    this.red = 1.0f;
	    this.green = 0.0f;
	    this.blue = 0.0f;
	    this.alpha = 0.0f;
	}

	Background(float red, float green, float blue, float alpha)
	{
	    this.red = red;
	    this.green = green;
	    this.blue = blue;
	    this.alpha = alpha;
	}

	public float getRed()
	{
	    return red;
	}

	public void setRed(float red)
	{
	    this.red = red;
	}

	public float getGreen()
	{
	    return green;
	}

	public void setGreen(float green)
	{
	    this.green = green;
	}

	public float getBlue()
	{
	    return blue;
	}

	public void setBlue(float blue)
	{
	    this.blue = blue;
	}

	public float getAlpha()
	{
	    return alpha;
	}

	public void setAlpha(float alpha)
	{
	    this.alpha = alpha;
	}

    }

    class Triangle
    {

	private float red;
	private float green;
	private float blue;
	private float x1;
	private float y1;
	private float x2;
	private float y2;
	private float x3;
	private float y3;
	private float centerX;
	private float centerY;

	Triangle()
	{
	    this.red = 0.0f;
	    this.green = 0.0f;
	    this.blue = 0.0f;
	    this.x1 = 0;
	    this.y1 = 0;
	    this.x2 = 100;
	    this.y2 = 0;
	    this.x3 = 100;
	    this.y3 = 100;
	    this.centerX = (this.x1 + this.x2 + this.x3) / 3;
	    this.centerX = (this.y1 + this.y2 + this.y3) / 3;
	}


	Triangle(float red, float green, float blue, float x1, float y1, float x2, float y2, float x3, float y3)
	{
	    this.red = red;
	    this.green = green;
	    this.blue = blue;
	    this.x1 = x1;
	    this.y1 = y1;
	    this.x2 = x2;
	    this.y2 = y2;
	    this.x3 = x3;
	    this.y3 = y3;
	    this.centerX = (this.x1 + this.x2 + this.x3) / 3;
	    this.centerX = (this.y1 + this.y2 + this.y3) / 3;
	}

	public float getCenterX()
	{
	    return this.centerX;
	}

	public void setCenterX()
	{
	    this.centerX = (this.x1 + this.x2 + this.x3) / 3;
	}

	public float getCenterY()
	{
	    return this.centerY;
	}

	public void setCenterY()
	{
	    this.centerY = (this.y1 + this.y2 + this.y3) / 3;
	}

	public float getRed()
	{
	    return red;
	}

	public void setRed(float red)
	{
	    this.red = red;
	}

	public float getGreen()
	{
	    return green;
	}

	public void setGreen(float green)
	{
	    this.green = green;
	}

	public float getBlue()
	{
	    return blue;
	}

	public void setBlue(float blue)
	{
	    this.blue = blue;
	}

	public float getX1()
	{
	    return x1;
	}

	public void setX1(float x1)
	{
	    this.x1 = x1;
	}

	public float getY1()
	{
	    return y1;
	}

	public void setY1(float y1)
	{
	    this.y1 = y1;
	}

	public float getX2()
	{
	    return x2;
	}

	public void setX2(float x2)
	{
	    this.x2 = x2;
	}

	public float getY2()
	{
	    return y2;
	}

	public void setY2(float y2)
	{
	    this.y2 = y2;
	}

	public float getX3()
	{
	    return x3;
	}

	public void setX3(float x3)
	{
	    this.x3 = x3;
	}

	public float getY3()
	{
	    return y3;
	}

	public void setY3(float y3)
	{
	    this.y3 = y3;
	}
    }
