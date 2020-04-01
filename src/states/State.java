package states;

import java.awt.DisplayMode;
import java.awt.Graphics2D;

import managers.InputManager;
import managers.ScreenManager;

/**
* Das ist eine Abstrakte Klasse State. Sie ist das Fundament für ein State und muss von jedem State extended werden.
* @author  Eduard Heller
* @version 1.0
*/
public abstract class State{
	
	// Eingabeobjekt
	public InputManager input = InputManager.getInstance();
	
	// Screenobjekt
	public DisplayMode screen = ScreenManager.device.getDisplayMode();
	
	/**
	 * Initialisierungsfunktion
	 * Dort werden Klassenobjekte erstellt. Sprites geladen usw.
	 */
	public abstract void init();
	
	/**
	 * Updatefunktion
	 * Jede Frame wird die Updatefunktion gestartet. 
	 * Hier werden Sprites gerendert usw.
	 * @param g Graphics2D Objekt
	 * @param delta Deltatime
	 */
	public abstract void update(Graphics2D g,double delta);
	

}
