package enemies;

import java.util.Random;

import config.Config;
import character.Enemy;
import character.Hounds;

public class Hound {
	
	private Hounds _enemy;

	public Hounds create(String name){
		_enemy = new Hounds(name);
		_enemy.getTransform().setOrigin(82, 50);
		_enemy.getTransform().scale(1.4,1.4);
		return _enemy;
	}
	
	public void spawn(int x,int y){
	
		_enemy.move(1200 + x*200,Config.BOUND_TOP+20 + y*50);

	}
	
	public void spawn(double x,double y){
	
		_enemy.move(x,y);
		
		
	}
}
