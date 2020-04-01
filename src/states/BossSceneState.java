package states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import managers.GameStateManager;
import sprite.Sprite;
import utils.BitmapFont;
import utils.SoundEffect;

public class BossSceneState extends State{
	private Sprite _background;
	private Sprite _talk1;
	private Sprite _talk2;
	private Sprite _talk3;
	
	SoundEffect haSound = new SoundEffect("BOSS_LAUGH");
	private int _talkNmb = 0;
	BitmapFont _bmf;
	BitmapFont _bmf2;
	
	@Override
	public void init(){ 
		_background = new Sprite("res/intro/back_intro_death.png");
		_talk1 = new Sprite("res/intro/death_talk_01.png");
		_talk1.flip();
		_talk1.scale(1.3, 1.3);
		_background.scale(2, 2);
		_background.translate(160,120);
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,true);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,true);
		_talkNmb = 0;
		_talk1.translate(70,320);
		haSound.play();
	}
	
	@Override
	public void update(Graphics2D g,double delta) {
		_background.show(g);
		switch(_talkNmb){
			case 0:
				_talk1.show(g);
				_bmf2.drawString(g, "Unbekannter Toter Typ", 100, 370);
				_bmf.drawString(g, "Ha ha ha ha ha ha ha", 70, 400);
				break;
			case 1:
				_talk1.show(g);
				_bmf2.drawString(g, "Unbekannter Toter Typ", 100, 370);
				_bmf.drawString(g, "Ich toete euch ihr doofen Typen", 70, 400);
				_bmf.drawString(g, "Ich bin der staerkste", 70, 420);
				break;
			case 2:
				GameStateManager.getInstance().setState("LEVEL_5");
				break;
		}
		
		if(input.isKeyUp(KeyEvent.VK_ENTER)){
			_talkNmb++;
		}
		
	}

}
