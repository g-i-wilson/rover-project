import java.util.*;
import java.io.*;
import java.net.*;

public class RoverClient {

  String address;
  int port;


  public RoverClient ( String address, int port ) {
    this.address = address;
    this.port = port;
  }


  public String roverCommand ( String command ) throws Exception {
  	// open a new socket
	Socket socket = new Socket( address, port );
	// write a string to the socket
	PrintWriter writing = new PrintWriter( socket.getOutputStream(), true ); // true: autoflush
	writing.println(command);
	//writing.close();
	// read the responce string from the socket
	Scanner reading = new Scanner( socket.getInputStream() );
	String data = "";
	while (reading.hasNextLine()) {
		data += reading.nextLine() + "\n";
	}
	// close the socket
	socket.close();
	// return the string from the socket
	return data;
  }


  public static void main (String[] args) throws Exception {
    RoverClient roverClient = new RoverClient( args[0], Integer.valueOf(args[1]) );
    Scanner stdin = new Scanner( System.in );
    while (true) {
    	System.out.print( "> " );
    	String command = stdin.nextLine();
    	if (command.equals("exit")) {
    		break;
    	} else {
    		String res = roverClient.roverCommand( command );
    		System.out.println( res );
    	}
    }
  }

}
