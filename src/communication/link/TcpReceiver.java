package communication.link;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;

import communication.messages.CardMotionMessage;
import communication.messages.Message;
import communication.messages.SampleMessage;

public class TcpReceiver extends Receiver{
	private int port;
	private BufferedReader in;
	
	public TcpReceiver(){}
//	public TcpReceiver(int port){
//		this.port = port;
//	}
	
	public TcpReceiver(BufferedReader in){
		this.in = in;
	}
	
	@Override
	public void startListen() {

	    final boolean stop=false;
	    try {
	    	final ServerSocket serverSocket = new ServerSocket(port);
			new Thread(new Runnable(){
				public void run(){
					while(!stop){
						try {							
							String stringMessage;
						    stringMessage = in.readLine();
							System.out.println("the message is: " + stringMessage);
							Message message = unParseMessage(stringMessage);
							// adds the ip address of the sender to the message.
							setChanged();
							notifyObservers(message);
							System.out.println("Message delivared");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						} 
					}					
				}
			}).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	    
	}

}
