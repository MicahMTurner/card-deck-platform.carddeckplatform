package communication.server;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;

import carddeckplatform.game.gameEnvironment.GameEnvironment;
import carddeckplatform.game.gameEnvironment.GameEnvironment.ConnectionType;

import utils.Player;
import utils.Position;
import communication.actions.InitialConnectionAction;
import communication.link.Streams;
import communication.messages.InitialMessage;
import communication.messages.Message;

public class ConnectionsManager {
	private ServerSocket serverSocket=null;
	private BluetoothServerSocket bluetoothServerSocket=null;

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
//		try {
//			if(GameEnvironment.getGameEnvironment().getConnectionType()==ConnectionType.TCP || GameEnvironment.getGameEnvironment().getPlayerInfo().isServer()){
//				serverSocket = new ServerSocket(GameEnvironment.getGameEnvironment().getTcpInfo().getHostPort());
//			}
//			else if(GameEnvironment.getGameEnvironment().getConnectionType()==ConnectionType.BLUETOOTH){
//				
//				bluetoothServerSocket = GameEnvironment.getGameEnvironment().getBluetoothInfo().bluetoothServerSocket;
//			}	
//		} catch (IOException e) {			
//			e.printStackTrace();
//		}
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
	public void sendToAllExcptMe(Message msg , int id){
		for(Connection conn : connections){
			if(conn.getId()!=id){
				conn.send(msg);
			}			
		}
	}
	
	/**
	 * sendTo - sends the message to user 'id'.
	 * @param msg
	 * @param id
	 */
	public void sendTo(Message msg, int id){
		for(Connection serverTask :connections){
			if(serverTask.getId()==id){
				serverTask.send(msg);
				break;
			}
		}
	}
	
	/**
	 * connects the hosting player. Will always use TCP connection. 
	 * @param position
	 * @param gameId
	 * @param playersInfo
	 */
	public void connectHostingPlayer(Position.Player position,String gameId,ArrayList<Player> playersInfo){
		Acceptor acceptor = new TcpAcceptor();
		Streams s = acceptor.accept();
		addConnection(s, position, gameId, playersInfo);
	}
	
	
	/**
	 * connects players. Will use either TCP or BLUETOOTH.
	 * @param position
	 * @param gameId
	 * @param playersInfo
	 */
	public void connectPlayer(Position.Player position,String gameId,ArrayList<Player> playersInfo){			
		Acceptor acceptor=null;
		// accept connections according to the connection type specified by the user.
		if(GameEnvironment.get().getConnectionType()==ConnectionType.TCP)
			acceptor = new TcpAcceptor();
		else if(GameEnvironment.get().getConnectionType()==ConnectionType.BLUETOOTH)
			acceptor = new BluetoothAcceptor();
		
		Streams s = acceptor.accept();
		
		addConnection(s, position, gameId, playersInfo);
	}
	
	/**
	 * adds a new connection to the connections list.
	 * @param s
	 * @param position
	 * @param gameId
	 * @param playersInfo
	 */
	public void addConnection(Streams s, Position.Player position,String gameId,ArrayList<Player> playersInfo){
			ObjectOutputStream out=s.getOut();
			ObjectInputStream in=s.getIn();
			
			Connection connection = new Connection(position.getId(),in, out);
			connections.add(connection);
			sendTo(new InitialMessage(new InitialConnectionAction(gameId,position,playersInfo)),connection.getId());
			connection.getInitialMessage();			
		    new Thread(connection).start();	
			
			//GameEnvironment.getGameEnvironment().getHandler().post(connection);

	}
	
}
