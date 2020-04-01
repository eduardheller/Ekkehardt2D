package states;

import java.awt.Color;
import java.awt.Graphics2D;

import sprite.Sprite;
import utils.BitmapFont;
import utils.SoundEffect;
import world.World;
import managers.GameStateManager;

import java.awt.event.*;

public class MenuState extends State{

	
	private boolean _chosen;
	private enum MenuStates {
	    NEW_GAME, ANLEITUNG, HIGHSCORE, CREDITS, BEENDEN
	}

	MenuStates selectedMenuState;
	BitmapFont _bmf;
	BitmapFont _bmf2;
	BitmapFont _bmf3;
	Sprite _background;
	@Override
	public void init() {
		// TODO Auto-generated method stub
		selectedMenuState = MenuStates.NEW_GAME;
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,false);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,false);
		_bmf3 = new BitmapFont(new Sprite("res/ui/BitmapFont3.gif"),13,17,false);
		_background = new Sprite("res/menu/menu.png");
		_background.scale(2, 2);
		_background.translate(160,120);
		_chosen = false;
		World.hardnessLevel = 1;
		World.currentLevel = 0;
	}

	@Override
	public void update(Graphics2D g, double delta) {
		_background.show(g);
		_bmf3.drawString(g,"ekkehardt fighters", screen.getWidth()/2, 150);
		if(selectedMenuState == MenuStates.NEW_GAME){
			_bmf2.drawString(g,"New Game", screen.getWidth()/2, screen.getHeight()/2+25);
			_bmf.drawString(g,"Anleitung", screen.getWidth()/2, screen.getHeight()/2+50);
			_bmf.drawString(g,"Highscore", screen.getWidth()/2, screen.getHeight()/2+75);
			_bmf.drawString(g,"Credits", screen.getWidth()/2, screen.getHeight()/2+100);
			_bmf.drawString(g,"Beenden", screen.getWidth()/2, screen.getHeight()/2+125);
			
			if(!_chosen){
				if(input.isKeyUp(KeyEvent.VK_DOWN)){
					selectedMenuState = MenuStates.ANLEITUNG;
				}else if(input.isKeyUp(KeyEvent.VK_UP)){
					selectedMenuState = MenuStates.BEENDEN;
				}else if(input.isKeyUp(KeyEvent.VK_ENTER)){
					GameStateManager.getInstance().setState("MENU_NEW_GAME");
					_chosen = true;
					
				}
			}

			
		}else if(selectedMenuState == MenuStates.ANLEITUNG){
			_bmf.drawString(g,"New Game", screen.getWidth()/2, screen.getHeight()/2+25);
			_bmf2.drawString(g,"Anleitung", screen.getWidth()/2, screen.getHeight()/2+50);
			_bmf.drawString(g,"Highscore", screen.getWidth()/2, screen.getHeight()/2+75);
			_bmf.drawString(g,"Credits", screen.getWidth()/2, screen.getHeight()/2+100);
			_bmf.drawString(g,"Beenden", screen.getWidth()/2, screen.getHeight()/2+125);
			
			if(!_chosen){
				if(input.isKeyUp(KeyEvent.VK_DOWN)){
					selectedMenuState = MenuStates.HIGHSCORE;
				}else if(input.isKeyUp(KeyEvent.VK_UP)){
						selectedMenuState = MenuStates.NEW_GAME;
				}else if(input.isKeyUp(KeyEvent.VK_ENTER)){
					GameStateManager.getInstance().setState("MENU_ANLEITUNG");
					_chosen = true;
					
				}
			}
			
			
		}else if(selectedMenuState == MenuStates.HIGHSCORE){
			_bmf.drawString(g,"New Game", screen.getWidth()/2, screen.getHeight()/2+25);
			_bmf.drawString(g,"Anleitung", screen.getWidth()/2, screen.getHeight()/2+50);
			_bmf2.drawString(g,"Highscore", screen.getWidth()/2, screen.getHeight()/2+75);
			_bmf.drawString(g,"Credits", screen.getWidth()/2, screen.getHeight()/2+100);
			_bmf.drawString(g,"Beenden", screen.getWidth()/2, screen.getHeight()/2+125);
			
			if(!_chosen){
				if(input.isKeyUp(KeyEvent.VK_DOWN)){
					selectedMenuState = MenuStates.CREDITS;
				}else if(input.isKeyUp(KeyEvent.VK_UP)){
						selectedMenuState = MenuStates.ANLEITUNG;
				}else if(input.isKeyUp(KeyEvent.VK_ENTER)){
					GameStateManager.getInstance().setState("MENU_HIGHSCORE");
					_chosen = true;
					
				}
			}

			
		}else if(selectedMenuState == MenuStates.CREDITS){
			_bmf.drawString(g,"New Game", screen.getWidth()/2, screen.getHeight()/2+25);
			_bmf.drawString(g,"Anleitung", screen.getWidth()/2, screen.getHeight()/2+50);
			_bmf.drawString(g,"Highscore", screen.getWidth()/2, screen.getHeight()/2+75);
			_bmf2.drawString(g,"Credits", screen.getWidth()/2, screen.getHeight()/2+100);
			_bmf.drawString(g,"Beenden", screen.getWidth()/2, screen.getHeight()/2+125);
			
			if(!_chosen){
				if(input.isKeyUp(KeyEvent.VK_DOWN)){
					selectedMenuState = MenuStates.BEENDEN;
				}else if(input.isKeyUp(KeyEvent.VK_UP)){
						selectedMenuState = MenuStates.HIGHSCORE;
				}else if(input.isKeyUp(KeyEvent.VK_ENTER)){
					GameStateManager.getInstance().setState("MENU_CREDITS");
					_chosen = true;
					
				}	
			}

			
		}else if(selectedMenuState == MenuStates.BEENDEN){
			_bmf.drawString(g,"New Game", screen.getWidth()/2, screen.getHeight()/2+25);
			_bmf.drawString(g,"Anleitung", screen.getWidth()/2, screen.getHeight()/2+50);
			_bmf.drawString(g,"Highscore", screen.getWidth()/2, screen.getHeight()/2+75);
			_bmf.drawString(g,"Credits", screen.getWidth()/2, screen.getHeight()/2+100);
			_bmf2.drawString(g,"Beenden", screen.getWidth()/2, screen.getHeight()/2+125);
			
			if(!_chosen){
				if(input.isKeyUp(KeyEvent.VK_DOWN)){
					selectedMenuState = MenuStates.NEW_GAME;
				}else if(input.isKeyUp(KeyEvent.VK_UP)){
						selectedMenuState = MenuStates.CREDITS;
				}else if(input.isKeyUp(KeyEvent.VK_ENTER)){
					GameStateManager.getInstance().setState("MENU_BEENDEN");
					_chosen = true;
					
				}
			}

		}
		_bmf.drawString(g,"fh hannover productions - 2015 from ekkerhardt group", screen.getWidth()/2, screen.getHeight()-16);
		
	}
	

}
