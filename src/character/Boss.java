package character;

import java.awt.Graphics2D;


import java.awt.Graphics2D;
import java.util.ArrayList;

import config.Config;
import sprite.Animator;
import world.Background;


public class Boss extends ActorObject{
	
	private ArrayList<Projectile> _projectiles;
	
		
	// Hier Attackvariablen entsprechend ändern
	private final int _comboDelay = 200; 
	private final int _hitDelay = 320; // Wie lange ein Schlag dauert
	private final int _hitBoxDelay = 100; // Desto niedriger desto später kommt der Attack an einem Entity
	private final int _hitRangedDelay = 200;
	
	// Projektile Variablen
	private final int _projectileXOffset = -30;
	private final int _projectileYOffset = -220;
	private final int _projectileHitWidth = 12;
	private final int _projectileHitHeight = 12;
	private double _projectileSpeed = 6;
	private int _projectileStrength = 1;
	private boolean _dead = false;
		
	// ******************************************************************************


	private boolean _attack = false;
	private boolean _rdyToAttack = true;
	private int _currentDmgIndex = 0;
	private boolean _hasAmmo = false;
	private boolean _hasHeal = false;
	
	private boolean _rangedAttack = false;

	private int _hitDelayTimer = _hitDelay;
	private int _hitRangedDelayTimer = _hitRangedDelay;

	private final int _score = 10;

	
	public Boss(String animName){
		super(new Animator(animName),
				50,1, // HP und Strength
				78,54,18,88, // HurtBox
				60,60,50,11); // HitBox
		_projectiles = new ArrayList<Projectile>();
		setMaxHp(50);
		getAnimator().setAnimation("idle");
	}
	
	@Override
	public void update(Graphics2D g, double delta){
		
		super.update(g, delta);
		_handleRangedAttack(delta);

		if(isDead()){
			if(!_dead){
				getAnimator().setAnimation("die");
				_dead = true;
			}
			if(getAnimator().getMaxFrame()==getAnimator().getCurrentFrame()){
				getAnimator().setAnimation("dead");
			}
		}else{
			if(isBlocked()){
				getAnimator().setAnimation("block");
			}
		}
	}
	
	@Override
	public void move(double x, double y){
		if(!_dead){
			if(!_attack && !_rangedAttack && !isBlocked()){
				setZ(getZ()+(int)Math.round(y));
				
				getTransform().translate(x-Background.getMoveX(),y);
				if(!getAnimator().isPlaying("walk") && !getAnimator().isPlaying("attk")
						&& !getAnimator().isPlaying("attkShoot")){
					
					getAnimator().setAnimation("walk");
				}
			}
		}
		

	}
	
	@Override
	void idle() {
		if(!_dead){
			if(!getAnimator().isPlaying("idle") && !getAnimator().isPlaying("attk")
					&& !getAnimator().isPlaying("attkShoot") && !getAnimator().isPlaying("die")){
				
				getAnimator().setAnimation("idle");
			}
		}
	}
	
	@Override
	void handleAttack(double delta){
		if(!_dead){
			if(_attack){
				_hitDelayTimer -= delta*Config.TIMER_DIF;
				if(_hitDelayTimer<=_hitBoxDelay){
					setHitting(true);
				}
				if(_hitDelayTimer<=0){
					_rdyToAttack = true;
					_attack = false;
					_hitDelayTimer = _hitDelay;
					if(!getAnimator().isPlaying("jump")){
						getAnimator().setAnimation("idle");
					}

				}
			}
		}

	}
	
	
	public void attack(){
		if(!_dead){
			if(_rdyToAttack){
				getAnimator().setAnimation("attk");
				_attack = true;
				_rdyToAttack = false;
				
				_hitDelayTimer = _hitDelay;
			}
		}
	}
	
	public void rangedAttack(){
		if(!_dead){
			if(_rdyToAttack){
				_rangedAttack = true;
				_rdyToAttack = false;
				_hitDelayTimer = _hitDelay;
				
			}
		}
	}
	
	private void _handleRangedAttack(double delta){
		if(!_dead){
			if(_rangedAttack){
				_hitRangedDelayTimer -= delta*Config.TIMER_DIF;
		
				if(_hitRangedDelayTimer<=0){
					_projectiles.add(new Projectile(new Animator("proj"),this,_projectileStrength,_projectileSpeed,
													_projectileHitWidth,_projectileHitHeight,_projectileXOffset,_projectileYOffset));
					_rdyToAttack = true;
					_rangedAttack = false;
					_hitRangedDelayTimer = _hitRangedDelay;

				}
			}		
		}
	
	}
	public boolean isAttacking(){
		return _attack || _rangedAttack;
	}
	
	public ArrayList<Projectile> getProjectiles(){
		return _projectiles;
	}
	
	public void setAmmoItem(){
		_hasAmmo = true;
	}
	
	public boolean hasAmmo(){
		return _hasAmmo;
	}
	
	public void setHealItem(){
		_hasHeal = true;
	}
	
	public boolean hasHeal(){
		return _hasHeal;
	}

	public int getScore(){
		return _score;
	}
}
