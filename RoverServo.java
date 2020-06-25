import java.util.*;

public class RoverServo extends ErrorTrackingInt {

	private double position; // -1.0 .. 1.0

	private double min;
	private double mid;
	private double max;
	
	private double slope;
	
	
	
	public RoverServo (double min, double mid, double max, double slope) {
		super( mid );
		this.min = min;
		this.mid = mid;
		this.max = max;
		this.slope = slope;
		this.position = 0.0;
	}
	
	public RoverServo () {
		this( 1000.0, 1500.0, 2000.0, 1.0 );
	}
	
	
	
	public double min () { return min; }
	
	public double min (double val) {
		min = val;
		return min;
	}
	
	public double max () { return max; }
	
	public double max (double val) {
		max = val;
		if (mid>max) mid = max;
		return max;
	}
	
	public double mid () { return mid; }
	
	public double mid (double val) {
		mid = val;
		if (mid<min) mid = min;
		return mid;
	}
	
	public double slope () { return slope; }
	
	public double slope (double val) { slope = val; return slope; }
	
	
	@Override
	public double input (double position) { // -1.0 .. 1.0
		if (position > 1.0) position = 1.0;
		if (position < -1.0) position = -1.0;
		double range = ( position >= 0 ? (max-mid) : (mid-min) );
		double value = ( position * slope * range ) + mid;
		if (value > max) value = max;
		if (value < min) value = min;
		super.input( value );
		this.position = position;
		return this.position;
	}
	
	@Override
	public double input () {
		return this.position;
	}
	
	
	public String toString () {
		return
			"Settings: " +
			min + "," +
			mid + "," + 
			max + "," + 
			slope + ", State: " + 
			input() + " -> " +
			output() + " err: " +
			error()
		;
	}
	
	
	// main method for testing
	public static void main (String[] args) throws Exception {
		RoverServo rs = new RoverServo();
		System.out.println( rs );
		for (int a=0; a<4; a++) {
			for (int b=0; b<40; b++) {
				Thread.sleep(100);
				System.out.println( rs );
			}
			Thread.sleep(100);
			System.out.println( "New value: " + rs.input(rs.input()+0.0154) + " error:" + rs.error() );
		}
	}
	
}