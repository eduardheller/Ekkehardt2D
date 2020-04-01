package states;

import java.awt.Color;
import java.awt.Graphics2D;

import sprite.Sprite;
import utils.BitmapFont;
import managers.GameStateManager;

import java.awt.event.*;

public class MenuCreditsState extends State{
	
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
		_bmf.drawString(g,"Gameprogramming:", screen.getWidth()/2, screen.getHeight()/6);
		_bmf.drawString(g,"Eduard Heller", screen.getWidth()/2, screen.getHeight()/6+50);
		_bmf.drawString(g,"Jannes Joern", screen.getWidth()/2, screen.getHeight()/6+75);
		_bmf.drawString(g,"Simon Klossok", screen.getWidth()/2, screen.getHeight()/6+100);
		_bmf.drawString(g,"Lars Lampe", screen.getWidth()/2, screen.getHeight()/6+125);
		_bmf.drawString(g,"Fabian Moenkemoeller", screen.getWidth()/2, screen.getHeight()/6+150);
		_bmf.drawString(g,"Kristoff Raveling", screen.getWidth()/2, screen.getHeight()/6+175);
		_bmf2.drawString(g,"Back - Backspace", screen.getWidth()/2, screen.getHeight()/6+225);
		
		if(!_chosen){
			if(input.isKeyDown(KeyEvent.VK_BACK_SPACE)){
				_chosen = true;
				GameStateManager.getInstance().setState("MENU");
				
			}
		}

	}
}
