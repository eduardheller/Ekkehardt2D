package character;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import config.Config;
import sprite.Transform;
import world.Background;
import world.World;

public abstract class GameObject implements Comparable<GameObject> {
	

	private Transform _transform;



	private double _z;
	
	// Kollisionshape
	private Shape _collideBox;
	
	// Kollisionsboxoffsets
	private int _colliderX = 0;
	private int _colliderY = 0;
	private int _colliderXscale = 0;
	private int _colliderYscale = 0;
	
	public GameObject(Transform trans, 
			int hurtx, int hurty,int hurtscale_x,int hurtscale_y){
		_transform = trans;
		this.setCollider(hurtx, hurty, hurtscale_x, hurtscale_y);

	}
	
	@Override
    public int compareTo(GameObject compareTo) {
        int compareZ=(int)((GameObject)compareTo).getZ();
        return (int)(compareZ-this.getZ());
    }
	
	public void setPosition(double x, double y){
		move(-getX(),-getZ());
		move(x,y);
	}
	
	/**
	 * Setzt die Stärke
	 * @param strength
	 */
	public Transform getTransform(){
		return _transform;
	}
	
	/**
	 * Bewegt den Actor und spiegelt den Actor gegebenenfalls
	 * @param x Richtung Bewegung
	 * @param y Richtung Bewegung
	 */
	public void move(double x,double y){
		_z += y;
		_transform.translate(x-Background.getMoveX(),y);
	}

	
	/**
	 * Erstellt einen Rectangle Shape der entsprechenden Transformatiot
	 * @return Die Shape
	 */
	public Shape getHurtBox(){
		AffineTransform as = new AffineTransform();
		as.setTransform(_transform.getTransform());
		_collideBox = as.createTransformedShape(new Rectangle(_colliderX,_colliderY,_colliderXscale,
															_colliderYscale));
		return _collideBox;
	}
	
	/**
	 * Übeprüft ob eine Kollision mit einer anderen Transformation stattfindet
	 * @param other Transformation mit der die Kollisionbfrage starten soll
	 * @return Ob Kollision erfolgreich war
	 */
	public boolean hasHurtBoxCollide(GameObject other){
		Area areaA = new Area(getHurtBox());
		areaA.intersect(new Area(other.getHurtBox()));
		return !areaA.isEmpty() && Math.abs(getZ()-other.getZ())<Config.Z_FIGHT_DIST;
	}
	
	/**
	 * Subtrahiert die Kollisionsshape
	 * @param x Substraktion
	 * @param y Substraktion
	 */
	public void setCollider(int x1,int y1, int x2, int y2){
		_colliderX = x1;
		_colliderY = y1;
		_colliderXscale = x2;
		_colliderYscale = y2;
	}
	
	/**
	 * Fragt ab ob dieses Objekt von einem Objekt getroffen wurde
	 * @param dyn AttackAbleObjekt
	 * @return
	 */
	public abstract boolean isHit(AttackAbleObject dyn);
	
	
	public abstract void update(Graphics2D g, double delta);
	
	/**
	 * 
	 * @return Z Kooardinate ( Unabhängig von Sprüngen etc.. )
	 */
	public double getZ(){
		return _z;
	}
	

	/**
	 * 
	 * @return X Kooardinate 
	 */
	public double getX(){
		return _transform.getX();
	}

	
	/**
	 * 
	 * @return Y Kooardinate ( Abhängig von Sprüngen etc.. )
	 */
	public double getY(){
		return  _transform.getY();
	}
	
	public void setZ(double z){
		_z = z;
	}

	
}
