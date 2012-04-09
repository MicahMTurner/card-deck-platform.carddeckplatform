package communication.messages;

//import logic.host.Host;
import carddeckplatform.game.TableView;
import communication.client.ClientMessageHandler;


public class RegistrationMessage extends Message {
	public RegistrationMessage(){
		messageType = "RegistrationMessage";
	}
	
	public String clientName;
	
	public String id;

	@Override
	public void serverAction(String connectionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		
	}
	
}
