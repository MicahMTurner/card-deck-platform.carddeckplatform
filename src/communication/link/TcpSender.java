package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import client.controller.ClientController;

//import com.google.gson.Gson;

import communication.messages.InitialMessage;
import communication.messages.Message;

public class TcpSender extends Sender {
	//private String serverIp;
	//private int port;
	
	private ObjectOutputStream out = null;
	//private ObjectInputStream in = null;
	//private Socket socket=null;
	
	//public ObjectInputStream getIn(){
	//	return in;
	//}
	
	
	public TcpSender(ObjectOutputStream out){
		this.out=out;
	}
	
	//public TcpSender(String serverIp , int port){
	//	this.serverIp = serverIp;
	//	this.port = port;
	//}

	@Override
	public void send(Message msg) {
		// TODO Auto-generated method stub
		// sends the message.
		System.out.println("Sending message");
		try {
			out.writeObject(msg);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		System.out.println("Message sent");
	}

	@Override
	public boolean closeStream() {
		try {
			out.close();
		} catch (IOException e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void initializeMode() {
		send(new InitialMessage(ClientController.getController().getLogic().getMe()));
		
	}

//	@Override
//	public boolean openConnection() {
//		
//		try {
//			// creates a socket.
//			socket = new Socket(serverIp,port);
//			// creates an outputstream.
//			out = new ObjectOutputStream(socket.getOutputStream());
//			in = new ObjectInputStream(socket.getInputStream());
//			
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//		
//		return true;
//	}
//
//	@Override
//	public boolean closeConnection() {
//		// TODO Auto-generated method stub
//		
//		try {
//			out.close();
//			in.close();
//			socket.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//		
//		return true;
//	}
	
	

}
