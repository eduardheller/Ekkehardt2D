package character;

import java.awt.Graphics2D;

import sprite.Animator;
import sprite.Sprite;
import sprite.Transform;

public class HealthObject extends GameObject{

	private Animator _sprite;
	private final int healAmount = 10;
	
	public HealthObject(Animator sprite, double x, double y, int hurtx, int hurty, int hurtscale_x,
			int hurtscale_y) {
		super(sprite, hurtx, hurty, hurtscale_x, hurtscale_y);
		_sprite = sprite;
		move(x, y);
		setZ(getZ()-100);
		
		_sprite.setAnimation("idle");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isHit(AttackAbleObject dyn) {
		if(dyn instanceof Player){
			if(dyn.hasHurtBoxCollide(this)){
				((Player) dyn).setHp(((Player) dyn).getHp()+healAmount);
				
				if( ((Player) dyn).getHp() >=   ((Player) dyn).getMaxHp()){
					((Player) dyn).setHp(((Player) dyn).getMaxHp());
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
