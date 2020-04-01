package states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import managers.GameStateManager;
import sprite.Sprite;
import utils.BitmapFont;
import utils.SoundEffect;
import world.World;

public class Level5 extends State{
	
	private World _world;
	
	private Sprite _background;
	private Sprite _talk1;
	private Sprite _talk2;
	private Sprite _talk3;
	
	SoundEffect haSound = new SoundEffect("BOSS_LAUGH");
	private int _talkNmb = 0;
	BitmapFont _bmf;
	BitmapFont _bmf2;
	



	

	@Override
	public void init() {

		_world = new World(5);
		_talk1 = new Sprite("res/intro/death_talk_01.png");
		_talk1.flip();
		_talk1.scale(1.3, 1.3);
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,true);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,true);
		_talkNmb = 0;
		_talk1.translate(70,320);
		haSound.play();
	}

	@Override
	public void update(Graphics2D g, double delta) {
		Graphics2D g2 = (Graphics2D) g;
		_world.update(g,delta);
		
	}

}
