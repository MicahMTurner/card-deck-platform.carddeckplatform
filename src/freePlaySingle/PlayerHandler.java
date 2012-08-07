package freePlaySingle;

import client.controller.ClientController;
import utils.Card;
import utils.Player;
import handlers.PlayerEventsHandler;

public class PlayerHandler implements PlayerEventsHandler{

	@Override
	public boolean onMyTurn(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTurnEnd(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCardAdded(Player player, Card card) {
		if(player.equals(ClientController.get().getMe())){
			card.reveal();
		}else{
			card.hide();
		}
		return true;
	}

	@Override
	public boolean onCardRemoved(Player player, Card card) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onCardRevealed(Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onRoundEnd(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

}
