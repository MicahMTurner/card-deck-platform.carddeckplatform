package war;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import java.util.Queue;

import client.controller.ClientController;
import client.gui.entities.Droppable;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;


import utils.DeckArea;
import utils.Card;
import utils.Deck;
import utils.Player;
import utils.Position;
import utils.Public;
import utils.StandartCard;

import logic.client.Game;




public class War extends Game{
	static public boolean tie=false;


	@Override
	public Queue<Position.Player> setTurns() {
		return utils.Turns.clockWise(Position.Player.BOTTOM);
		
	}

	@Override
	public int minPlayers() {		
		return 2;
	}

	@Override
	public int cardsForEachPlayer() {
		return 0;
	}

	

	@Override
	public String toString() {		
		return "War";
	}

	@Override
	public void dealCards() {
		//TODO add "default deal" or "deal all cards"
		int deckSize=deck.getSize();
		
		int numOfPlayers=players.size();
		
		ArrayList<ArrayList<Card>> playersCards= new ArrayList<ArrayList<Card>>();
		for (int i=0;i<numOfPlayers;i++){
			playersCards.add(new ArrayList<Card>());
		}

		for (int i=0;i<deckSize;i++){			
			Card card=deck.drawCard();			
			//card.setOwner(players.get(i%players.size()).getPosition());
			playersCards.get(i%players.size()).add(card);			
			
		}
		for (int i=0;i<players.size();i++){
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(playersCards.get(i),players.get(i).getId())));
		}
		
	}

	@Override
	public void setLayouts(ArrayList<Droppable> publics) {
		publics.add(new Public(new PublicHandler(),Position.Public.MIDLEFT));
		publics.add(new Public(new PublicHandler(),Position.Public.MIDRIGHT));		
	}
//	@Override
//	public void getLayouts(ArrayList<Droppable> publics){//,ArrayList<Button>buttons) {
//		publics.add(new Public(new PublicHandler(),Position.Public.MIDLEFT));
//		publics.add(new Public(new PublicHandler(),Position.Public.MIDRIGHT));
//	}

	

	@Override
	public Deck getDeck() {		
		return new Deck(new CardHandler(),true);
	}

	@Override
	public PlayerEventsHandler getPlayerHandler() {
		return new PlayerHandler();
	}

	@Override
	public void onRoundEnd() {		
		Public public1=(Public) ClientController.get().getZone(Position.Public.MIDLEFT);
		Public public2=(Public) ClientController.get().getZone(Position.Public.MIDRIGHT);
		calculateRoundWinner(public1,public2);
		checkAndDeclareGameWinner();		
	}
	
	private void checkAndDeclareGameWinner() {
		if (!War.tie){
			if (ClientController.get().getMe().isEmpty()){
				ClientController.get().disableUi();
				ClientController.get().declareLoser();
				
			}else{
				if (ClientController.get().getZone(Position.Player.TOP).isEmpty()){
					ClientController.get().disableUi();
					ClientController.get().declareWinner();					
				}
			}
		}
		
	}	
	private void calculateRoundWinner(Public public1, Public public2) {		
		Player me=getMe();					
		War.tie=false;
			
		Player winner=getWinner(public1,public2);
		if (winner!=null){
			//move cards from public areas to winner			
			getCards(public1,winner);								
			getCards(public2,winner);
				
			if (!winner.equals(me)){
				me.endTurn();
			}
		}		
	}
	private void getCards(Public publicArea, Player player){
		for (StandartCard card : ((ArrayList<StandartCard>)((ArrayList)publicArea.getCards()))){
			card.moveTo(publicArea,player);
			
		}
	}
	private Player getWinner(Public public1,Public public2){
		int comparisonAnswer=((StandartCard)public1.peek()).compareTo((StandartCard)public2.peek());
		if (comparisonAnswer>0){
			return (Player)(ClientController.get().getZone(public1.peek().getOwner()));			
		}
		else if (comparisonAnswer<0){			
			return (Player)(ClientController.get().getZone(public2.peek().getOwner()));
		}else{
			//tie
			War.tie=true;
			return null;
		}
	}
	
}
	