package communication.server;


import java.io.BufferedReader;
import java.io.PrintWriter;


public class ConnObj {
	
	public ConnObj(){}
	
	
	
	public ConnObj(PrintWriter out, BufferedReader in, String id){
		this.out = out;
		this.in = in;
		this.id = id;
	}
	
	public PrintWriter out;
	public BufferedReader in;
	public String id;
}
