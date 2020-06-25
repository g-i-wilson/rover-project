import java.time.*;

public class ErrorTrackingInt {

	// actual value as exact double
	private double doubleValue;
	
	// running error total from error function
	private double errorSum = 0.0;
	
	// timestamp object from the last and current samples of intValue()
	private Instant last;
	private Instant now;
	
	
	public ErrorTrackingInt (double value) {
		doubleValue = value;
		now = Instant.now();
	}
	
	
	private int calculateOutput () {
		return errorSum >= 0 ? (int)(doubleValue)+1 : (int)doubleValue ;
	}
	
	private void calculateError () {
		last = now;
		now = Instant.now();
		errorSum += ( doubleValue - calculateOutput() ) * Duration.between(last, now).toNanos();
	}
	
	public double error () {
		return errorSum;
	}
	
	public double input () {
		return doubleValue;
	}
	
	public double input (double value) {
		doubleValue = value;
		errorSum = 0; // reset error
		calculateError();
		return doubleValue;
	}
	
	public int output () {
		calculateError();
		return calculateOutput();
	}
	
	
	
	// main method for testing
	public static void main (String[] args) throws Exception {
		ErrorTrackingInt eti = new ErrorTrackingInt(3.999);
		System.out.println( eti.output() + ", " + eti.error() );
		for (int a=0; a<4; a++) {
			for (int b=0; b<10; b++) {
				Thread.sleep(100);
				System.out.println( eti.output() + ", " + eti.error() );
			}
			Thread.sleep(100);
			System.out.println( "New value: " + eti.input(eti.input()-1.1) + ", " + eti.error() );
		}
	}
	
}