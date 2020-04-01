package enemies;

import java.util.Random;

import character.Enemy;
import character.Hounds;

public class Ghoul {
	
	public void create(Enemy enemy){
		enemy.setHp(125);
		enemy.setCollider(64, 38, 18, 88);
		enemy.setHitBoxOffsets(69, 54, 50, 11);
		enemy.getTransform().setOrigin(71, 110);
		enemy.getTransform().scale(2,2);
		Random r = new Random();
		int dist = 50+r.nextInt(70-50+1);
		enemy.setAttackDst(dist);
		enemy.setAttackDelay(1000,4000);
		
	}
	
}
