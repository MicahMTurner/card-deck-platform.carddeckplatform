package war;

import handlers.PublicEventsHandler;

import java.util.ArrayList;

import utils.Card;
import utils.Player;
import utils.Position;
import utils.Public;
import utils.StandartCard;
import client.controller.ClientController;

public class PublicHandler implements PublicEventsHandler{
	private int cardsPlacedWhileTie=0;
	//private boolean guiLocked=false;
	
	@Override
	public boolean onCardAdded(Public publicArea,Player byWhom, Card card) {
		
		boolean answer=false;
		Card cardInPublic=publicArea.peek();

		if (card.getOwner().equals(byWhom.getPosition())){	
			if (card.getOwner().equals(cardInPublic.getOwner())){				
				if (War.tie){
					if (cardsPlacedWhileTie<2){				
						card.hide();
						++cardsPlacedWhileTie;
					}else{
						if(cardsPlacedWhileTie==3){
							answer=false;
						}else{
							//already placed 2 cards upside-down
							card.reveal();
							Public midright=(Public) ClientController.get().getZone(Position.Public.MIDRIGHT);
							Public midleft=(Public) ClientController.get().getZone(Position.Public.MIDLEFT);
							
							++cardsPlacedWhileTie;
							if (midright.cardsHolding()==midleft.cardsHolding()){
								War.tie=false;
								ClientController.get().endRound();
							}
						}
					}
				}else{					
					cardsPlacedWhileTie=0;
					card.reveal();
					ClientController.get().getMe().endTurn();					
				}
				answer=true;
			}
		}
		return answer;
	}
	
	@Override
	public boolean onCardRemoved(Public publicZone, Player player, Card card) {
		return false;
	}
	@Override
	public boolean onCardRevealed(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onRoundEnd(Public publicZone, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

}
