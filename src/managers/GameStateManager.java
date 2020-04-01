package managers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import sprite.Sprite;
import states.*;
import utils.BitmapFont;
import world.RessourceLoader;

/**
* Die Gamestatemanagerklasse behandelt die Gamestates. Es ist eine Singeltonklasse.
* @author  Eduard Heller
* @version 1.0
*/
public class GameStateManager {
	
	private static GameStateManager instance = null;
	private static HashMap<String,State> gameStates;
	private static String currentState = "";
	private float alpha = 1.0f;
	private float alphaOut = 0.0f;
	private boolean hasFadeOut = true;
	private boolean hasFadeIn = true;
	private String oldState;
	private static RessourceLoader _resLoader;
	
	public DisplayMode screen;	
	public boolean pause = false;
	private int highlight = 0;
	/**
	 * Singeltoninstanzbehandlung
	 * @return die Instanz
	 */
	public static GameStateManager getInstance(){
		if(instance == null){
			instance = new GameStateManager();
			_resLoader = new RessourceLoader();
			gameStates = new HashMap<String,State>();
			
			_resLoader.loadAnimations();
			// Hier werden die Gamestates eingefügt
			gameStates.put("INTRO",new IntroState());
			gameStates.put("INTRO_HEROES",new IntroStateHeroes());
			gameStates.put("MENU",new MenuState());
			gameStates.put("LEVEL_1", new Level1());
			gameStates.put("LEVEL_2", new Level2());
			gameStates.put("LEVEL_3", new Level3());
			gameStates.put("LEVEL_4", new Level4());
			gameStates.put("LEVEL_5", new Level5());
			gameStates.put("OUTRO", new OutroState());
			gameStates.put("ENDLEVELSCENE", new EndLevelSceneState());
			gameStates.put("BOSSSCENE", new BossSceneState());
			gameStates.put("MENU_ANLEITUNG", new MenuAnleitungState());
			gameStates.put("MENU_BEENDEN", new MenuBeendenState());
			gameStates.put("MENU_CREDITS", new MenuCreditsState());
			gameStates.put("MENU_HIGHSCORE", new MenuHighscoreState());
			gameStates.put("MENU_NEW_GAME", new MenuNewGameState());
			gameStates.put("GAMEOVER",new GameOverState());
			gameStates.put("RESULT",new NewStartState());
		}
		return instance;
	}
	
	/**
	 * Setzt einen neuen State
	 * @param key Schlüssel des States
	 */
	public void setState(String key){
		if(gameStates.get(key)!=null){
			oldState = key;
			alpha = 1.0f;
			alphaOut = 0.0f;
			hasFadeOut = false;	
		}else{
			System.out.println("ERROR: State exisitert nicht: "+key);
		}

	}
	
	/**
	 * @return aktueller Statename
	 */
	public String getCurrentState(){
		return currentState;
	}
	
	
	
	/**
	 * Diese Updatefunktion wird unabhängig vom State, in der Hauptschleife des Games aufgerufen
	 * @param g Graphics2D Objekt
	 * @param deltaTime 
	 */
	public void update(Graphics2D g,double deltaTime){
		if(oldState!=""){
			if(currentState!=""){
				if (!pause) {
					gameStates.get(currentState).update(g,deltaTime);
				} else {					
					pause(g);
				}
			}
			Composite original = g.getComposite();
			
			if(!hasFadeIn){
				// FadeIn
			    g.setComposite(AlphaComposite.getInstance(
			            AlphaComposite.SRC_OVER, alpha));
			    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			    g.setColor(Color.BLACK);
			    //do the drawing here
			    g.fillRect(0, 0, 640, 480);

			    //increase the opacity and repaint
			    alpha -= 0.05f*deltaTime;
			    if (alpha <= 0.0f) {
			        alpha = 1.0f;
			        alphaOut = 0.0f;
			        hasFadeIn = true;
			    } 
			    g.setComposite(original);
			}

		    
		    
		    if(!hasFadeOut){
			    // FadeOut
			    g.setComposite(AlphaComposite.getInstance(
			            AlphaComposite.SRC_OVER, alphaOut));
			    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			    g.setColor(Color.BLACK);
			    //do the drawing here
			    g.fillRect(0, 0, 640, 480);

			    //increase the opacity and repaint
			    alphaOut += 0.05f*deltaTime;
			    if (alphaOut >= 1.0f) {
			    	hasFadeOut = true;
			    	hasFadeIn = false;
			    	gameStates.get(oldState).init();
			    	currentState = oldState;
					alpha = 1.0f;
					alphaOut = 0.0f;
			    } 
			    g.setComposite(original);
		    }

		}else{
			System.out.println("ERROR: Du versuchst einen State zu updaten der nicht gesetzt wurde!");
		}
	}
	
	public void pause(Graphics2D g) {

		DisplayMode screen = ScreenManager.device.getDisplayMode();
		InputManager input = InputManager.getInstance();
		BitmapFont _bmf, _bmf2;
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,false);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,false);
		_bmf2.drawString(g,"PAUSE", screen.getWidth()/2, screen.getHeight()/2-50);
		
		if (highlight%2 == 0) {
			_bmf2.drawString(g,"Continue", screen.getWidth()/2, screen.getHeight()/2+25);
			_bmf.drawString(g,"Exit", screen.getWidth()/2, screen.getHeight()/2+50);
		} else {
			_bmf.drawString(g,"Continue", screen.getWidth()/2, screen.getHeight()/2+25);
			_bmf2.drawString(g,"Exit", screen.getWidth()/2, screen.getHeight()/2+50);
		}
		
		if(input.isKeyUp(KeyEvent.VK_UP)){
			highlight++;
		}
		if(input.isKeyUp(KeyEvent.VK_DOWN)){
			highlight++;
		}
		if(input.isKeyUp(KeyEvent.VK_ENTER)){
			if (highlight%2 == 1) setState("MENU");
			pause = false;
		}
	}
	
	
}
