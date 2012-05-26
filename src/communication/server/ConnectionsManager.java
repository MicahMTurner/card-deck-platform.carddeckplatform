package communication.server;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import carddeckplatform.game.GameEnvironment;

import utils.Player;
import utils.Position;
import communication.actions.InitialConnectionAction;
import communication.link.Streams;
import communication.messages.InitialMessage;
import communication.messages.Message;

public class ConnectionsManager {
	private ServerSocket serverSocket=null;

	//-------Singleton implementation--------//
	private static class ConnectionsManagerHolder
	{
		private final static ConnectionsManager connectionsManager=new ConnectionsManager();
	}
			
					
	/**
	 * get Controller instance
	 */
	public static ConnectionsManager getConnectionsManager(){
		return ConnectionsManagerHolder.connectionsManager;
	}
			
	private ArrayList<Connection> connections;
	
	private ConnectionsManager() {
		connections = new ArrayList<Connection>();
		try {
			serverSocket = new ServerSocket(GameEnvironment.getGameEnvironment().getTcpInfo().getHostPort());
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	public int getNumberOfConnections(){
		return connections.size();
	}

		
	/**
	 * sendToAll - sends the message to every user.
	 * @param msg
	 */
	public void sendToAll(Message msg){
		for(Connection conn : connections){
			conn.send(msg);
		}
	}
	
	/**
	 * sendToAllExcptMe - sends the message to every user except for the user 'id'.
	 * @param msg
	 * @param id
	 */
	public void sendToAllExcptMe(Message msg , Position.Player id){
		for(Connection conn : connections){
			if(!conn.getId().equals(id)){
				conn.send(msg);
			}			
		}
	}
	
	/**
	 * sendTo - sends the message to user 'id'.
	 * @param msg
	 * @param id
	 */
	public void sendTo(Message msg, Position.Player id){
		for(Connection serverTask :connections){
			if(serverTask.getId().equals(id)){
				serverTask.send(msg);
				break;
			}
		}
	}
	
	
	public void connectHostingPlayer(Position.Player position,String gameId,ArrayList<Player> playersInfo){
		Acceptor acceptor = new TcpAcceptor(serverSocket);
		Streams s = acceptor.accept();
		addConnection(s, position, gameId, playersInfo);
	}
	
	
	
	public void connectPlayer(Position.Player position,String gameId,ArrayList<Player> playersInfo){			
		Acceptor acceptor = new TcpAcceptor(serverSocket);
		Streams s = acceptor.accept();
		
		addConnection(s, position, gameId, playersInfo);
		
		
		
		
		
//		try {				
//			Socket clientSocket;
//			//System.out.println("Listening to port " + GameStatus.hostPort + " Waiting for messages...");
//			
//			clientSocket = serverSocket.accept();
//			
//			System.out.println("connection request from from " + clientSocket.getRemoteSocketAddress().toString());
//			
//			ObjectOutputStream out=new ObjectOutputStream(clientSocket.getOutputStream());
//			ObjectInputStream in=new ObjectInputStream(clientSocket.getInputStream());
//			
//			Connection connection = new Connection(position,in, out);
//			connections.add(connection);
//			sendTo(new InitialMessage(new InitialConnectionAction(gameId,position,playersInfo)),position);
//			connection.getInitialMessage();			
//		    new Thread(connection).start();	
//		    
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public void addConnection(Streams s, Position.Player position,String gameId,ArrayList<Player> playersInfo){
			ObjectOutputStream out=s.getOut();
			ObjectInputStream in=s.getIn();
			
			Connection connection = new Connection(position,in, out);
			connections.add(connection);
			sendTo(new InitialMessage(new InitialConnectionAction(gameId,position,playersInfo)),position);
			connection.getInitialMessage();			
		    new Thread(connection).start();	

	}
	
}
