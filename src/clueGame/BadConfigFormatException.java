/**
 * @author John Baker and Taewoo Kim
 * Exception class for bad configuration file format
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {

	public BadConfigFormatException(){
		super("Error: Invalid configuration file format.");
		
		try {
			PrintWriter w = new PrintWriter("log.txt");
			
			
			w.write("Error: Invalid configuration file format.");
			w.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public BadConfigFormatException(String input){
		super("Error: " + input + " is an invalid configuration input.");
		
		try {
			PrintWriter w = new PrintWriter("log.txt");
			
			
			w.write("Error: " + input + " is an invalid configuration input.");
			w.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
