package com.bamboo.tloll.debug;

import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.graphics.GraphicsUtil;

public class Logger
{

    String filePath = "";
    String level = "";
    
    public Logger()
    {
	this.level = "INFO";
    }

    public Logger(String level)
    {
	this.level = level;
    }

    public Logger(String level, String filePath)
    {
	this.level = level;
	this.filePath = filePath;
    }

    public void writeToFile()
    {
	// Method that will write out to a file if we ever needed it.
    }

    public void printDebugInfo(String message)
    {
	switch (this.level)
	    {
	    case "INFO":
		System.out.println("INFO: " + message);
		break;
	    case "DEBUG":
		System.out.println("DEBUG: " + message);
		break;
	    case "ERROR":
		System.out.println("ERROR: " + message);
		break;
	    }
    }

    public void printToWindow(GraphicsUtil gu, String currentDir, String message, float posX, float posY, boolean leftToRight)
    {
	Renderer.drawString(gu, currentDir, posX, posY, message, leftToRight);
    }
    
}
