package client.controller.actions;

import logic.card.Card;
import logic.client.GameLogic;
import logic.client.Player;
import carddeckplatform.game.TableView;

public class GiveCardAction extends Action{

	private Player from;
	private Player to;
	private Card card;
	
	public GiveCardAction(TableView gui, GameLogic logic, Player from, Player to, Card card) {
		super(gui, logic);
		this.from=from;
		this.to=to;
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
