package enemies;

import character.BossController;
import character.Enemy;

public class BossEnemy {
	
	public void create(BossController enemy){
		enemy.setCollider(35, 43, 55, 88);
		enemy.setHitBoxOffsets(75, 43, 28, 88);
		enemy.getTransform().setOrigin(89, 180);
		enemy.getTransform().scale(1.5,1.5);
		enemy.getTransform().flip();
	}
}
