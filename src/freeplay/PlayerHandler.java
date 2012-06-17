package freeplay;

import client.controller.ClientController;
import utils.Card;
import utils.Player;
import handlers.PlayerEventsHandler;

public class PlayerHandler implements PlayerEventsHandler{

	private boolean playerCardsVisible;
	private Player attachedPlayer;
	
	
	public void setAttachedPlayer(Player attachedPlayer) {
		this.attachedPlayer = attachedPlayer;
	}
	
	
	public void setPlayerCardsVisible(boolean playerCardsVisible) {
		this.playerCardsVisible = playerCardsVisible;
	}
	
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
		if(playerCardsVisible || attachedPlayer.equals(ClientController.get().getMe())){
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
