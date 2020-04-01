package enemies;

import java.util.Random;

import character.Enemy;

public class zBoss {
	
	public void create(Enemy enemy){
		enemy.setHp(300);
		enemy.getTransform().setOrigin(95, 140);
		enemy.setCollider(88, 38, 18, 88);
		enemy.setHitBoxOffsets(93, 54, 50, 11);
		enemy.getTransform().scale(2,2);
		Random r = new Random();
		int dist = 90+r.nextInt(110-90+1);
		enemy.setAttackDst(dist);
		enemy.setAttackDelay(1000,4000);
		enemy.setzBoss();
		
	}
	
}
