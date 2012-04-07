package communication.messages;

import logic.builtIn.defaultCards.Heart;
import com.google.gson.annotations.SerializedName;
//import logic.host.Host;
import carddeckplatform.game.TableView;

import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public class SendCardMessage extends Message {
	
	public SendCardMessage(){
		messageType = "SendCardMessage";
	}
	
	public SendCardMessage(int cardId){
		messageType = "SendCardMessage";
		this.cardId = cardId;
	}
	
	@SerializedName("cardId")
	public int cardId;
	
	@Override
	public void serverAction() {
		// TODO Auto-generated method stub
	}
	

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		//tableView.getGame().getLogic().cardGiven(new Heart(5));
	}

}
