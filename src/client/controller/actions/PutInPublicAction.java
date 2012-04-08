package client.controller.actions;

import logic.card.Card;
import logic.client.GameLogic;
import logic.client.Player;
import carddeckplatform.game.TableView;

public class PutInPublicAction extends Action{
	Player player;
	Card card;
	boolean isRevealed;
	
	public PutInPublicAction(TableView gui, GameLogic logic, Player player, Card card, boolean isRevealed) {
		super(gui, logic);
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
