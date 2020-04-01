package states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import managers.GameStateManager;
import sprite.Sprite;
import utils.BitmapFont;
import utils.SoundEffect;

public class OutroState extends State{
	
	private Sprite _background;
	
	BitmapFont _bmf;
	BitmapFont _bmf2;
	
	@Override
	public void init(){ 
		_background = new Sprite("res/intro/3b.png");
		_background.scale(2, 2);
		_background.translate(160,120);
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,true);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,true);
	}
	
	@Override
	public void update(Graphics2D g,double delta) {
		_background.show(g);
		_bmf.drawString(g, "Und so gewannen die Ekkerhardts den unbekannten toten Typen", 70, 400);
		_bmf.drawString(g, "Zur Feier des Tage faerbte sich Jasmin die Haare", 70, 420);
		if(input.isKeyUp(KeyEvent.VK_ENTER)){
			GameStateManager.getInstance().setState("RESULT");
		}
		
	}

}
