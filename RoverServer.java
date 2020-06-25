import java.util.*;
import java.io.*;
import java.net.*;

public class RoverServer
{

	private ServerSocket serverSocket; // Coninuously monitors port
	private Map<String,String> sensorData;  // HashMap to hold sensor data



	public RoverServer ( int port ) throws Exception {
		sensorData = new HashMap<>();
		sensorData.put( "forward", "unknown" );
		sensorData.put( "left", "unknown" );
		sensorData.put( "right", "unknown" );
		sensorData.put( "rear", "unknown" );
		serverSocket = new ServerSocket( port );
		while (true) {
			Socket socket = serverSocket.accept(); // new Socket object
			clientSession( socket );
			socket.close();
		}
	}
	
	
	private String readStdIn () {
		String line = "";
		try {
			while (System.in.available() > 0) {
				int oneChar = System.in.read();
				if (oneChar=='\n' || oneChar==-1) break;
				else line += (char)oneChar;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
	
	
	private void clientSession ( Socket socket ) throws Exception {
		Scanner reading = new Scanner( socket.getInputStream() );
		String command = reading.nextLine();
		if (command.equals("read")) {
		
			// read any new sensor data from the standard input into sensorData
			// there may or may not be new data
			while (true) {
				String stdin = readStdIn();
				if (stdin.equals("")) break;
				try {
					String[] strArray = stdin.split(",");
					sensorData.put( "forward", strArray[0] );
					sensorData.put( "left", strArray[1] );
					sensorData.put( "right", strArray[2] );
					sensorData.put( "rear", strArray[3] );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			PrintWriter writing = new PrintWriter( socket.getOutputStream(), true );
			for (String key : sensorData.keySet()) {
				writing.println( key + " " + sensorData.get(key) );
			}
			
		} else {
		
			// print any other command to the standard output
			System.out.println( command );
			
		}
	}



	public static void main(String[] args) throws Exception {
		RoverServer server = new RoverServer( Integer.valueOf(args[0]) );
	}

}
