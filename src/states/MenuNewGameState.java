package states;

import java.awt.Color;
import java.awt.Graphics2D;

import managers.GameStateManager;
import sprite.Sprite;
import utils.BitmapFont;
import utils.Score;
import utils.SoundEffect;
import world.World;

import java.awt.event.*;

import config.Config;

public class MenuNewGameState extends State{

	private Sprite jackPlayerOne;
	private Sprite jasminPlayerOne;
	private Sprite chrisPlayerOne;
	private Sprite noonePlayerOne;
	private Sprite jackPlayerTwo;
	private Sprite jasminPlayerTwo;
	private Sprite chrisPlayerTwo;
	private Sprite noonePlayerTwo;
	public static int chooseP1 = 0;
	public static int chooseP2 = 0;
	Sprite _background;
	SoundEffect choiceSound = new SoundEffect("CHOICE");
	SoundEffect selectSound = new SoundEffect("SELECT");
	
	private int _delay = 2000;
	
	private boolean _started = false;
	
	BitmapFont _bmf;
	BitmapFont _bmf2;
	
	private boolean _chosen;
	@Override
	public void init() {
		World.hardnessLevel = 1;
		Score.reset();
		World.players.clear();
		chooseP1 = 0;
		chooseP2 = 0;
		_chosen = false;
		_background = new Sprite("res/menu/menu.png");
		_background.scale(2, 2);
		_background.translate(160,120);
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,false);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,false);
		selectSound.play();
		jackPlayerOne = new Sprite("res/menu/jack.png");
		jasminPlayerOne = new Sprite("res/menu/jasmin.png");
		chrisPlayerOne = new Sprite("res/menu/chris.png");
		noonePlayerOne = new Sprite("res/menu/noone.png");
		jackPlayerTwo = new Sprite("res/menu/jack.png");
		jasminPlayerTwo = new Sprite("res/menu/jasmin.png");
		chrisPlayerTwo = new Sprite("res/menu/chris.png");
		noonePlayerTwo = new Sprite("res/menu/noone.png");

		noonePlayerOne.translate(screen.getWidth()/2-225, screen.getHeight()/2-175);
		jasminPlayerOne.translate(screen.getWidth()/2-100, screen.getHeight()/2-175);
		chrisPlayerOne.translate(screen.getWidth()/2+25, screen.getHeight()/2-175);
		jackPlayerOne.translate(screen.getWidth()/2+150, screen.getHeight()/2-175);
		noonePlayerTwo.translate(screen.getWidth()/2-225, screen.getHeight()/2+25);
		jasminPlayerTwo.translate(screen.getWidth()/2-100, screen.getHeight()/2+25);
		chrisPlayerTwo.translate(screen.getWidth()/2+25, screen.getHeight()/2+25);
		jackPlayerTwo.translate(screen.getWidth()/2+150, screen.getHeight()/2+25);
		_started = false;
	}

	@Override
	public void update(Graphics2D g, double delta) {
		
		_background.show(g);
		g.setColor(Color.RED);
		
		jackPlayerOne.show(g);
		jasminPlayerOne.show(g);
		chrisPlayerOne.show(g);
		noonePlayerOne.show(g);
		jackPlayerTwo.show(g);
		jasminPlayerTwo.show(g);
		chrisPlayerTwo.show(g);
		noonePlayerTwo.show(g);
		
		// 0 Nothing
		// 1 Jasmin
		// 2 Chris
		// 3 Jack
		
		if(!_started){
			if(input.isKeyUp(KeyEvent.VK_D)){
				chooseP1=(chooseP1+1)%4;
			}
			if(input.isKeyUp(KeyEvent.VK_A)){
				chooseP1=(chooseP1-1)%4;
					if(chooseP1==-1){
						chooseP1=3;
					}
			}
			if(input.isKeyUp(KeyEvent.VK_RIGHT)){
				chooseP2=(chooseP2+1)%4;
			}
			if(input.isKeyUp(KeyEvent.VK_LEFT)){
				chooseP2=(chooseP2-1)%4;
					if(chooseP2==-1){
						chooseP2=3;
					}
			}
		}

		
		switch (chooseP1) {
		case 0:
			g.drawRect(screen.getWidth()/2-225, screen.getHeight()/2-175, 80, 128);
			break;
		case 1:
			g.drawRect(screen.getWidth()/2-100, screen.getHeight()/2-175, 80, 128);
			break;
		case 2:
			g.drawRect(screen.getWidth()/2+25, screen.getHeight()/2-175, 80, 128);
			break;
		case 3:
			g.drawRect(screen.getWidth()/2+150, screen.getHeight()/2-175, 80, 128);
			break;
		default:
			break;
		}
		switch (chooseP2) {
		case 0:
			g.drawRect(screen.getWidth()/2-225, screen.getHeight()/2+25, 80, 128);
			break;
		case 1:
			g.drawRect(screen.getWidth()/2-100, screen.getHeight()/2+25, 80, 128);
			break;
		case 2:
			g.drawRect(screen.getWidth()/2+25, screen.getHeight()/2+25, 80, 128);
			break;
		case 3:
			g.drawRect(screen.getWidth()/2+150, screen.getHeight()/2+25, 80, 128);
			break;
		default:
			break;
		}
		g.setColor(Color.WHITE);
		_bmf.drawString(g,"Jack", screen.getWidth()/2+165, screen.getHeight()/2-42);
		_bmf.drawString(g,"Jasmin", screen.getWidth()/2-75, screen.getHeight()/2-42);
		_bmf.drawString(g,"Chris", screen.getWidth()/2+45, screen.getHeight()/2-42);
		_bmf.drawString(g,"Jack", screen.getWidth()/2+165, screen.getHeight()/2+158);
		_bmf.drawString(g,"Jasmin", screen.getWidth()/2-75, screen.getHeight()/2+158);
		_bmf.drawString(g,"Chris", screen.getWidth()/2+45, screen.getHeight()/2+158);
		_bmf.drawString(g,"Player 1 - Auswahl mit den Tasten A und D", screen.getWidth()/2-41, screen.getHeight()/2-185);
		_bmf.drawString(g,"Player 2 - Auswahl mit den Tasten LINKS und RECHTS", screen.getWidth()/2, screen.getHeight()/2+15);
		
		if(chooseP1==0 && chooseP2==0){
			_bmf2.drawString(g,"Mindestens ein Spieler muss gewaehlt werden", screen.getWidth()/2+80, screen.getHeight()-25);
			_bmf2.drawString(g,"Zurueck mit Backspace", screen.getWidth()/2-225, screen.getHeight()-25);
			if(input.isKeyUp(KeyEvent.VK_BACK_SPACE)){
				GameStateManager.getInstance().setState("MENU");
			}
		}else{
			_bmf2.drawString(g,"Starten mit ENTER", screen.getWidth()/2+80, screen.getHeight()-25);
			_bmf2.drawString(g,"Zurueck mit Backspace", screen.getWidth()/2-225, screen.getHeight()-25);	
			if(input.isKeyUp(KeyEvent.VK_ENTER)){
				if(!_started){
					choiceSound.play();
				}
				_started = true;
			}
			if(!_chosen){
				if(input.isKeyUp(KeyEvent.VK_BACK_SPACE)){
					_chosen = true;
					GameStateManager.getInstance().setState("MENU");
				}
			}

		}
		if(_started){
			_delay -= Config.TIMER_DIF*delta;
			if(_delay<=0){
				GameStateManager.getInstance().setState("INTRO");
				_delay = 2000;
			}
			
		}

	}

}
