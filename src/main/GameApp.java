package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import managers.GameStateManager;
import managers.InputManager;
import managers.ScreenManager;
import utils.FpsHandler;
import utils.SoundEffect;
import world.World;
import config.Config;

/**
* Die Hauptklasse inkl. Mainloop und Fensterhandling. Sie wird von der Main aufgerufen.
* @author  Eduard Heller
* @version 1.0
*/
public class GameApp {
	
	private boolean _gameRunning = true;
	private Frame _frm;
	private FpsHandler _fpsh;
	private Font _font;
	
	SoundEffect menuSound = new SoundEffect("MENU");
	SoundEffect intro1Sound = new SoundEffect("INTRO");
	SoundEffect intro2Sound = new SoundEffect("INTRO_HEROES");
	SoundEffect level1Sound = new SoundEffect("LEVEL1");
	SoundEffect level2Sound = new SoundEffect("LEVEL2");
	SoundEffect level3Sound = new SoundEffect("LEVEL3");
	SoundEffect level4Sound = new SoundEffect("LEVEL4");
	SoundEffect level5Sound = new SoundEffect("BOSS_LEVEL");
	SoundEffect outro = new SoundEffect("OUTRO");
	SoundEffect overSound = new SoundEffect("GAMEOVER");
	/**
	 * Konstruktor der GameApp() Klasse. Sie erstellt das Fenster, geht in den Fullscreenmodus und startet die Gameloop
	 */
	public GameApp(){
		try{
			System.out.println("Erstelle Fenster");
			_font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("res/Gamegirl.ttf"));
			_font = _font.deriveFont(8f);
			_frm = new Frame();
			_frm.setUndecorated(true);
			_frm.setIgnoreRepaint(true);
			_frm.setTitle(Config.TITEL);
			_frm.addKeyListener(InputManager.getInstance());
			
			System.out.println("ScreenManager wird behandelt");
			
			new ScreenManager(_frm);
			
			System.out.println("Fps Handler wird initialisiert");
			
			_fpsh = new FpsHandler();
			GameStateManager.getInstance().setState("MENU");
			
	        _gameLoop();

		}catch (Exception e) {
            e.printStackTrace();
		}finally{
			ScreenManager.device.setFullScreenWindow(null);
			System.out.println("Fullscreen beendet. Spiel wird geschlossen");
			System.exit(0);
		}
	}
	
	/**
	 * Hauptschleife des Spieles. Erstellt auﬂerdem auch wichtige Variablen wie deltaTime.
	 */
	private void _gameLoop(){
		
		BufferStrategy buff = _frm.getBufferStrategy();
		long lastTime = System.nanoTime();
		long optimalTime = 1000000000 / Config.FPS;
		
		System.out.println("Game Loop initialisiert");
		

		while(_gameRunning){
			Graphics2D g = (Graphics2D)buff.getDrawGraphics();

			// Initialisere Timer und Deltatime
			long now = System.nanoTime();
			long updateTime = now - lastTime;
			lastTime = now;
			double deltaTime = updateTime / ((double)optimalTime);
			
			
			
	       
	        
	        
			
			// Rendering
			_initRendering(g);
			g.setFont(_font);
	
	
			if(GameStateManager.getInstance().getCurrentState()=="LEVEL_1"){
				menuSound.stop();
				//level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				level1Sound.loop();
			}else if(GameStateManager.getInstance().getCurrentState()=="LEVEL_2"){
				menuSound.stop();
				level1Sound.stop();
				//level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				level2Sound.loop();
			}else if(GameStateManager.getInstance().getCurrentState()=="LEVEL_3"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				//level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				level3Sound.loop();
			}else if(GameStateManager.getInstance().getCurrentState()=="LEVEL_4"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				//level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				level4Sound.loop();
			}else if(GameStateManager.getInstance().getCurrentState()=="LEVEL_5"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				//level5Sound.stop();
				outro.stop();
				overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				if(World._boss!=null){
					level5Sound.loop();
				}else{
					level5Sound.stop();
				}
				
			}	
			else if(GameStateManager.getInstance().getCurrentState()=="MENU"){
				//menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				menuSound.loop();
			}
			else if(GameStateManager.getInstance().getCurrentState()=="INTRO"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				overSound.stop();
				intro2Sound.stop();
				//intro1Sound.stop();
				
				intro1Sound.loop();
			}else if(GameStateManager.getInstance().getCurrentState()=="INTRO_HEROES"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				overSound.stop();
				//intro2Sound.stop();
				intro1Sound.stop();
				
				intro2Sound.loop();

			}else if(GameStateManager.getInstance().getCurrentState()=="GAMEOVER"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				//overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				overSound.loop();
			}else if(GameStateManager.getInstance().getCurrentState()=="MENU_HIGHSCORE"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				//overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				overSound.loop();
			}else if(GameStateManager.getInstance().getCurrentState()=="OUTRO"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				//outro.stop();
				overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				outro.play();
			}else if(GameStateManager.getInstance().getCurrentState()=="ENDLEVELSCENE"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
			}else if(GameStateManager.getInstance().getCurrentState()=="BOSSSCENE"){
				menuSound.stop();
				level1Sound.stop();
				level2Sound.stop();
				level3Sound.stop();
				level4Sound.stop();
				level5Sound.stop();
				outro.stop();
				//overSound.stop();
				intro2Sound.stop();
				intro1Sound.stop();
				
				overSound.play();
			}
			
			
				
			GameStateManager.getInstance().update(g, deltaTime);
			
			if(Config.DEBUG){
				_displayInfoText(g,updateTime);
			}

			// Graphik Ressourcen freigeben
			g.dispose();
			// Buffer anzeigen
			if (!buff.contentsLost())
		    {
				buff.show();
		    }
			
			// Beendentaste
			if(InputManager.getInstance().isKeyUp(KeyEvent.VK_ESCAPE)){
				if (World.currentLevel==0) {
					ScreenManager.device.setFullScreenWindow(null);
					System.exit(0);
					} else {
										
						if (GameStateManager.getInstance().pause == true) {
							GameStateManager.getInstance().pause = false;
						} else {
							GameStateManager.getInstance().pause = true;
					}
										
					
									
				}
			}
			
			 _changeGameStates();
			
			InputManager.getInstance().update();
			

			
			// Bildschirm Syncronisieren
			Toolkit.getDefaultToolkit().sync();

			// Jede 10ms updaten ( spart cpu auslastung und abh‰ngig von der FPS damit nichts ruckelt )
		    try {
                Thread.sleep((System.nanoTime()-lastTime + optimalTime)/10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
	}

	private void _changeGameStates(){
		if(InputManager.getInstance().isKeyUp(KeyEvent.VK_1)){
			GameStateManager.getInstance().setState("INTRO");
		}
		if(InputManager.getInstance().isKeyUp(KeyEvent.VK_2)){
			GameStateManager.getInstance().setState("MENU");
		}
		if(InputManager.getInstance().isKeyUp(KeyEvent.VK_3)){
			GameStateManager.getInstance().setState("LEVEL");
		}
		if(InputManager.getInstance().isKeyUp(KeyEvent.VK_4)){
			GameStateManager.getInstance().setState("LARSEDDYSTATE");
		}
	}
	
	/**
	 * Erneuert Hintergrund damit kein Flickern ensteht
	 * @param g Graphics2D Objekt
	 */
	private void _initRendering(Graphics2D g){
		g.setColor(Color.black);
		g.fillRect(0, 0, ScreenManager.device.getDisplayMode().getWidth(), ScreenManager.device.getDisplayMode().getHeight());

	}
	
	/**
	 * Zeigt Information und FPS des Spiels oben links am Bildschirm an
	 * @param g Graphics2D Objekt
	 * @param updateTime
	 */
	private void _displayInfoText(Graphics2D g,long updateTime){
		g.setColor(Color.white);
		g.drawString(Config.TITEL+ " "+ Config.VERSION, 20, 20);
		int fps = _fpsh.getFps(updateTime);
		g.drawString("FPS: "+fps, 20, 35);
		g.drawString("GAMESTATE: "+GameStateManager.getInstance().getCurrentState(),20,65);
		g.drawString("ZUM BEENDEN ESC KLICKEN", 20, 50);
		g.drawString("GAMESTSTATE WECHSELN: 1-9",20,85);

	}

	
}
