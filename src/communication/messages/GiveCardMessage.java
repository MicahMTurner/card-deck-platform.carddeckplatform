package communication.messages;

import carddeckplatform.game.TableView;

import communication.server.ServerMessageHandler;

public class GiveCardMessage extends Message{

	public String from;
	public String to;
	public int cardId;
	public boolean isRevealed;
	
	@Override
	public void serverAction(ServerMessageHandler serverMessageHandler) {
		// TODO send only to 'to'.
		
	}

	@Override
	public void clientAction(TableView tableView) {
//		tableView.addDraggable(card,isRevealed,cardId);
		
	}

}
