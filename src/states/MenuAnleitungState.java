package states;

import java.awt.Color;
import java.awt.Graphics2D;

import sprite.Sprite;
import utils.BitmapFont;
import managers.GameStateManager;

import java.awt.event.*;

public class MenuAnleitungState extends State{

	BitmapFont _bmf;
	BitmapFont _bmf2;

	private boolean _chosen;
	@Override
	public void init() {
		// TODO Auto-generated method stub
			_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,false);
			_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,false);
			_chosen = false;
	}

	@Override
	public void update(Graphics2D g, double delta) {
		g.setColor(Color.white);
		_bmf.drawString(g,"Steuerung", screen.getWidth()/6, screen.getHeight()/6);
		_bmf.drawString(g,"Schritt nach Rechts", screen.getWidth()/6, screen.getHeight()/6+50);
		_bmf.drawString(g,"Schritt nach Links", screen.getWidth()/6, screen.getHeight()/6+75);
		_bmf.drawString(g,"Schritt nach Oben", screen.getWidth()/6, screen.getHeight()/6+100);
		_bmf.drawString(g,"Schritt nach Unten", screen.getWidth()/6, screen.getHeight()/6+125);
		_bmf.drawString(g,"Springen", screen.getWidth()/6, screen.getHeight()/6+150);
		_bmf.drawString(g,"Schlagen", screen.getWidth()/6, screen.getHeight()/6+175);
		_bmf.drawString(g,"Ability 1", screen.getWidth()/6, screen.getHeight()/6+200);
		_bmf.drawString(g,"Ability 2", screen.getWidth()/6, screen.getHeight()/6+225);
		_bmf.drawString(g,"Fernkampf", screen.getWidth()/6, screen.getHeight()/6+250);
		
		_bmf2.drawString(g,"Back - Backspace", screen.getWidth()/6, screen.getHeight()/6+300);
		
		
		_bmf.drawString(g,"Spieler 1", screen.getWidth()/2, screen.getHeight()/6);
		_bmf.drawString(g,"D", screen.getWidth()/2, screen.getHeight()/6+50);
		_bmf.drawString(g,"A", screen.getWidth()/2, screen.getHeight()/6+75);
		_bmf.drawString(g,"W", screen.getWidth()/2, screen.getHeight()/6+100);
		_bmf.drawString(g,"S", screen.getWidth()/2, screen.getHeight()/6+125);
		_bmf.drawString(g,"Leertaste", screen.getWidth()/2, screen.getHeight()/6+150);
		_bmf.drawString(g,"F", screen.getWidth()/2, screen.getHeight()/6+175);
		_bmf.drawString(g,"G", screen.getWidth()/2, screen.getHeight()/6+200);
		_bmf.drawString(g,"H", screen.getWidth()/2, screen.getHeight()/6+225);
		_bmf.drawString(g,"T", screen.getWidth()/2, screen.getHeight()/6+250);
		
		_bmf.drawString(g,"Spieler 2", screen.getWidth()-150, screen.getHeight()/6);
		_bmf.drawString(g,"Pfeiltaste Rechts", screen.getWidth()-150, screen.getHeight()/6+50);
		_bmf.drawString(g,"Pfeiltaste Links", screen.getWidth()-150, screen.getHeight()/6+75);
		_bmf.drawString(g,"Pfeiltaste Oben", screen.getWidth()-150, screen.getHeight()/6+100);
		_bmf.drawString(g,"Pfeiltaste Unten", screen.getWidth()-150, screen.getHeight()/6+125);
		_bmf.drawString(g,"Num 0", screen.getWidth()-150, screen.getHeight()/6+150);
		_bmf.drawString(g,"Num 4", screen.getWidth()-150, screen.getHeight()/6+175);
		_bmf.drawString(g,"Num 5", screen.getWidth()-150, screen.getHeight()/6+200);
		_bmf.drawString(g,"Num 6", screen.getWidth()-150, screen.getHeight()/6+225);
		_bmf.drawString(g,"Num 8", screen.getWidth()-150, screen.getHeight()/6+250);
		
		
		if(!_chosen){
			if(input.isKeyDown(KeyEvent.VK_BACK_SPACE)){
				_chosen = true;
				GameStateManager.getInstance().setState("MENU");
				
			}
		}

	}
}
