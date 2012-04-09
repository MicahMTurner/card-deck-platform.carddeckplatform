package communication.messages;

import logic.builtIn.defaultCards.Heart;
//import logic.host.Host;
import carddeckplatform.game.TableView;




public class SendCardMessage extends Message {
	
	public SendCardMessage(){
		messageType = "SendCardMessage";
	}
	
	public SendCardMessage(int cardId){
		messageType = "SendCardMessage";
		this.cardId = cardId;
	}
	
	public int cardId;
	
	@Override
	public void serverAction(String connectionId) {
		// TODO Auto-generated method stub
	}
	

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		//tableView.getGame().getLogic().cardGiven(new Heart(5));
	}

}
