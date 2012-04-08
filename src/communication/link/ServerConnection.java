package communication.link;

import java.util.Observer;

import communication.client.ClientMessageSender;
import communication.entities.Client;

public class ServerConnection {
	
	private ClientMessageSender clientMessageSender;
	private Client c;
	private TcpSender s;
	private Observer observer;
	private Receiver rc;
	
	public ServerConnection(){}
	
	public ServerConnection(Client c, TcpSender s, Observer observer){
		this.c = c;
		this.s = s;
		this.observer = observer;
		
		clientMessageSender = new ClientMessageSender();
	    clientMessageSender.setSender(s, c);
	}
	
	public void openConnection(){
		s.openConnection();
		rc = new TcpReceiver(s.getIn());
	    rc.reg(observer);
	}
	
	public void closeConnection(){
		s.closeConnection();
	}
	
	public ClientMessageSender getMessageSender(){
		return clientMessageSender;
	}
}
