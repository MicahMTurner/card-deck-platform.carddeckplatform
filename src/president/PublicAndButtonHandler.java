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
		if (counter==4){
			//got series,end round
			//ClientController.sendAPI().endRound();
			ClientController.get().endRound();
			
			
		}else if(this.player!=null && this.player.equals(player)){
				/*not first card added in this turn, all cards 
				/*must be equal to first one that was placed*/
				if (publicZone.peek().compareTo(card)==0){
					counter++;
					answer=true;
				}
		}
		else{
			//player just switched, go over switched player possible actions
			answer=switchedPlayerActions(publicZone, player,card);
			
		}	
		if (answer){
			card.reveal();
		}
		return answer;
	}
		
	/*
	 * covering all possible actions that player can do when he just got his turn 
	 */
	private boolean switchedPlayerActions(Public publicZone,Player player,Card card) {
		boolean answer=false;
		if (publicZone.cardsHolding()==1){
			//first player on round
			counter++;
			this.player=player;
			answer=true;
			
		}else if (card.compareTo(publicZone.peek())>0){
			//replying with bigger cards
			this.player=player;
			this.playerCounter=counter;
			counter=1;
			answer=true;
			
		}else if (((StandartCard)card).getValue()==2 && !publicZone.isEmpty()){
			//cutting cards
			//ClientController.sendAPI().endRound();
			ClientController.get().endRound();
			answer=true;
			
		}else if (card.compareTo(publicZone.peek())==0){
			//trying to complete series
			this.player=player;
			this.playerCounter=counter;
			counter++;
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
		publicZone.clear();
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
