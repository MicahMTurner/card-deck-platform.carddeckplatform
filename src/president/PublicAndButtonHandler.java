package president;

import client.controller.ClientController;
import utils.Card;
import utils.Player;
import utils.Public;
import utils.StandartCard;
import handlers.ButtonEventsHandler;
import handlers.PublicEventsHandler;

public class PublicAndButtonHandler implements PublicEventsHandler,ButtonEventsHandler{
	private int counter=0;
	private Integer playerCounter=0;
	private Player player=null;
	
	@Override
	public boolean onCardAdded(Public publicZone, Player player, Card card) {
		boolean answer=false;
		if (publicZone.isEmpty()){
			counter++;
			this.player=player;
			answer=true;
		}
		else if(this.player.equals(player) && (playerCounter==null || counter<=playerCounter)){
			
			if (publicZone.peek().compareTo(card)==0){
				counter++;
				answer=true;
			}
		}
		else{
			//first move of player that is not first in round
			if (card.compareTo(publicZone.peek())>0){
				//switched player
				this.player=player;
				this.playerCounter=counter;
				counter=0;
				
			}else if (((StandartCard)card).getValue()==2){
				//cut number placed, end round
				ClientController.sendAPI().endRound();
				ClientController.get().endRound();
			}
			
			
		}
		if (card.compareTo(publicZone.peek())>0 && publicZone.cardsHolding()<2){
			answer=true;
		}
		if (((StandartCard)card).getValue()==2){
			ClientController.sendAPI().endRound();
			ClientController.get().endRound();
			answer=true;
		}
		return answer;
	}

	@Override
	public boolean onCardRemoved(Public publicZone, Player player, Card card) {
		if (player!=null && card.getOwner().getId()==player.getId()){
			counter--;
			return true;
		}
		return true;
	}

	@Override
	public boolean onCardRevealed(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onRoundEnd(Public publicZone, Player player) {
		// TODO Auto-generated method stub
		init();
		return true;
	}

	@Override
	public void onClick() {
		if (playerCounter==null || (playerCounter!=null && counter==playerCounter)){
			ClientController.get().getMe().endTurn();
			President.passed=true;
		}
	}
	private void init(){
		
		this.counter=0;
		this.player=null;
		this.playerCounter=0;
	}

}
