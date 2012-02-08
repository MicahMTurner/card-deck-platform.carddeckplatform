package carddeckplatform.game;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import communication.entities.Client;
import communication.entities.TcpClient;
import communication.link.Receiver;
import communication.link.Sender;
import communication.link.TcpReceiver;
import communication.link.TcpSender;
import communication.messages.CardMotionMessage;
import communication.messages.Message;
import communication.messages.RegistrationMessage;
import communication.server.ClientMapping;
import communication.server.ServerMessageSender;

public class SampleServer implements Observer {
	Sender s = new TcpSender("localhost" , 9999);
	Receiver r = null; //new TcpReceiver(9998);
	ServerMessageSender serverMessageSender; 
	
	public SampleServer(){
		serverMessageSender = new ServerMessageSender(s);
		r.reg(this);
		
	}

	@Override
	public void update(Observable arg0, Object data) {
		// TODO Auto-generated method stub
		Message message = (Message) data;
		System.out.println("Got server event");
		
		if(message.messageType.equals("CardMotionMessage")){
			CardMotionMessage cmm = (CardMotionMessage)message;
			
			
			
			ArrayList<Client> clients = ClientMapping.getAllClients();
			
			for(Client c : clients){
				System.out.println("client id: " + c.getId());
				if(!c.getId().equals(message.sender)){
					serverMessageSender.cardMotion(c, cmm.cardId, cmm.X, cmm.Y);
				}
			}
			
		}
		else if(message.messageType.equals("RegistrationMessage")){
			RegistrationMessage rg = (RegistrationMessage)message;
			Client c = new TcpClient((String)rg.id , rg.clientName);
			ClientMapping.addClient(c);
		}
		else if(message.messageType.equals("Something else...")){
			// do some other thing...
		}
	}
}
