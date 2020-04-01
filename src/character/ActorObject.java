package character;

import java.awt.Graphics2D;

import config.Config;
import sprite.Animator;
import utils.SoundEffect;

/**
 * ActorObject - Abstrakte Klasse die eine Attackieren und Verletzt werden kann
 * @author Eduard Heller
 *
 */
public abstract class ActorObject extends AttackAbleObject{

	// Spritevariablen
	private Animator _animator;
	
	
	// Bewegungsvariablen
	private double _flipCord = 1;
	
	// Kampfvariablen
	private float _xBlockOffset = 90f;
	private double _blockDelay = 700;
	private double _blockSpeed = 1.5;
	private double _blockStrength = 2.0;
	private double _blockDelayTimer = _blockDelay;
	
	private int _hp = 0;
	private int _maxHp = 0;
	private boolean _blocked = false;
	private boolean _blockedFromRight = true;
	

	/**
	 * Konstruktor der ActorObjekte
	 * @param anim Animationssprite
	 * @param hp HP des Actors
	 * @param speed Laufgeschwindigkeit
	 */
	public ActorObject(Animator anim, int hp, int strength,int hurt_x,int hurt_y, int hurtscale_x, int hurtscale_y,
										int hitoffset_x1, int hitoffset_y1, int hitoffset_x2, int hitoffset_y2){
		super(anim,strength,hurt_x,hurt_y,hurtscale_x,hurtscale_y,hitoffset_x1,hitoffset_y1,hitoffset_x2,hitoffset_y2);
		_animator = anim;
		_hp = hp;

		
	}
	
	public Animator getAnimator(){
		return _animator;
	}
	
	/**
	 * Bewegt den Actor und spiegelt den Actor gegebenenfalls
	 * @param x Richtung Bewegung
	 * @param y Richtung Bewegung
	 */
	@Override
	public void move(double x,double y){
	
		super.move(x, y);
		if(x>0 && _flipCord<0){
			_animator.flip();
			_flipCord = x;
		}
		if(x<0 && _flipCord>0){
			_animator.flip();
			_flipCord = x;
		}
		
	}
	

	/**
	 * Update Funktion des Gameactors, diese wird in der Gameloop aufgerufen
	 * @param g Graphics2D Objekt
	 * @param delta Time
	 */
	public void update(Graphics2D g,double delta){
		_animator.show(g);
		if(!isDead()){
			if(isBlocked()){
				blockEffect(delta);
			}
			setHitting(false); // Ressetet Hitting, da nur nur einmal getroffen werden darf
			handleAttack(delta);
		}
	
	}
	

	/**
	 * Animationseffekte etc.. für die Attacken kommen hier rein ( wird von Update aufgerufen )
	 * @param delta time
	 */
	abstract void handleAttack(double delta);
	
	/**
	 * Startet den Angriff
	 */
	abstract void attack();
	
	
	/**
	 * Idle Status 
	 */
	abstract void idle();
	
	
	/**
	 * @param dyn AttackAbleObjekt
	 */
	public void takeDamage(AttackAbleObject dyn){
		setHp(getHp()-dyn.getStrength());
	}
	
	
	/**
	 * 
	 * @return Ob Actor tot ist
	 */
	public boolean isDead(){
		return getHp()<=0;
	}
	
	/**
	 * Fragt ab ob dieses Objekt von einem Objekt getroffen wurde
	 * @param dyn AttackAbleObjekt
	 * @return
	 */
	public boolean isHit(AttackAbleObject dyn){
		if(!isDead()){
			if(!_blocked){
				if(dyn.hasHitBoxCollide(this) && dyn.isHitting()){
					if(!dyn.getTransform().isFlippedRight()){
						_blockedFromRight = false;
					}else{
						_blockedFromRight = true;
					}
					_blocked = true;
					return true;
				}
				else{
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Setzt den Blocked Status, wenn geblockt dann kann dieser Actor keine Aktionen durchführen
	 * @param bl
	 */
	public void setBlocked(boolean bl){
		
		_blocked = bl;
	}
	
	/**
	 * 
	 * @return Ob Blocked
	 */
	public boolean isBlocked(){
		return _blocked;
	}
	
	/**
	 * Setzt den Blocked Status, wenn geblockt dann kann dieser Actor keine Aktionen durchführen
	 * @param bl
	 */
	public boolean isBlockedFromRight(){
		return _blockedFromRight;
	}
	
	
	/**
	 * Setzt die HP des Actors
	 * @param hp
	 */
	public void setHp(int hp){
		_hp = hp;
	}
	
	/**
	 * 
	 * @return HP des Actors
	 */
	public int getHp(){
		return _hp;
	}
	
	
	public void setMaxHp(int max){
		_maxHp = max;
	}
	
	public int getMaxHp(){
		return _maxHp;
	}
	
	
	/**
	 * Blockeffekt sagt an was passiert wenn der Aktor blocked ist. 
	 * @param delta
	 */
	void blockEffect(double delta){
		_xBlockOffset -= _blockSpeed*delta;
		double y = Math.toRadians(_xBlockOffset);
		if(getX()>50 && getX()<555){
			if(isBlockedFromRight()){
				_animator.translate(Math.sin(y)*_blockStrength*delta, 0);
			}else{
				_animator.translate(-Math.sin(y)*_blockStrength*delta, 0);
			}
		}

		
		_blockDelayTimer -= delta * Config.TIMER_DIF;
		
		if(_blockDelayTimer<=0){
			setBlocked(false);
			_blockDelayTimer = _blockDelay;
			_xBlockOffset = 100;
		}
	}

}
