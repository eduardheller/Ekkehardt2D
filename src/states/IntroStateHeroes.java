package states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import managers.GameStateManager;
import sprite.Sprite;
import utils.BitmapFont;
import utils.SoundEffect;

public class IntroStateHeroes extends State{

	private Sprite _background;
	private Sprite _chrisTalk1;
	private Sprite _chrisTalk2;
	private Sprite _chrisTalk3;
	private Sprite _chrisTalk4;
	private Sprite _jackTalk1;
	private Sprite _jackTalk2;
	private Sprite _jasminTalk1;
	private Sprite _jasminTalk2;
	BitmapFont _bmf;
	BitmapFont _bmf2;
	private int _talkNmb = 0;
	

	
	@Override
	public void init(){ 

		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,true);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,true);
		_background = new Sprite("res/intro/back_intro_heroes.png");
		_chrisTalk1 = new Sprite("res/intro/chris_talk_01.png");
		_jackTalk1 = new Sprite("res/intro/jack_talk_01.png");
		_jasminTalk1 = new Sprite("res/intro/jasmin_talk_01.png");
		_background.scale(2, 2);
		_background.translate(160,120);
		_talkNmb = 0;
		_chrisTalk1.scale(1.3, 1.3);
		_jackTalk1.scale(1.3, 1.3);
		_jasminTalk1.scale(1.3, 1.3);

		_chrisTalk1.translate(70,320);
		_jackTalk1.translate(70,320);
		_jasminTalk1.translate(70,320);
	}
	
	@Override
	public void update(Graphics2D g,double delta) {
		_background.show(g);
		switch(_talkNmb){
			case 0:
				_chrisTalk1.show(g);
				_bmf2.drawString(g, "Chris", 100, 370);
				_bmf.drawString(g, "Irgendetwas stinkt hier", 70, 400);
				break;
			case 1:
				_jackTalk1.show(g);
				_bmf2.drawString(g, "Jack", 100, 370);
				_bmf.drawString(g, "Entschuldigung", 70, 400);
				break;
			case 2: 
				_chrisTalk1.show(g);
				_bmf2.drawString(g, "Chris", 100, 370);
				_bmf.drawString(g, "Nein, ich spuere etwas", 70, 400);
				_bmf.drawString(g, "irgendwas stinkt hier gewaltig", 70, 420);
				break;
			case 3:
				_jasminTalk1.show(g);
				_bmf2.drawString(g, "Jasmin", 100, 370);
				_bmf.drawString(g, "Sorry das war ich", 70, 400);
				break;
			case 4:
				_chrisTalk1.show(g);
				_bmf2.drawString(g, "Chris", 100, 370);
				_bmf.drawString(g, "Verdammt spuert ihr das nicht", 70, 400);
				_bmf.drawString(g, "Es kommt eine Bedrohung auf uns zu", 70, 420);
				break;
			case 5:
				_jackTalk1.show(g);
				_bmf2.drawString(g, "Jack", 100, 370);
				_bmf.drawString(g, "Wie sollen wir so etwas spueren", 70, 400);
				break;
			case 6:
				_chrisTalk1.show(g);
				_bmf2.drawString(g, "Chris", 100, 370);
				_bmf.drawString(g, "Wir sind Helden", 70, 400);
				_bmf.drawString(g, "Wir spueren so etwas", 70, 420);
				_bmf.drawString(g, "Wir muessen die Welt retten", 70, 440);
				break;
			case 7:
				
				_jasminTalk1.show(g);
				_bmf.drawString(g, "Aber vorher gehe ich noch", 70, 400);
				_bmf.drawString(g, "auf die Toilette", 70, 420);
				break;
			case 8:
				GameStateManager.getInstance().setState("LEVEL_1");
				break;
		}
		
		if(input.isKeyUp(KeyEvent.VK_ENTER)){
			_talkNmb++;
		}
		
	}


}
