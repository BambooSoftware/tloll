package com.bamboo.tloll.graphics;

import org.lwjgl.system.Platform;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.BufferUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;

public class GraphicsUtil
{

    public GraphicsUtil()
    {
    }
    
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

    public ByteBuffer loadTexture(String imagePath)
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

	int textureNumber = glGenTextures(); // Get the texture ID.
	System.out.println("Loaded texture number: " + textureNumber);

	return buffer;
    }
    
}
