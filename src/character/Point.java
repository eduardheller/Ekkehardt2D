package character;

public class Point {

	public double x;
	public double y;
	
	public Point normalize() {
	   Point v2 = new Point();

	   double length = Math.sqrt( this.x*this.x + this.y*this.y );
	   if (length != 0) {
		   v2.x = this.x/length;
		   v2.y = this.y/length;
	   }
	   return v2;
	}   
}
