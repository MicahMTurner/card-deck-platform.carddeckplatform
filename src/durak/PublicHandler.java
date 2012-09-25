package durak;

import client.controller.ClientController;
import utils.Card;
import utils.Player;
import utils.Public;
import utils.StandartCard;
import handlers.PublicEventsHandler;

public class PublicHandler implements PublicEventsHandler {

	@Override
	public boolean onCardAdded(Public publicZone, Player player, Card card) {
		boolean answer = false;
		// first move.
		if ((player.isMyTurn() && !Durak.hasActiveNumber())
				|| (!Durak.isAttacked(player)
						&& Durak.getActiveNumber(((StandartCard) card)
								.getValue()) && publicZone.cardsHolding() == 1)) {

			Durak.addActiveNumber(((StandartCard) card).getValue());
			ClientController.get().enableUi();
			answer = true;
		} else if (Durak.isAttacked(player)	&& (publicZone.isEmpty() || publicZone.cardsHolding() == 3)) { // attacked player tries to put a card on an empty space.	
			answer = false;
		} else if (Durak.isAttacked(player)) {	
			if (((StandartCard) publicZone.peek()).getValue() < ((StandartCard) card).getValue()
					&& (((StandartCard) publicZone.peek()).getColor() != Durak.getRulerColor()
							&& ((StandartCard) card).getColor() != Durak.getRulerColor() || ((StandartCard) publicZone.peek()).getColor() == Durak.getRulerColor()
							&& ((StandartCard) card).getColor() == Durak.getRulerColor()))
				answer = true;
			if (((StandartCard) card).getColor() == Durak.getRulerColor()
					&& ((StandartCard) publicZone.peek()).getColor() != Durak.getRulerColor())
				answer = true;
		}
		else if(!Durak.isAttacked(player) && (publicZone.isEmpty() || publicZone.cardsHolding() == 3)){
			answer = true;
		}
		
		
		if (answer)
			card.reveal();

		// ClientController.get().getMe().endTurn();
		return answer;
	}

	// @Override
	// public boolean onCardAdded(Public publicZone, Player player, Card card) {
	// boolean answer=true;
	// // first move.
	//
	// if(Durak.isAttacked(player) && (publicZone.isEmpty() ||
	// publicZone.cardsHolding()==3)){
	// answer = false;
	// }
	// else{
	// Durak.addActiveNumber(((StandartCard)card).getValue());
	// ClientController.get().enableUi();
	// }
	//
	// if(answer)
	// card.reveal();
	//
	// //ClientController.get().getMe().endTurn();
	// return answer;
	// }

	@Override
	public boolean onCardRemoved(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onCardRevealed(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onRoundEnd(Public publicZone, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFlipCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

}
