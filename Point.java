public class Point {

  private double x;
  private double y;


  public Point ( double initX, double initY ) {
    x = initX;
    y = initY;
  }

  public double getX () {
    return x;
  }

  public double getY() {
    return y;
  }

  public void translate ( double addX, double addY ) {
    x += addX;
    y += addY;
  }
  
  public void moveTo ( double setX, double setY ) {
  	x = setX;
  	y = setY;
  }
  
  @Override
  public String toString () {
    return "(" + x + ", " + y + ")";
  }

}
