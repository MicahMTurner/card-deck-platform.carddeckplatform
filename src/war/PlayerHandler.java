package war;


import client.controller.ClientController;
import utils.Card;
import utils.Player;
import utils.Position;
import utils.Public;
import handlers.PlayerEventsHandler;

public class PlayerHandler implements PlayerEventsHandler{

	@Override
	public boolean onMyTurn(Player player) {	
		Public midRightPublic=(Public) (ClientController.get().getZone(Position.Public.MIDRIGHT));	// add methods.
		Public midLeftPublic=(Public) (ClientController.get().getZone(Position.Public.MIDLEFT));		
		if (!midRightPublic.isEmpty() && !midLeftPublic.isEmpty()){
			if (midRightPublic.cardsHolding()==midLeftPublic.cardsHolding() && !War.tie){	
				ClientController.get().endRound();
				//ClientController.sendAPI().endRound();
			}
		}		
		return true;
	}

	@Override
	public boolean onTurnEnd(Player player) {		
		return true;
	}

	@Override
	public boolean onCardAdded(Player player, Card card) {
		//card.setLocation(player.getX(), player.getY());
		if (player==null){
			card.hide();
			return true;
		}
		return false;
	}

	@Override
	public boolean onCardRemoved(Player player, Card card) {	
		if (player.getPosition().equals(card.getOwner())){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean onCardRevealed(Player player, Card card) {		
		return false;
	}

	@Override
	public boolean onRoundEnd(Player player) {
		return true;
	}

}
