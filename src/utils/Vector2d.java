package utils;
public class Vector2d {

   public double dX;
   public double dY;

   // Constructor methods ....
   public Vector2d( double dXp, double dYp ) {
	      this.dX = dXp;
	      this.dY = dYp;
   }
   
   public Vector2d() {
      dX = dY = 0.0;
   }



   // Convert Vector2d to a string ...
    
   public String toString() {
      return "Vector2d(" + dX + ", " + dY + ")";
   }

   // Compute magnitude of Vector2d ....
 
   public double length() {
      return Math.sqrt ( dX*dX + dY*dY );
   }

   // Sum of two Vector2ds ....

   public void add( Vector2d v1 ) {
       this.dX += v1.dX;
       this.dY += v1.dY;
   }

   // Subtract Vector2d v1 from v .....

   public void sub( Vector2d v1 ) {
       this.dX -= v1.dX;
       this.dY -= v1.dY;
   }

   // Scale Vector2d by a constant ...

   public void scale( double scaleFactor ) {
	   this.dX *=scaleFactor;
	   this.dY *=scaleFactor;
   }

   // Normalize a Vector2ds length....

   public Vector2d normalize() {
      Vector2d v2 = new Vector2d();

      double length = Math.sqrt( this.dX*this.dX + this.dY*this.dY );
      if (length != 0) {
        v2.dX = this.dX/length;
        v2.dY = this.dY/length;
      }

      return v2;
   }   

   // Dot product of two Vector2ds .....

   public double dotProduct ( Vector2d v1 ) {
        return this.dX*v1.dX + this.dY*v1.dY;
   }
 
}