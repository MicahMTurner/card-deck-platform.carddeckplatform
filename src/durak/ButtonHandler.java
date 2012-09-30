package durak;

import utils.Card;
import handlers.ButtonEventsHandler;
import client.controller.ClientController;

public class ButtonHandler implements ButtonEventsHandler {
	
	@Override
	public void onClick() {
		//ClientController.get().requestCard(Durak.deckId, 1);
		
		//ClientController.get().getZone(Durak.deckId).peek().moveTo(ClientController.get().getZone(Durak.deckId), ClientController.get().getMe());
		
		
		if(Durak.isAttacked(ClientController.get().getMe()) && Durak.hasOpenCards() || Durak.getCurrentAttacker().equals(ClientController.get().getMe()) && !Durak.hasOpenCards() && Durak.hasActiveNumber()){
			ClientController.sendAPI().endRound();
			ClientController.get().endRound();
		}	
	}

	@Override
	public boolean onFlipCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

}
