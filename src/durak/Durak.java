package durak;

import java.util.ArrayList;
import java.util.Queue;

import client.controller.ClientController;
import client.gui.entities.Droppable;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;

import utils.Button;
import utils.Card;
import utils.Deck;
import utils.DeckArea;
import utils.Pair;
import utils.Point;
import utils.Position;
import utils.Public;
import utils.Position.Player;
import utils.StandartCard;
import utils.droppableLayouts.DroppableLayout;

import carddeckplatform.game.gameEnvironment.PlayerInfo;
import logic.client.Game;

public class Durak extends Game{
	
	public Durak(){
		initActiveNumbers();
	}

	static private boolean activeNumbers[] = {false,false,false,false,false,false,false,false,false};
	
	static public boolean hasActiveNumber(){
		for(int i=0; i<9;i++)
			if(activeNumbers[i]==true)
				return true;
		return false;
	}
	
	static public void initActiveNumbers(){
		for(int i=0; i<9;i++)
			activeNumbers[i]=false;
	}
	
	
	static boolean getActiveNumber(int num){
		return activeNumbers[num-6];
	}
	
	static public void addActiveNumber(int num){
		activeNumbers[num-6]=true;
	}
	
	static boolean isAttacked(utils.Player player){
		for(int i=0; i<staticPlayers.size() ; i++){
			if(staticPlayers.get(i).isMyTurn() && staticPlayers.get( (i + 1) % staticPlayers.size()).equals(player)){
				return true;
			}
		}
		return false;
	}
	
	private utils.Player getAttackedPlayer(){
		for(utils.Player player : super.players){
			if(isAttacked(player))
				return player;
		}
		return null;
	}
	
	@Override
	public Deck getDeck() {	
		ArrayList<Card> cardsToRemove = new ArrayList<Card>();
		Deck deck = new Deck(new CardHandler(),true);	
		for(Card card : deck.getCards())
			if( ((StandartCard) card).getValue() < 6)
				cardsToRemove.add(card);
		deck.getCards().removeAll(cardsToRemove);
		return deck;
	}

	@Override
	public Integer onRoundEnd() {

		Public public1 =  (Public)ClientController.get().getZone(Position.Public.BOTMID);
		Public public2 =  (Public)ClientController.get().getZone(Position.Public.BOTMIDLEFT);
		Public public3 =  (Public)ClientController.get().getZone(Position.Public.BOTMIDRIGHT);
		Public public4 =  (Public)ClientController.get().getZone(Position.Public.TOPMID);
		Public public5 =  (Public)ClientController.get().getZone(Position.Public.TOPMIDLEFT);
		Public public6 =  (Public)ClientController.get().getZone(Position.Public.TOPMIDRIGHT);
		
		DeckArea junk = (DeckArea)ClientController.get().getZone(Position.Button.BOTLEFT);
		
		
		// The attacked player managed to beat all cards.
		if(		public1.cardsHolding()%2==0 &&
				public2.cardsHolding()%2==0 &&
				public3.cardsHolding()%2==0 &&
				public4.cardsHolding()%2==0 &&
				public5.cardsHolding()%2==0 &&
				public6.cardsHolding()%2==0){
			
			ArrayList<Pair<Droppable , Droppable>> pairs = new ArrayList<Pair<Droppable , Droppable>>();
			
			pairs.add(new Pair(public1, junk));
			pairs.add(new Pair(public2, junk));
			pairs.add(new Pair(public3, junk));
			pairs.add(new Pair(public4, junk));
			pairs.add(new Pair(public5, junk));
			pairs.add(new Pair(public6, junk));
			Card.moveTo(pairs);	
		}else{
			
			utils.Player attacktedPlayer = getAttackedPlayer();	
			ArrayList<Pair<Droppable , Droppable>> pairs = new ArrayList<Pair<Droppable , Droppable>>();		
			pairs.add(new Pair(public1, attacktedPlayer));
			pairs.add(new Pair(public2, attacktedPlayer));
			pairs.add(new Pair(public3, attacktedPlayer));
			pairs.add(new Pair(public4, attacktedPlayer));
			pairs.add(new Pair(public5, attacktedPlayer));
			pairs.add(new Pair(public6, attacktedPlayer));		
			Card.moveTo(pairs);
		}
			
		
		return null;
	}

	@Override
	protected Queue<Player> setTurns() {
		return utils.Turns.clockWise(Position.Player.BOTTOM);
	}

	@Override
	public int minPlayers() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void dealCards() {
		int deckSize=deck.getSize();
		int numOfPlayers=players.size();
		ArrayList<Card> deckCards = new ArrayList<Card>();
		
		
		ArrayList<ArrayList<Card>> playersCards= new ArrayList<ArrayList<Card>>();
		for (int i=0;i<numOfPlayers;i++){
			playersCards.add(new ArrayList<Card>());
		}
		
		for(int i=0; i<numOfPlayers; i++){
			for(int j=0; j<6; j++)
				playersCards.get(i).add(deck.drawCard());
		}
		
		for(Card card : deck.getCards())
			deckCards.add(card);
		
		for (int i=0;i<players.size();i++){
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(playersCards.get(i),players.get(i).getId())));
		}
		
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(deckCards,Position.Button.TOPRIGHT.getId())));
	}

	@Override
	public void setLayouts() {
		droppables.add(new DeckArea(Position.Button.TOPRIGHT));
		
		
		PublicHandler publicArea = new PublicHandler();
		JunkHandler junkHandler = new JunkHandler();
		
		droppables.add(new Public(publicArea, Position.Public.BOTMID,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
		droppables.add(new Public(publicArea, Position.Public.BOTMIDLEFT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
		droppables.add(new Public(publicArea, Position.Public.BOTMIDRIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
		droppables.add(new Public(publicArea, Position.Public.TOPMID,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
		droppables.add(new Public(publicArea, Position.Public.TOPMIDLEFT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
		droppables.add(new Public(publicArea, Position.Public.TOPMIDRIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
		
		buttons.add(new Button(new ButtonHandler(), Position.Button.BOTRIGHT, "Pass"));
		
		droppables.add(new DeckArea(Position.Button.BOTLEFT));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Durak";
	}

	@Override
	public utils.Player getPlayerInstance(PlayerInfo playerInfo,
			Player position, int uniqueId) {
		// TODO Auto-generated method stub
		return new utils.Player(playerInfo, position, uniqueId, new PlayerHandler(), DroppableLayout.LayoutType.LINE);
	}

	@Override
	public String instructions() {
		// TODO Auto-generated method stub
		return null;
	}

}
