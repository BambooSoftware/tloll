package com.bamboo.tloll.debug;

public class Logger
{

    String filePath = "";
    
    public Logger()
    {
	
    }

    public Logger(String filePath)
    {
	this.filePath = filePath;
    }

    public void writeToFile()
    {
	// Method that will write out to a file if we ever needed it.
    }

    public void printDebugInfo(String level, String message)
    {
	switch (level)
	    {
	    case "INFO":
		System.out.println("INFO: " + message);
	    case "DEBUG":
		System.out.println("DEBUG: " + message);
	    case "ERROR":
		System.out.println("ERROR: " + message);
	    }
    }

    public void printToWindow(String message, float posX, float posY)
    {
	
    }
    
}
