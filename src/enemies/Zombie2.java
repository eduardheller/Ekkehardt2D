package enemies;

import java.util.Random;

import character.Enemy;
import character.Hounds;

public class Zombie2 {
	
	public void create(Enemy enemy){
		enemy.setHp(100);
		enemy.setCollider(88, 38, 18, 88);
		enemy.setHitBoxOffsets(93, 54, 50, 11);
		enemy.getTransform().setOrigin(97, 110);
		enemy.getTransform().scale(2,2);
		Random r = new Random();
		int dist = 50+r.nextInt(70-50+1);
		enemy.setAttackDst(dist);
		enemy.setAttackDelay(1000,4000);
	}
	
}
