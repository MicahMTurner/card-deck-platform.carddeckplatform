package communication.actions;

import utils.Card;
import utils.DeckArea;
import client.controller.ClientController;

public class SetRulerCardAction implements Action {
	private Card card;
	private Integer targetID;
	
	public SetRulerCardAction(Card card, Integer targetID){
		this.card = card;
		this.targetID = targetID;
	}
	
	@Override
	public void execute() {
		// get the local instance of the card.
		Card actualCard = ClientController.get().getCard(card.getId());
		// get the deck instance.
		DeckArea target = (DeckArea)ClientController.get().getZone(targetID);
		// set the ruler card.
		target.setRulerCard(actualCard);
	}
	
}
