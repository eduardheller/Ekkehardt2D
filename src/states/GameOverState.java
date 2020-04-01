package states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import managers.GameStateManager;
import sprite.Sprite;
import utils.BitmapFont;
import utils.Score;
import utils.SoundEffect;

import java.awt.event.*;

public class GameOverState extends State{
	
	int _score;
	ArrayList<Character> _eingabe;
	String _playerName = "";
	FileWriter pw;
	Sprite _spr;
	BitmapFont _bmf;
	BitmapFont _bmf2;
	BitmapFont _bmf3;
	boolean accepted = false;

	SoundEffect laugh = new SoundEffect("BOSS_LAUGH");
	@Override
	public void init() {
		try {
			pw = new FileWriter("src/res/highscore",true); //the true will append the new data
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_score = Score.getScore();
		_eingabe = new ArrayList<Character>();
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,true);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,true);
		_bmf3 = new BitmapFont(new Sprite("res/ui/BitmapFont3.gif"),13,17,false);
		_spr = new Sprite("res/menu/hiscore.gif");
		_spr.scale(2, 2);
		_spr.translate(160,120);
		
		laugh.play();
	}
	
	@Override
	public void update(Graphics2D g, double delta) {
		_spr.show(g);
		if(!accepted){
			if(input.isKeyUp(KeyEvent.VK_ENTER) && !_eingabe.isEmpty()){
				accepted = true;
				_playerName = _playerName.replace(" ", "_");
				_playerName = _playerName.replace("?", "");
				_playerName = _playerName.toUpperCase();
				try {
					System.out.println(_eingabe.toString());
					pw.write(" " + _score + " " + _playerName);
					pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				GameStateManager.getInstance().setState("MENU_HIGHSCORE");
			}
			if(input.isKeyUp(KeyEvent.VK_BACK_SPACE) && _eingabe.size()> 0){
				_eingabe.remove(_eingabe.size()-1);
			}
			if(input.isAnyKeyUp() && !input.isKeyUp(KeyEvent.VK_BACK_SPACE) &&
					!input.isKeyDown(KeyEvent.VK_SHIFT)&& !input.isKeyUp(KeyEvent.VK_SHIFT) && 
					!input.isKeyUp(KeyEvent.VK_LEFT) && !input.isKeyUp(KeyEvent.VK_RIGHT) && 
					!input.isKeyUp(KeyEvent.VK_UP)&& !input.isKeyUp(KeyEvent.VK_DOWN)){
				_eingabe.add(input.getKeyChar());
				System.out.print(input.getKeyChar());
			}
			
			_playerName = "";
			for(int i = 0;i<_eingabe.size();i++){
				_playerName = _playerName.concat(""+_eingabe.get(i));
			}
			g.setColor(Color.white);
			_bmf3.drawString(g,"game over", screen.getWidth()/2, 50);
			_bmf.drawString(g,"Ihre Punktzahl betraegt: ", 170, 150);
			_bmf2.drawString(g,"" + _score, 390, 150);
			_bmf.drawString(g,"Bitte geben Sie Ihren Namen ein.", 170, 175);
			_bmf.drawString(g,"Es sind nur Kleinbuchstaben erlaubt", 170, 200);
			_bmf.drawString(g,"Spielername:",170,225);
			_bmf2.drawString(g,"[ " + _playerName + " ]", 290, 225);
			_bmf2.drawString(g,"Enter zum bestaetigen", 170, 275);
			
		}
	}
}