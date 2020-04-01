package states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import managers.GameStateManager;
import sprite.Sprite;
import utils.BitmapFont;

public class EndLevelSceneState extends State{
	private Sprite _background;
	
	BitmapFont _bmf;
	BitmapFont _bmf2;
	
	@Override
	public void init(){ 
		_background = new Sprite("res/intro/1b.png");
		_background.scale(2, 2);
		_background.translate(160,120);
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,true);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,true);
	}
	
	@Override
	public void update(Graphics2D g,double delta) {
		_background.show(g);
		_bmf.drawString(g, "Nur noch das letzte Level", 70, 400);
		_bmf.drawString(g, "Busen ist groeser als Doener", 70, 420);
		if(input.isKeyUp(KeyEvent.VK_ENTER)){
			GameStateManager.getInstance().setState("LEVEL_4");
		}
		
	}
}
