package character;

import java.awt.Graphics2D;

import sprite.Animator;
import sprite.Sprite;
import sprite.Transform;

public class AmmoObject extends GameObject{

	private Sprite _sprite;
	private final int bulletAmount = 10;
	
	public AmmoObject(Sprite sprite, double x, double y, int hurtx, int hurty, int hurtscale_x,
			int hurtscale_y) {
		super(sprite, hurtx, hurty, hurtscale_x, hurtscale_y);
		_sprite = sprite;
		move(x, y);
		setZ(getZ()-100.0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isHit(AttackAbleObject dyn) {
		if(dyn instanceof Player){
			if(dyn.hasHurtBoxCollide(this)){
				((Player) dyn).setBullets(((Player) dyn).getBullets()+bulletAmount);
				if(((Player) dyn).getBullets()>= ((Player) dyn).getMaxBullets()){
					((Player) dyn).setBullets(((Player) dyn).getMaxBullets());
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void update(Graphics2D g, double delta) {
		// TODO Auto-generated method stub
		_sprite.show(g);
		move(0,0);
	}


}
