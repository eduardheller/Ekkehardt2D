package character;


import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import config.Config;
import sprite.Animator;
import utils.SoundEffect;


public class Player extends ActorObject{
	
	private ArrayList<Projectile> _projectiles;
	
	PlayerController controller;
	
	// Hier Jumpvariablen entsprechend ändern
	private final int _jumpDelay = 300;
	private final double _jumpHangTime = 20;
	private final double _jumpHeight = 120;
	
	// Hier Attackvariablen entsprechend ändern
	private final int _comboDelay = 200; 
	private final int _hitDelay = 320; // Wie lange ein Schlag dauert
	private final int _hitBoxDelay = 100; // Desto niedriger desto später kommt der Attack an einem Entity
	private final int _hitRangedDelay = 100;
	
	// Projektile Variablen
	private final int _projectileXOffset = 160;
	private int _projectileYOffset = -180;
	private final int _projectileHitWidth = 24;
	private final int _projectileHitHeight = 24;
	private double _projectileSpeed = 6;
	private int _projectileStrength = 25;
		
	// ******************************************************************************

	// Fixe Jumpvariable
	private boolean _isJumping = false;
	private int _jumpDelayTimer = _jumpDelay;
	private boolean _rdyToJump = true;
	private double _halfPI = Math.PI/2;
	private double _jumpSinWaveSpeed = _halfPI / _jumpHangTime;
	private double _jumpSinWavePos = 0.0;
	private double _jumpTranslateSum = 0.0;
	
	private boolean _attack = false;
	private boolean _comboError = false;
	private boolean _ableToCombo = false;
	private boolean _rdyToAttack = true;
	private int _currentDmgIndex = 0;
	
	private boolean _rangedAttack = false;

	private int _bullets = 10;
	private final int _maxBullets = 100;
	
	private int _comboDelayTimer = _comboDelay;
	private int _hitDelayTimer = _hitDelay;
	private int _hitRangedDelayTimer = _hitRangedDelay;
	
	private int _life;
	private boolean _gameOver = false;
	private boolean _currentDead = false;
	
	private final int _godModeDelay = 3000;
	private int _godModeTimer = _godModeDelay;
	private final int _blendDelay= 200;
	private int _blendTimer = _blendDelay;
	private boolean _godModeShow = false;
	private boolean _isForceBlocked = true;
	
	private boolean _isRunning = false;
	
	private String _name;
	
	SoundEffect shootSound = new SoundEffect("SHOOT");
	SoundEffect punchSound = new SoundEffect("PUNCH");
	SoundEffect guyDieSound = new SoundEffect("GUYDIE");
	SoundEffect girlDieSound = new SoundEffect("GIRLDIE");
	SoundEffect jumpSound = new SoundEffect("JUMP");
	SoundEffect goSound1 = new SoundEffect("GO1");
	SoundEffect goSound2 = new SoundEffect("GO2");
	
	public Player(String animName){
		super(new Animator(animName),
				5,25, // HP und Strength
				60,44,18,98, // HurtBox
				65,60,50,11); // HitBox
		_projectiles = new ArrayList<Projectile>();
		setMaxHp(5);
		_name = animName;
		_life = Config.PLAYER_LIFE;
		if(_name=="jasmin"){
			_projectileYOffset = -140;
		}
		getAnimator().setAnimation("idle");
	}
	
	@Override
	public void update(Graphics2D g, double delta){
		
		if(!getAnimator().isPlaying("start") && _isForceBlocked){
			getAnimator().setAnimation("start");
		}else if(getAnimator().isPlaying("start")){
			if(getAnimator().getCurrentFrame()==getAnimator().getMaxFrame()){
				setForceBlock(false);
				goSound1.play();
				goSound2.play();
			}
		}
		
		if(_currentDead){
			_godModeTimer -= delta*Config.TIMER_DIF;
			_blendTimer -= delta*Config.TIMER_DIF;
			if(_blendTimer<=0){
				_godModeShow = !_godModeShow;
				_blendTimer = _blendDelay;
			}
			if(_godModeTimer<=0){
				_currentDead = false;
				_blendTimer = _blendDelay;
				_godModeTimer = _godModeDelay;
			}
			if(_godModeShow){
				getAnimator().show(g);
			}
		}else{
			getAnimator().show(g);
		}
		
		if(!isDead()){
			if(isBlocked()){
				blockEffect(delta);
			}
			setHitting(false); // Ressetet Hitting, da nur nur einmal getroffen werden darf
			handleAttack(delta);
		}
		
		
		_handleJump(delta);
		_handleRangedAttack(delta);
		if(!isDead() && isBlocked() && !getAnimator().isPlaying("block")){
			getAnimator().setAnimation("block");
		}
		if(isDead()){
			if(!getAnimator().isPlaying("dead")){
				if(_name!=Config.PLAYER_JASMIN){
					guyDieSound.play();
				}else{
					girlDieSound.play();
				}
				getAnimator().setAnimation("dead");
			}
			if(getAnimator().getCurrentFrame()==getAnimator().getMaxFrame()){
				if(_life>0){
					
					_currentDead = true;
					setHp(getMaxHp());
					_life--;
					getAnimator().setAnimation("idle");
					setBlocked(false);
				}else{
					_gameOver = true;
				}
				
			}
		}

		
	}
	
	@Override
	public void move(double x, double y){
		if(!_attack && !_rangedAttack && !isBlocked() && !isDead()){
			super.move(x,y);
			if(!getAnimator().isPlaying("walk") && !getAnimator().isPlaying("attk") && !getAnimator().isPlaying("jump")
					&& !getAnimator().isPlaying("attkShoot") && !getAnimator().isPlaying("run")){
				
				getAnimator().setAnimation("walk");
			}
		}
		else if(getAnimator().isPlaying("attkRun")){
			super.move(x,y);
		}

	}
	
	@Override
	void idle() {
		if(!isDead()){
			if(!getAnimator().isPlaying("idle") && !getAnimator().isPlaying("attk") 
					&& !getAnimator().isPlaying("attkKick")
					&& !isBlocked()
					&& !getAnimator().isPlaying("attkKickLast")
					&& !getAnimator().isPlaying("jump") 
					&& !getAnimator().isPlaying("attkShoot")
					&& !getAnimator().isPlaying("run")){
				getAnimator().setAnimation("idle");
			}
		}

	}
	
	@Override
	void handleAttack(double delta){
		if(_attack && !isDead()){
			_hitDelayTimer -= delta*Config.TIMER_DIF;

			if(_hitDelayTimer<=_hitBoxDelay){
				setHitting(true);
			}
			if(_hitDelayTimer<=0){
				_rdyToAttack = true;
				_attack = false;
				_hitDelayTimer = _hitDelay;
				if(_comboError){
					_ableToCombo = false;
					_comboError = false;
				}else{
					_ableToCombo = true;
				}
				if(!getAnimator().isPlaying("jump")){
					getAnimator().setAnimation("idle");
				}

			}
		}
		if(_ableToCombo){
			_comboDelayTimer -= delta*Config.TIMER_DIF;;
			if(_comboDelayTimer<=0){
				_ableToCombo = false;
				_comboDelayTimer = _comboDelay;
			}
		}
	}
	

	public void jump(){
		if(!_attack && !_rangedAttack && !isBlocked() && !isDead()){
			if(_rdyToJump){
				jumpSound.play();
				getAnimator().setAnimation("jump");
				_isJumping = true;
				_rdyToJump = false;
			}
		}

	}
	
	public void attack(){
		if(_rdyToAttack && !_isJumping && !isDead() && !isBlocked()){
			
			if(isRunning()){
				getAnimator().setAnimation("attkRun");
			}else{
				getAnimator().setAnimation("attk");
			}
			
			punchSound.play();
			_attack = true;
			_rdyToAttack = false;
			
			if(_ableToCombo && _currentDmgIndex<2){
				_currentDmgIndex++;
				if(_currentDmgIndex == 1){
					getAnimator().setAnimation("attkKick");
				}else{
					getAnimator().setAnimation("attkKickLast");
				}
				
			}else{
				_currentDmgIndex = 0;
			}
			
			_hitDelayTimer = _hitDelay;
			_comboDelayTimer = _comboDelay;
		}
	}
	
	public boolean isJumping(){
		return _isJumping;
	}	
	
	private void _handleJump(double delta){
		
		if(!_rdyToJump && !_isJumping && !isDead()){
			_jumpDelayTimer -= delta*Config.TIMER_DIF;
			if(_jumpDelayTimer<=0){
				_rdyToJump = true;
				_jumpDelayTimer = _jumpDelay;
			}
		}

		if(_isJumping && !isDead()){
			double lastHeight = _jumpSinWavePos;
			_jumpSinWavePos += _jumpSinWaveSpeed * delta;
			
			if((_jumpSinWavePos >= Math.PI)){
				_jumpSinWavePos = 0;
				_isJumping = false;
				_rdyToJump = false;
				getAnimator().translate(0,_jumpTranslateSum);
				getAnimator().setAnimation("idle");
				_jumpTranslateSum = 0;
				return;
			}
			double translateY = Math.round((Math.sin(_jumpSinWavePos)-Math.sin(lastHeight))*_jumpHeight);
			_jumpTranslateSum += translateY;
			getAnimator().translate(0,-translateY);
		}
		
	}
	
	public void rangedAttack(){
		if(_bullets > 0 && !isDead()){
			if(_rdyToAttack && !_isJumping){
				getAnimator().setAnimation("attkShoot");
				_rangedAttack = true;
				_rdyToAttack = false;
				_hitDelayTimer = _hitDelay;
				_comboDelayTimer = _comboDelay;
				shootSound.play();
			}	
		}
	}
	
	private void _handleRangedAttack(double delta){
		if(_rangedAttack){
			_hitRangedDelayTimer -= delta*Config.TIMER_DIF;
	
			if(_hitRangedDelayTimer<=0){
				_projectiles.add(new Projectile(new Animator("proj"),_name,this,_projectileStrength,_projectileSpeed,
												_projectileHitWidth,_projectileHitHeight,_projectileXOffset,_projectileYOffset));
				_rdyToAttack = true;
				_rangedAttack = false;
				_bullets--;
				_hitRangedDelayTimer = _hitRangedDelay;
				if(!getAnimator().isPlaying("jump")){
					getAnimator().setAnimation("idle");
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
	
	public void setBullets(int ammo){
		_bullets = ammo;
	}
	
	public int getBullets(){
		return _bullets;
	}
	
	public int getMaxBullets(){
		return _maxBullets;
	}
	
	public boolean isGameOver(){
		return _gameOver;
	}
	
	@Override
	public boolean isHit(AttackAbleObject dyn) {
		if(!_currentDead){
			return super.isHit(dyn);
		}
		return false;
	}

	public String getPlayerName(){
		return _name;
	}
	
	public void setLife(int life){
		_life = life;
	}
	public int getLife(){
		return _life;
	}
	
	public boolean isForceBlocked(){
		return _isForceBlocked;
	}
	
	public void setForceBlock(boolean f){
		_isForceBlocked = f;
	}
	
	public boolean isRunning(){
		return _isRunning;
	}
	
	public void setRunning(boolean run){
		_isRunning = run;
	}
}
