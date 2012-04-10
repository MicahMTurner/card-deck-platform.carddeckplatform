package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

//import com.google.gson.Gson;

import communication.messages.Message;

public class TcpSender extends Sender {
	private String serverIp;
	private int port;
	
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private Socket socket=null;
	
	public ObjectInputStream getIn(){
		return in;
	}
	
	
	public TcpSender(){}
	
	public TcpSender(String serverIp , int port){
		this.serverIp = serverIp;
		this.port = port;
	}

	@Override
	public void send(Message msg) {
		// TODO Auto-generated method stub
		// sends the message.
		System.out.println("Sending message");
		try {
			out.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Message sent");
	}

//	@Override
//	public void sendTo(String ip, String className, Message msg) {
//		// TODO Auto-generated method stub
//		String stringMessage = parseMessage(className , msg);
//		Socket socket=null;
//		PrintWriter out=null;
//		
//		try {
//			// creates a socket.
//			socket = new Socket(ip,port);
//			// creates an outputstream.
//			out = new PrintWriter(socket.getOutputStream(),true);
//			// sends the message.
//			System.out.println("Sending message");
//			out.println(stringMessage);
//			System.out.println("Message sent");
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	public boolean openConnection() {
		// TODO Auto-generated method stub
		
		
		try {
			// creates a socket.
			socket = new Socket(serverIp,port);
			// creates an outputstream.
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean closeConnection() {
		// TODO Auto-generated method stub
		
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	

}
