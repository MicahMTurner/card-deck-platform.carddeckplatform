package war;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import java.util.Queue;

import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.controller.ClientController;
import client.gui.entities.Droppable;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;


import utils.Button;
import utils.DeckArea;
import utils.Card;
import utils.Deck;
import utils.Player;
import utils.Point;
import utils.Position;
import utils.Public;
import utils.StandartCard;
import utils.StandartCard.Color;
import utils.droppableLayouts.DroppableLayout;

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

//		for (int i=0;i<deckSize;i++){			
//			Card card=deck.drawCard();			
//			//card.setOwner(players.get(i%players.size()).getPosition());
//			playersCards.get(i%players.size()).add(card);			
//			
//		}
		playersCards.get(0).add(new StandartCard(new CardHandler(),Color.CLUB.getCode()+2,"back",2,Color.CLUB));
		playersCards.get(0).add(new StandartCard(new CardHandler(),Color.CLUB.getCode()+3,"back",3,Color.CLUB));
		playersCards.get(0).add(new StandartCard(new CardHandler(),Color.CLUB.getCode()+4,"back",4,Color.CLUB));
		playersCards.get(0).add(new StandartCard(new CardHandler(),Color.CLUB.getCode()+5,"back",5,Color.CLUB));
		playersCards.get(1).add(new StandartCard(new CardHandler(),Color.CLUB.getCode()+2,"back",2,Color.CLUB));
		playersCards.get(1).add(new StandartCard(new CardHandler(),Color.CLUB.getCode()+7,"back",7,Color.CLUB));
		playersCards.get(1).add(new StandartCard(new CardHandler(),Color.CLUB.getCode()+8,"back",8,Color.CLUB));
		playersCards.get(1).add(new StandartCard(new CardHandler(),Color.CLUB.getCode()+9,"back",9,Color.CLUB));
		for (int i=0;i<players.size();i++){
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(playersCards.get(i),players.get(i).getId())));
		}
		
	}

	@Override
	public void setLayouts(ArrayList<Droppable> publics, ArrayList<Button> buttons) {
		
		publics.add(new Public(new PublicHandler(),Position.Public.MIDLEFT,DroppableLayout.LayoutType.HEAP , new Point(10,13)));
		publics.add(new Public(new PublicHandler(),Position.Public.MIDRIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,13)));		
	}

	

	@Override
	public Deck getDeck() {		
		return new Deck(new CardHandler(),true);
	}


	@Override
	public Integer onRoundEnd() {
		Integer answer=null;
		Public public1=(Public) ClientController.get().getZone(Position.Public.MIDLEFT);
		Public public2=(Public) ClientController.get().getZone(Position.Public.MIDRIGHT);
		War.tie=false;
		Player winner=getWinner(public1,public2);
		if (winner!=null){
			//move cards from public areas to winner			
			getCards(public1,winner);								
			getCards(public2,winner);
			answer=winner.getId();
		}else{
			ClientController.get().enableUi();	
		}
		if (checkAndDeclareGameWinner()){
			answer=null;
		}
		return answer;
	}
	
	private boolean checkAndDeclareGameWinner() {
		boolean answer=false;
		if (!War.tie){
			if (ClientController.get().getMe().isEmpty()){
				ClientController.get().disableUi();
				ClientController.get().declareLoser();
				answer=true;
			}else{
				if (ClientController.get().getZone(Position.Player.TOP).isEmpty()){
					ClientController.get().disableUi();
					ClientController.get().declareWinner();
					answer=true;
				}
			}
		}
		return answer;
		
	}	
	private void calculateRoundWinner(Public public1, Public public2) {		
		Player me=getMe();					
		War.tie=false;
			
		Player winner=getWinner(public1,public2);
		if (winner!=null){
			//move cards from public areas to winner			
			getCards(public1,winner);								
			getCards(public2,winner);				
			
		}else{			
				ClientController.get().enableUi();			
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

	@Override
	public Player getPlayerInstance(PlayerInfo playerInfo,
			utils.Position.Player position, int uniqueId) {
		
		return new Player(playerInfo, position, uniqueId, new PlayerHandler(), DroppableLayout.LayoutType.HEAP);
	}
	
}
	