package client.controller.actions;

import logic.card.CardLogic;
import logic.client.GameLogic;
import logic.client.Player;
import carddeckplatform.game.TableView;

public class PutInPublicAction extends ClientAction{
	Player player;
	CardLogic card;
	boolean isRevealed;
	
	public PutInPublicAction( Player player, CardLogic card, boolean isRevealed) {
		this.player=player;
		this.card=card;
		this.isRevealed=isRevealed;
	}

	@Override
	public void incoming() {
		
		
	}

	@Override
	public void outgoing() {
		
		
	}

}
