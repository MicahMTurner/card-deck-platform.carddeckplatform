package client.controller.actions;

import logic.card.CardLogic;
import logic.client.GameLogic;
import logic.client.Player;
import carddeckplatform.game.TableView;

public class GiveCardAction extends ClientAction{

	private Player from;
	private Player to;
	private CardLogic card;
	
	public GiveCardAction( Player from, Player to, CardLogic card) {
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
