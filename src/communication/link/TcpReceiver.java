package communication.link;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;

import communication.messages.Message;
import communication.messages.SampleMessage;

public class TcpReceiver extends Receiver{
	private int port;
	
	public TcpReceiver(){}
	public TcpReceiver(int port){
		this.port = port;
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
							StringBuilder sb = new StringBuilder();
							String stringMessage;
							String tmpStr = "";
							Socket clientSocket;
							
							clientSocket = serverSocket.accept();
							System.out.println("Message Accepted");
						
							PrintWriter out=new PrintWriter(clientSocket.getOutputStream(),true);
							BufferedReader in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//						    while((tmpStr = in.readLine())!=null)
//						    	sb.append(tmpStr);
//							stringMessage = sb.toString();
						    stringMessage = in.readLine();
							System.out.println("the message is: " + stringMessage);
							Message message = unParseMessage(stringMessage);
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
