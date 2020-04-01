package world;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import config.Config;
import managers.ScreenManager;
import sprite.Sprite;
import utils.BitmapFont;
import utils.Score;
import character.Player;
import character.PlayerController;

public class UserInterface {

	public DisplayMode screen = ScreenManager.device.getDisplayMode();
	
	private ArrayList<Sprite> _healthList;
	private ArrayList<Sprite> _ammo;
	private ArrayList<Sprite> _life;
	private int player2Index = 6;
	
	private ArrayList<PlayerController> _player;
	BitmapFont _bmf;
	BitmapFont _bmf2;
	
	public UserInterface(ArrayList<PlayerController> player){
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont3.gif"),13,17,false);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont.gif"),9,9,true);		
		_player = player;
		_healthList = new ArrayList<Sprite>();
		_ammo = new ArrayList<Sprite>();
		_life = new ArrayList<Sprite>();
		for(int i=0;i<player.size();i++){
			if(player.get(i).getPlayerName()==Config.PLAYER_CHRIS){
				_healthList.add(new Sprite("res/ui/chris_00.png"));
				_healthList.add(new Sprite("res/ui/chris_20.png"));
				_healthList.add(new Sprite("res/ui/chris_40.png"));
				_healthList.add(new Sprite("res/ui/chris_60.png"));
				_healthList.add(new Sprite("res/ui/chris_80.png"));
				_healthList.add(new Sprite("res/ui/chris.png"));	
			}else if(player.get(i).getPlayerName()==Config.PLAYER_JACK){
				_healthList.add(new Sprite("res/ui/jack_00.png"));
				_healthList.add(new Sprite("res/ui/jack_20.png"));
				_healthList.add(new Sprite("res/ui/jack_40.png"));
				_healthList.add(new Sprite("res/ui/jack_60.png"));
				_healthList.add(new Sprite("res/ui/jack_80.png"));
				_healthList.add(new Sprite("res/ui/jack.png"));
			}else if(player.get(i).getPlayerName()==Config.PLAYER_JASMIN){
				_healthList.add(new Sprite("res/ui/jasmin_00.png"));
				_healthList.add(new Sprite("res/ui/jasmin_20.png"));
				_healthList.add(new Sprite("res/ui/jasmin_40.png"));
				_healthList.add(new Sprite("res/ui/jasmin_60.png"));
				_healthList.add(new Sprite("res/ui/jasmin_80.png"));
				_healthList.add(new Sprite("res/ui/jasmin.png"));
			}
			_ammo.add(new Sprite ("res/misc/ammo.png"));
			_life.add(new Sprite ("res/ui/herz.png"));
			
			for (int j = i; j<_ammo.size(); j++){
				
				_ammo.get(j).translate(70+i*420, 55);
				_life.get(j).translate(20+i*420, 50);
			}
			for(int j=i*6;j<_healthList.size();j++){
				_healthList.get(j).scale(2, 2);
				_healthList.get(j).translate(65+i*420,25);
			}
		}

		
	}
	
	public void update(Graphics2D g,double delta){
		g.setColor(Color.WHITE);
		for(int i = 0; i<_player.size(); i++){
			drawHealth(g,delta,_player.get(i),i);
			drawAmmo(g,delta,_player.get(i),i);
			drawLife(g,delta,_player.get(i),i);
			_bmf2.drawString(g,"P"+i+" "+_player.get(i).getPlayerName(), 60+i*420,20);
		}
		_bmf.drawString(g,"Score: "+Score.getScore(), screen.getWidth()/2, 20);
	
		
	}

	private void drawAmmo(Graphics2D g, double delta, PlayerController playerController, int i) {
		_ammo.get(0+i*1).show(g);
		_bmf2.drawString(g,""+_player.get(i).getBullets(), 110+i*420,60);
	}
	
	private void drawLife(Graphics2D g, double delta, PlayerController playerController, int i) {
		_life.get(0+i*1).show(g);
		_bmf2.drawString(g,""+_player.get(i).getLife(), 55+i*420,60);
	}

	private void drawHealth(Graphics2D g, double delta, PlayerController player,int i) {
		if(player.getHp()+i*player2Index<0) {
			_healthList.get(0+i*player2Index).show(g);
		}else{
			_healthList.get(player.getHp()+i*player2Index).show(g);
		}
		
	}
	
}
