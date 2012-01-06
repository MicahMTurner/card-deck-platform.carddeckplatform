package communication.link;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import communication.messages.Message;

public class TcpSender extends Sender {
	
	private String serverIp;
	private int port;
	
	public TcpSender(){}
	
	public TcpSender(String serverIp , int port){
		this.serverIp = serverIp;
		this.port = port;
	}

	@Override
	public void send(String className , Message msg) {
		// TODO Auto-generated method stub
		String stringMessage = parseMessage(className , msg);
		Socket socket=null;
		PrintWriter out=null;
		
		try {
			// creates a socket.
			socket = new Socket(serverIp,port);
			// creates an outputstream.
			out = new PrintWriter(socket.getOutputStream(),true);
			// sends the message.
			System.out.println("Sending message");
			out.println(stringMessage);
			System.out.println("Message sent");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
