package character;

import java.awt.Graphics2D;


import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import config.Config;
import sprite.Animator;
import utils.SoundEffect;
import world.Background;


public class Enemy extends ActorObject{
	
	private ArrayList<Projectile> _projectiles;
	
		
	// Hier Attackvariablen entsprechend ändern
	private final int _comboDelay = 200; 
	private final int _hitDelay = 320; // Wie lange ein Schlag dauert
	private final int _hitBoxDelay = 100; // Desto niedriger desto später kommt der Attack an einem Entity
	private final int _hitRangedDelay = 900;
	
	// Projektile Variablen
	private final int _projectileXOffset = 20;
	private final int _projectileYOffset = 0;
	private final int _projectileHitWidth = 12;
	private final int _projectileHitHeight = 12;
	private double _projectileSpeed = 6;
	private int _projectileStrength = 25;
	private boolean _dead = false;
		
	// ******************************************************************************

	private int _attackDelayMin;
	private int _attackDelayMax;
	private int _attackDst;

	private boolean _attack = false;
	private boolean _rdyToAttack = true;
	private int _currentDmgIndex = 0;
	private boolean _hasAmmo = false;
	private boolean _hasHeal = false;
	
	private boolean _rangedAttack = false;

	private int _hitDelayTimer = _hitDelay;
	private int _hitRangedDelayTimer = _hitRangedDelay;

	private boolean _zBoss;
	
	private final int _score = 10;
	SoundEffect punchSound = new SoundEffect("PUNCH");
	
	public Enemy(String animName){
		super(new Animator(animName),
				50,1, // HP und Strength
				40,14,18,88, // HurtBox
				60,60,50,11); // HitBox
		_projectiles = new ArrayList<Projectile>();
		setMaxHp(50);
		getAnimator().setAnimation("idle");
		_zBoss = false;
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
				setZ(getZ()+y);
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
		if(!_dead && !isBlocked()){
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
		if(!_dead && !isBlocked()){
			if(_rdyToAttack){
				getAnimator().setAnimation("attk");
				_attack = true;
				_rdyToAttack = false;
				punchSound.play();
				_hitDelayTimer = _hitDelay;
			}
		}
	}
	
	public void rangedAttack(){
		if(!_dead && !isBlocked()){
			if(_rdyToAttack){
				getAnimator().setAnimation("attkShoot");
				_rangedAttack = true;
				_rdyToAttack = false;
				_hitDelayTimer = _hitDelay;
				
			}
		}
	}
	
	private void _handleRangedAttack(double delta){
		if(!_dead && !isBlocked()){
			if(_rangedAttack){
				_hitRangedDelayTimer -= delta*Config.TIMER_DIF;
		
				if(_hitRangedDelayTimer<=0){
					_projectiles.add(new Projectile(new Animator("proj"),"",this,_projectileStrength,_projectileSpeed,
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
	
	public void setAttackDst(int dst){
		
		_attackDst = dst;
	
	}
	
	public int getAttackDst(){
		
		return _attackDst;
	}
	public void setAttackDelay(int dlmin, int dlmax){
		
		_attackDelayMin = dlmin;
		_attackDelayMax = dlmax;
	
	}
	
	public int getAttackDelay(){
		Random r = new Random();
		int ret = _attackDelayMin + r.nextInt(_attackDelayMax-_attackDelayMin+1);
		return ret;
	}

	
	public void setzBoss(){
		
		_zBoss = true;
		
	}
	
	public boolean getzBoss(){
		
		return _zBoss;
		
	}
}
