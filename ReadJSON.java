import java.util.*;

public class ReadJSON {
	
  // states
  private static final int LINE_START_STATE = 0;
  private static final int LINE_END_STATE = 1;
  private static final int DATA_STATE = 2;
  private static final int COMMA_STATE = 3;
  private static final int ESCAPE_STATE = 4;


  // special strings
  private static String ROW_START = "\t<tr>\n";
  private static String ROW_END = "\t</tr>\n";
  private static String DATA_START = "\t\t<td>";
  private static String DATA_END = "</td>\n";
  private static String BLANK_DATA = "\t\t<td></td>\n";
  private static String TABLE_START = "<table>\n";
  private static String TABLE_END = "</table>\n";


  // state variable
  private static int state = LINE_START_STATE;

  // input variable
  private static int input;


  // input logic
  public static boolean input () {
    try {
      input = System.in.read();
      if (input != -1) return true;
      else return false;
    } catch (Exception e) {
      System.out.println( "ERROR: couldn't read from the standard input!" );
      return false;
    }
  }


  // transition and output logic
  public static void transition () {

    if (state == LINE_START_STATE) {
      if (input == ',') {
        System.out.print( ROW_START + BLANK_DATA );
        state = COMMA_STATE;
      } else if (input == '\n') {
        System.out.print( ROW_START + ROW_END );
        state = LINE_END_STATE;
      } else if (input == '\\') {
        // Only output the row and data start tags
        System.out.print( ROW_START + DATA_START );
        state = ESCAPE_STATE;
      } else {
        System.out.print( ROW_START + DATA_START + (char)input );
        state = DATA_STATE;
      }

    } else if (state == DATA_STATE) {
      if (input == ',') {
        System.out.print( DATA_END );
        state = COMMA_STATE;
      } else if (input == '\n') {
        System.out.print( DATA_END + ROW_END );
        state = LINE_END_STATE;
      } else if (input == '\\') {
        // Output nothing and go to the ESCAPE_STATE
        state = ESCAPE_STATE;
      } else {
        System.out.print( (char)input );
        state = DATA_STATE;
      }

    } else if (state == COMMA_STATE) {
      if (input == ',') {
        System.out.print( BLANK_DATA );
        state = COMMA_STATE;
      } else if (input == '\n') {
        System.out.print( BLANK_DATA + ROW_END );
        state = LINE_END_STATE;
      } else if (input == '\\') {
        // Only output the data start tag
        System.out.print( DATA_START );
        state = ESCAPE_STATE;
      } else {
        System.out.print( DATA_START + (char)input );
        state = DATA_STATE;
      }

    } else if (state == LINE_END_STATE) {
      if (input == ',') {
        System.out.print( ROW_START + BLANK_DATA );
        state = COMMA_STATE;
      } else if (input == '\n') {
        // Output nothing and stay in this state
      } else if (input == '\\') {
        // Only output the row and data start tags
        System.out.print( ROW_START + DATA_START );
        state = ESCAPE_STATE;
      } else {
        System.out.print( ROW_START + DATA_START + (char)input );
        state = DATA_STATE;
      }

    } else if (state == ESCAPE_STATE) {
      System.out.print( (char)input );
      state = DATA_STATE;

    }

  }


  // logic loop
  public static void main (String[] args) {

    System.out.print( TABLE_START );

    while (input()) {
      transition();
    }

    System.out.println( TABLE_END );

  }
