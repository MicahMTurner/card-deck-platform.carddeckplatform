package communication.messages;

//import logic.host.Host;
import carddeckplatform.game.TableView;
import com.google.gson.annotations.SerializedName;
import communication.client.ClientMessageHandler;
import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public class RegistrationMessage extends Message {
	public RegistrationMessage(){
		messageType = "RegistrationMessage";
	}
	
	@SerializedName("clientName")
	public String clientName;
	
	@SerializedName("id")
	public String id;

	@Override
	public void serverAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		
	}
	
}
