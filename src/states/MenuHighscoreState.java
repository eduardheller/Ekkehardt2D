package states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import sprite.Sprite;
import utils.BitmapFont;
import utils.SoundEffect;
import managers.GameStateManager;

import java.awt.event.*;

public class MenuHighscoreState extends State {
	
	TreeMap<Integer, ArrayList<String>> tm = new TreeMap<Integer, ArrayList<String>>();
	
	BitmapFont _bmf;
	BitmapFont _bmf2;
	
	Sprite _spr;
	private boolean _chosen;
	
	SoundEffect highscoreSound = new SoundEffect("GAMEOVER");
	@Override
	public void init() {
		
		highscoreSound.play();
		_bmf = new BitmapFont(new Sprite("res/ui/BitmapFont3.gif"),13,17,false);
		_bmf2 = new BitmapFont(new Sprite("res/ui/BitmapFont2.gif"),9,9,false);
		_chosen = false;
		_spr = new Sprite("res/menu/hiscore.gif");
		_spr.scale(2, 2);
		_spr.translate(160,120);
		tm.clear();
		int tempInt, size=0;
		String tempString;
		File highscore = new File("src/res/highscore");
		Scanner myScanner = null;
		
		try {
			myScanner = new Scanner(highscore);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(myScanner.hasNext()){
			tempInt = myScanner.nextInt();
			tempString = myScanner.next();
			if(!tm.containsKey(tempInt)){
				ArrayList<String> al = new ArrayList<String>();
				al.add(tempString);
				tm.put(tempInt, al);
				size++;
			}else{
				ArrayList<String> al = tm.get(tempInt);
				al.add(tempString);
				tm.put(tempInt, al);
				size++;
			}
		}
		while(size>10){			
			Entry<Integer, ArrayList<String>> temp = tm.pollFirstEntry();
			if(size - temp.getValue().size()<10){
				int diff = 10-(size-temp.getValue().size());
				ArrayList<String> tempList = new ArrayList<String>();
				for(int i =diff-1;i>=0;i--){
					tempList.add(temp.getValue().get(i));
				}
				tm.put(temp.getKey(), tempList);
				size = 10;
			}else
				size = size - temp.getValue().size();		
		}
	}

	@Override
	public void update(Graphics2D g, double delta) {
		g.setColor(Color.white);
		_spr.show(g);
		_bmf.drawString(g,"Highscore", screen.getWidth()/2, 100);
		int height = -150;
		for(Integer key: tm.keySet()){
			if(tm.get(key).size()>1){
				for(String name:tm.get(key)){
					_bmf.drawString(g,key.toString(), screen.getWidth()/2-50, screen.getHeight()/2-height);
					_bmf.drawString(g,"["+name.toString()+"]", screen.getWidth()/2+50, screen.getHeight()/2-height);
					height = height + 25;
				}
			}else{
				_bmf.drawString(g,key.toString(), screen.getWidth()/2-50, screen.getHeight()/2-height);
				_bmf.drawString(g,tm.get(key).toString(), screen.getWidth()/2+50, screen.getHeight()/2-height);
				height = height + 25;
			}
		}
		_bmf.drawString(g,"Back - Backspace", screen.getWidth() / 6,	screen.getHeight() - 25 );
		
		
		if(!_chosen){
			if (input.isKeyDown(KeyEvent.VK_BACK_SPACE)) {
				_chosen = true;
				GameStateManager.getInstance().setState("MENU");
				highscoreSound.stop();
			}
		}

		
		
	}	
}