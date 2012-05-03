package war;

import client.controller.ClientController;
import utils.Card;
import handlers.CardEventsHandler;

public class CardHandler implements CardEventsHandler{

	@Override
	public void onReveal(Card card) {
		ClientController.sendAPI().cardRevealed(card);
		
	}

	@Override
	public void OnHide(Card card) {
		ClientController.sendAPI().cardHidden(card);
		
		
	}

}
