package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import carddeckplatform.game.GameStatus;
//import communication.entities.Client;
//import communication.entities.TcpClient;


public class ServerConnection {
	private Socket socket;
	//private Client client;
	private Sender sender;
	private Receiver receiver;
	
	//-------Singleton implementation--------//
	private static class ServerConnectionHolder
	{
		private final static ServerConnection serverConnectionHolder=new ServerConnection();
	}
	
			
	/**
	 * get server connection instance
	 */
	public static ServerConnection getConnection(){
		return ServerConnectionHolder.serverConnectionHolder;
	}
	
	
	
	private ServerConnection(){	
		//this.client = new TcpClient(GameStatus.localIp , "jojo");
		
		//this.observer = observer;
		
		//clientMessageSender = new ClientMessageSender();
	   // clientMessageSender.setSender(sender, client);
	}
	
	public void openConnection(){

		try {
			// creates a socket.
			socket = new Socket(GameStatus.hostIp,GameStatus.hostPort);
			// creates an outputstream.
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		
			this.sender = new TcpSender(out);
			receiver = new TcpReceiver(in);
			receiver.initializeMode();
			sender.initializeMode();
			new Thread(receiver).start();
		//this.observer = observer;
		//sender.openConnection();
		
		//TcpReceiver.initializeMode();
	   // rceiver.reg(observer);
	    
	   // clientMessageSender.sendMessage(new InitialMessage(new AddPlayerAction(), GameStatus.username));
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	}
	
	public void closeConnection(){
		try {
			sender.closeStream();
			receiver.closeStream();
			socket.close();
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	public Sender getMessageSender(){
		return sender;
	}
}
