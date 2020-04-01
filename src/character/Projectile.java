package character;

import java.awt.Graphics2D;
import java.util.Random;

import sprite.Animator;



public class Projectile extends AttackAbleObject{
	
	private Animator _animation;
	private GameObject _object;
	private double _speed;
	private boolean _rightDir = true;
	
	private float _xdir;
	private float _ydir;
	
	private static float XDIR = 1.0f;
	private static float YDIR = 0.0f;
	
	private static boolean hitBelowRight = true;
	private static boolean hitBelowLeft = false;
	private static boolean hitTopLeft = false;
	private static boolean hitTopRight = false;
	
	private boolean _isBoss;
	
	public Projectile(Animator anim,String player,GameObject obj,int strength, double speed, int widthhitbox, int heightwidthbox, int xoffset, int yoffset){
		super(anim, strength,0,0,0,0,0,0,widthhitbox,heightwidthbox);
		_speed = speed;
		_animation = anim;
		_object = obj;
		_animation.setAnimation("effect");
		if(!_object.getTransform().isFlippedRight()){
			_rightDir = false;
			_animation.flip();
			move(_object.getTransform().getX()-xoffset, _object.getTransform().getY()+yoffset);
		}else{
			move(_object.getTransform().getX()+xoffset, _object.getTransform().getY()+yoffset);
		}
		if(player=="jasmin"){
			setZ(getZ());
		}else{
			setZ(getZ()+40);
		}
		
		_isBoss = false;
		
	}
	
	public Projectile(Animator anim,GameObject obj,int strength, double speed, int widthhitbox, int heightwidthbox, int xoffset, int yoffset){
		super(anim, strength,0,0,0,0,0,0,widthhitbox,heightwidthbox);
		_speed = speed;
		_animation = anim;
		_object = obj;
		_animation.setAnimation("effect");
		if(!_object.getTransform().isFlippedRight()){
			_rightDir = false;
			_animation.flip();
			move(300,240);
		}else{
			move(300,240);
		}
		
		_isBoss = true;
		_xdir = XDIR;
		_ydir = YDIR;
		
		if(hitBelowRight){
			XDIR -= 0.2;
			YDIR += 0.2;
		}
		if(hitBelowLeft){
			XDIR -= 0.2;
			YDIR -= 0.2;
		}
		if(hitTopLeft){
			XDIR += 0.2;
			YDIR -= 0.2;
		}
		if(hitTopRight){
			XDIR += 0.2;
			YDIR += 0.2;
		}
		
		if(YDIR>=1.0f && hitBelowRight){
			hitBelowRight = false;
			hitBelowLeft = true;
		}
		
		if(XDIR<=-1.0f && hitBelowLeft){
			hitBelowLeft = false;
			hitTopLeft = true;
		}
		
		if(YDIR<=-1.0f && hitTopLeft){
			hitTopRight = true;
			hitTopLeft = false;
		}
		
		if(XDIR>=1.0f && hitTopRight){
			hitTopRight = false;
			hitBelowRight = true;
		}
		
		if(XDIR<=0){
			_animation.flip();
		}
		
	}
	public void update(Graphics2D g,double delta){
		if(!_isBoss){
			_animation.show(g);
			setHitting(true);
			if(_rightDir){
				move(_speed*delta, 0);
			}else{
				move(-_speed*delta, 0);
			}
		}else{
			_animation.show(g);
			setHitting(true);
			move(_speed*_xdir*delta, _speed*_ydir*delta);
		}

		
	}

	@Override
	public boolean isHit(AttackAbleObject dyn) {
		// TODO Auto-generated method stub
		return false;
	}





}
