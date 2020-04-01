package character;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import config.Config;
import sprite.Transform;

public abstract class AttackAbleObject extends GameObject{
	
	private int _strength;
	private Transform _transform;
	private boolean _hitting = false;
	
	// Kollisionsvariablen
	private int _cx1;
	private int _cx2;
	private int _cy1;
	private int _cy2;
	
	public AttackAbleObject(Transform trans, int strength, 
			int hurt_x, int hurt_y, int hurtscale_x, int hurtscale_y, int hitoffset_x1,
			int hitoffset_y1, int hitoffset_x2, int hitoffset_y2) {
		super(trans,hurt_x,hurt_y,hurtscale_x, hurtscale_y);
		_strength = strength;
		_transform = trans;
		this.setHitBoxOffsets(hitoffset_x1,hitoffset_y1,hitoffset_x2,hitoffset_y2);
		
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * Setzt die Stärke
	 * @param strength
	 */
	public void setStrength(int strength){
		_strength = strength;
	}
	
	/**
	 * 
	 * @return Stärke des Actors
	 */
	public int getStrength(){
		return _strength;
	}

	/**
	 * 
	 * @return ob gerade ein Hit erfolgt
	 */
	public boolean isHitting(){
		return _hitting;
	}
	
	/**
	 * Setzt den Hitstatus
	 * @param hit
	 */
	public void setHitting(boolean hit){
		_hitting = hit;
	}
	
	/**
	 * 
	 * @return HitBox des Actors
	 */
	public Shape getHitBox(){
		AffineTransform as = new AffineTransform();
		as.setTransform(_transform.getTransform());
		return as.createTransformedShape(new Rectangle(_cx1,_cy1,_cx2,_cy2));
	}
	
	/**
	 * Setzt Kooardinaten der Hitbox relativ zum Usprung des Game Objects
	 * @param x1 Offset ( Start X )
	 * @param y1 Offset ( Start Y )
 	 * @param x2 Offset ( Breite X )
	 * @param y2 Off ( Hoehe Y )
	 */
	public void setHitBoxOffsets(int x1,int y1, int x2, int y2){
		_cx1 = x1;
		_cx2 = x2;
		_cy1 = y1;
		_cy2 = y2;
	}
	
	/**
	 * Fragt nach ob die HitBox eine Transformation an der HurtBox getroffen hat
	 * @param other ActorObjekt
	 * @return Ob Kollision erfolgreich war
	 */
	public boolean hasHitBoxCollide(GameObject other){
		Area areaA = new Area(getHitBox());
		areaA.intersect(new Area(other.getHurtBox()));
		return !areaA.isEmpty() && Math.abs(getZ()-other.getZ())<Config.Z_FIGHT_DIST;
	}
	
	
}
