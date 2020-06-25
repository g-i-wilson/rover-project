public class Vector extends Point {

  // static method to initialize this vector using polar coordinates
  public static Vector createFromPolar ( double magnitude, double angle ) {
    return new Vector(
      magnitude * Math.cos( Math.toRadians(angle) ),
      magnitude * Math.sin( Math.toRadians(angle) )
    );
  }

  // constructor
  Vector () {
    super( 0.0, 0.0 );
  }

  // constructor
  Vector ( double vx, double vy ) {
    super( vx, vy );
  }

  // constructor
  Vector ( Vector v ) {
    super( v.getX(), v.getY() );
  }

  public double getMagnitude () {
    return Math.sqrt( Math.pow(getX(),2) + Math.pow(getY(),2) );
  }

  public double getAngle () {
    if ( getX() == 0.0 ) return 0.0; // avoid a "divide by zero"
    double angle = Math.toDegrees( Math.atan( getY()/getX() ) );
    // correct the angle for other quadrants
    if ( getX() < 0 ) {
      angle += 180;
    } else if ( getX() > 0 && getY() < 0 ) {
      angle += 360;
    }
    return angle;
  }

  // performs a vector addition
  // involves two Vectors: the values of another Vector are added to this Vector
  public Vector vectorAddition ( Vector another ) {
    return new Vector( getX()+another.getX(), getY()+another.getY() );
  }

  // scales (multiplies) by a coeficient
  // involves one Vector: the vaues of this Vector are each multiplied by a constant
  public Vector multiply ( double c ) {
    return new Vector( getX()*c, getY()*c );
  }

}
