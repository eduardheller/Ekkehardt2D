package enemies;

import java.util.Random;

import character.Enemy;
import character.Hounds;

public class Zombie1 {
	
	public void create(Enemy enemy){
		enemy.setHp(50);
		enemy.getTransform().setOrigin(51, 81);
		enemy.getTransform().scale(2,2);
		enemy.setHitBoxOffsets(45,30,50,11);
		Random r = new Random();
		int dist = 50+r.nextInt(70-50+1);
		enemy.setAttackDst(dist);
		enemy.setAttackDelay(1000,4000);
	}
	
}
