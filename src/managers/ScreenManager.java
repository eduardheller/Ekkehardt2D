package managers;

import java.awt.*;
import config.Config;

/**
* Das ist die Screenklasse. Sie behandelt den Bildschirm und stellt eine Auflösung zu verfügung.
* Außerdem initialisiert sie den Bildschirm und erstellt einen Fullscreenmodus
* @author  Eduard Heller
* @version 1.0
*/
public class ScreenManager{
	
	public static GraphicsDevice device = null;
	
	// Hier stehen die Auflösungen inkl. Farbtiefe drinne
	private DisplayMode[] _bestDisplayModes = new DisplayMode[] {
		new DisplayMode(640, 480, 32, 0),
		new DisplayMode(640, 480, 16, 0)
	};
	
	/**
	 * Konstruktor der ScreenManager Klasse
	 * @param mainFrame Fenster das behandelt werden muss
	 */
	public ScreenManager(Frame mainFrame){
		 GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		 device = env.getDefaultScreenDevice();
	     device.setFullScreenWindow(mainFrame);
	     if(device.isDisplayChangeSupported()){
	    	 _chooseBestDisplayMode(device);
	     }
	     mainFrame.createBufferStrategy(Config.BUFFERS);

	}
	
	/**
	 * @param device GraphicsDevice des Screens
	 * @return die beste Auflösung
	 */
	private DisplayMode _getBestDisplayMode(GraphicsDevice device){
		for(int x = 0; x < _bestDisplayModes.length; x++){
			DisplayMode[] modes = device.getDisplayModes();
			for(int i = 0; i < modes.length; i++){
				if(modes[i].getWidth() == _bestDisplayModes[x].getWidth() &&
				modes[i].getHeight() == _bestDisplayModes[x].getHeight() &&
				modes[i].getBitDepth() == _bestDisplayModes[x].getBitDepth()){
					return _bestDisplayModes[x];
				}
			}
		}
		return null;
	}
	
	/**
	 * @param device GraphicsDevice des Screens
	 */
	private void _chooseBestDisplayMode(GraphicsDevice device){
		DisplayMode bestMode = _getBestDisplayMode(device);
		if (bestMode!=null){
			device.setDisplayMode(bestMode);
		}else{
			System.out.println("ERROR: Es wurde keine richtige Auflösung gefunden!");
		}
	}

	
}
