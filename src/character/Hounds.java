package character;

import java.awt.Graphics2D;
import java.util.ArrayList;

import config.Config;
import sprite.Animator;


public class Hounds extends ActorObject{
	
	// Hier Attackvariablen entsprechend ändern
	private final int _hitDelay = 1000; // Wie lange ein Schlag dauert
	private final int _hitRangedDelay = 900;
	
	// Projektile Variablen
	private boolean _dead = false;
		
	// ******************************************************************************


	private boolean _attack = false;
	private boolean _rdyToAttack = true;


	private int _hitDelayTimer = _hitDelay;
	
	private boolean right ;
	
	public Hounds(String animName){
		super(new Animator(animName),
				1,2, // HP und Strength
				0,0,0,0, // HurtBox
				60,80,60,40); // HitBox
		setMaxHp(50);

		getAnimator().setAnimation("walk");
		setHitting(true);

	}
	
	@Override
	public void update(Graphics2D g, double delta){
		getAnimator().show(g);
		
		if(!isDead()){
			if(isBlocked()){
				blockEffect(delta);
			}
			handleAttack(delta);
		}

		move(-4*delta,0);
		
		
	}
	
	@Override
	public void move(double x, double y){
		super.move(x,y);
	}

	@Override
	void handleAttack(double delta) {
		// TODO Auto-generated method stub
		if(!_dead){
			if(_attack){
				_hitDelayTimer -= delta*Config.TIMER_DIF;
				
				setHitting(false);
				
				if(_hitDelayTimer<=0){
					_rdyToAttack = true;
					_attack = false;
					_hitDelayTimer = _hitDelay;
					setHitting(true);
				}
			}
		}
	}

	@Override
	void attack() {
		if(!_dead){
			if(_rdyToAttack){
				_attack = true;
				_rdyToAttack = false;
				
				_hitDelayTimer = _hitDelay;
			}
		}
	}

	@Override
	void idle() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isHit(AttackAbleObject dyn){
		if(hasHitBoxCollide(dyn)){
			attack();
			return true;
		}
		return false;
	}


}
