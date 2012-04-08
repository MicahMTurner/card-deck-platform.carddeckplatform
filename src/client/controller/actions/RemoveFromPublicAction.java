package client.controller.actions;

import logic.card.Card;
import logic.client.GameLogic;
import logic.client.Player;
import carddeckplatform.game.TableView;

public class RemoveFromPublicAction extends Action{
	private Player player;
	private Card card;
	
	public RemoveFromPublicAction(TableView gui, GameLogic logic, Player player, Card card) {
		super(gui, logic);
		this.player=player;
		this.card=card;
	}

	@Override
	public void incoming() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outgoing() {
		// TODO Auto-generated method stub
		
	}

}
