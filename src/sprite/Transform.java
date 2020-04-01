package sprite;

import java.awt.geom.AffineTransform;

/**
* Das ist die Mutterklasse der Sprites. Es beinhaltet alle wichtige Transformationen inkl. Collisionsabfrage
* @author  Eduard Heller
* @version 1.0
*/
public class Transform {
	
	// X und Y Koordinate
	private double _x;
	private double _y;
	
	// Transformationen
	private AffineTransform _at;
	private AffineTransform _rat;
	
	// Ursprung der Transformation
	private double _cx;
	private double _cy;
	
	private boolean _isPivotRight = true;
	
	/**
	 * Konstruktor der Klasse Transform
	 */
	public Transform(){
		_rat = new AffineTransform();
		_x = 0;
		_y = 0;
	}
	
	/**
	 * Verschiebt die Transformation
	 * @param x Verschiebung
	 * @param y Verschiebung
	 */
	public void translate(double x,double y){
		_at = new AffineTransform();
		_at.translate(_cx,_cy);
		_at.translate(x, y);
		_at.translate(-_cx,-_cy);
		_rat.preConcatenate(_at);
		_x += x;
		_y += y;
	}
	
	/**
	 * Scalet die Transformation
	 * @param xscl Scalefaktor
	 * @param yscl Scalefaktor
	 */
	public void scale(double xscl,double yscl){
		_rat.translate(_cx,_cy);
		_rat.scale(xscl,yscl);
		_rat.translate(-_cx,-_cy);
	}
	
	/**
	 * Rotiert die Transformation
	 * @param degree Grad um welches die Transformation verschiebt werden soll
	 */
	public void rotate(double degree){
		double rotationRequired = Math.toRadians(degree);
		_rat.rotate(rotationRequired,_cx,_cy);
		
	}
	
	/**
	 * @return X-Koordinate
	 */
	public double getX(){
		return _x+_cx;
	}
	
	/**
	 * @return Y-Koordinate
	 */
	public double getY(){
		return _y+_cy;
	}
	
	/**
	 * @return Transformation der Instanz
	 */
	public AffineTransform getTransform(){
		return _rat;
	}
	
	
	public void flip(){
		_rat.translate(_cx,_cy);
		_rat.scale(-1,1);
		_rat.translate(-_cx,-_cy);
		_isPivotRight = !_isPivotRight;
	}
	
	public boolean isFlippedRight(){
		return _isPivotRight;
	}
	
	/**
	 * Setzt den Ursprung der Transformation
	 * @param x Koordinate des Ursprungs
	 * @param y Koordinate des Ursprungs
	 */
	public void setOrigin(int x, int y){
		_cx = x;
		_cy = y;
	}
	
	public int getOriginX(){
		return (int)Math.round(_cx);
	}
	
	public int getOriginY(){
		return (int)Math.round(_cy);
	}
}

