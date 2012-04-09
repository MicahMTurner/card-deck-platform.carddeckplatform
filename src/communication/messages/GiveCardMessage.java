package communication.messages;
//import logic.host.Host;
import carddeckplatform.game.TableView;




public class GiveCardMessage extends Message{

	public String from;
	public String to;
	public int cardId;
	public boolean isRevealed;
	
	@Override
	public void serverAction(String connectionId) {
		// TODO send only to 'to'.
		
	}

	@Override
	public void clientAction() {
//		tableView.addDraggable(card,isRevealed,cardId);
		
	}

}
