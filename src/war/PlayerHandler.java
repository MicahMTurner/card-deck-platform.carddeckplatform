package war;


import client.controller.ClientController;
import utils.Card;
import utils.Player;
import utils.Position;
import handlers.PlayerEventsHandler;

public class PlayerHandler implements PlayerEventsHandler{

	@Override
	public boolean onMyTurn(Player player) {		
		if (ClientController.get().getZone(Position.Public.MIDRIGHT).cardsHolding()==
				ClientController.get().getZone(Position.Public.MIDLEFT).cardsHolding() && !War.tie){
			ClientController.get().disableUi();
			
			ClientController.sendAPI().endRound(ClientController.get().endRound());
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
