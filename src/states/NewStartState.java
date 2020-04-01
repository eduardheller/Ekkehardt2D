package states;

import java.awt.Color;
import java.awt.Graphics2D;

import managers.GameStateManager;
import sprite.Sprite;
import utils.BitmapFont;
import world.World;

import java.awt.event.*;

public class NewStartState extends State{
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
		_bmf.drawString(g,"Sie haben das Spiel auf Stufe: "+(World.hardnessLevel-1)+" geschafft", screen.getWidth()/2, screen.getHeight()/6);
		_bmf.drawString(g,"Nun kommt ein haerteres Spiel auf Sie zu", screen.getWidth()/2, screen.getHeight()/6+100);
		_bmf2.drawString(g,"Enter fuer einen Neuanfang", screen.getWidth()/2, screen.getHeight()/6+250);
		
		if(!_chosen){
			if(input.isKeyDown(KeyEvent.VK_ENTER)){
				_chosen = true;
				GameStateManager.getInstance().setState("LEVEL_1");
			}
		}

	}
}
