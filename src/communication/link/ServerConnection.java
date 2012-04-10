package communication.link;

import java.util.Observer;

import logic.client.Game;

import client.controller.actions.AddPlayerAction;

import carddeckplatform.game.GameStatus;

import communication.client.ClientMessageSender;
import communication.entities.Client;
import communication.entities.TcpClient;
import communication.messages.InitialMessage;

public class ServerConnection {
	
	private ClientMessageSender clientMessageSender;
	private Client c;
	private TcpSender s;
	private Observer observer;
	private Receiver rc;
	
	private static ServerConnection serverConnection=new ServerConnection(new TcpClient(GameStatus.localIp , "jojo"), new TcpSender(GameStatus.hostIp , GameStatus.hostPort));
	public static ServerConnection getConnection(){
		return serverConnection;
	}
	
	
	private ServerConnection(){}
	
	private ServerConnection(Client c, TcpSender s){
		this.c = c;
		this.s = s;
		//this.observer = observer;
		
		clientMessageSender = new ClientMessageSender();
	    clientMessageSender.setSender(s, c);
	}
	
	public void openConnection(Observer observer){
		this.observer = observer;
		s.openConnection();
		rc = new TcpReceiver(s.getIn());
	    rc.reg(observer);
	    
	   // clientMessageSender.sendMessage(new InitialMessage(new AddPlayerAction(), GameStatus.username));
	}
	
	public void closeConnection(){
		s.closeConnection();
	}
	
	public ClientMessageSender getMessageSender(){
		return clientMessageSender;
	}
}
