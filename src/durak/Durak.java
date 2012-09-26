package durak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

import javax.tools.StandardLocation;

import android.graphics.Color;

import client.controller.ClientController;
import client.gui.entities.Droppable;

import communication.actions.DealCardAction;
import communication.actions.SetRulerCardAction;
import communication.messages.Message;
import communication.messages.RequestCardMessage;
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
	Card rulerCard;
	utils.Player startingPlayer;
	Button button;
	static private StandartCard.Color rulerColor=null;
	static private ArrayList<utils.Player> winners=new ArrayList<utils.Player>();
	
	static private ArrayList<utils.Player> getSortedPlayers(){
		ArrayList<utils.Player> sortedPlayers = new ArrayList<utils.Player>(staticPlayers);
		
		sortedPlayers.removeAll(winners);
		// sort the players by their position.
		Collections.sort(sortedPlayers, new PlayerComp());
		
		return sortedPlayers;
	}
	
	private static class PlayerComp implements Comparator<utils.Player>{

		@Override
		public int compare(utils.Player player1, utils.Player player2) {
			int answer = -1;
			if(player1.getGlobalPosition()==Player.BOTTOM)
				answer=1;
			else if(player1.getGlobalPosition()==Player.LEFT && player2.getGlobalPosition()!=Player.BOTTOM)
				answer=1;
			else if(player1.getGlobalPosition()==Player.TOP && player2.getGlobalPosition()==Player.RIGHT)	
				answer=1;
				
			return answer;
		}

				
	}
	
	static private PlayerComp playerComp = new PlayerComp();
	
	private static utils.Player getNextPlayer(utils.Player player){
		ArrayList<utils.Player> sortedPlayers = getSortedPlayers();
		
		for(int i=0; i<sortedPlayers.size(); i++){
			if(sortedPlayers.get(i).equals(player))
				return sortedPlayers.get( (i + 1) % sortedPlayers.size());
		}
		return null;
	}
	
	static public StandartCard.Color getRulerColor(){
		if(rulerColor==null){
			Card ruler = ((DeckArea)(ClientController.get().getZone(deckId))).getRulerCard();
			rulerColor = ((StandartCard)ruler).getColor();
		}
		return rulerColor;
	}
	
	public Durak(){
		initActiveNumbers();
	}

	static private boolean activeNumbers[] = {false,false,false,false,false,false,false,false,false};
	static public Integer deckId;
	
	
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
//		boolean answer = false;
//		for(int i=0; i<staticPlayers.size() ; i++){
//			if(staticPlayers.get(i).isMyTurn() && staticPlayers.get( (i + 1) % staticPlayers.size()).equals(player)){
//				answer = true;
//				break;
//			}
//		}
//		return answer;
		
		
		utils.Player attacker = getCurrentAttacker();
		Position.Player attackerPos = attacker.getGlobalPosition();
		
		// create a new instance of players.
		ArrayList<utils.Player> sortedPlayers = getSortedPlayers();
		
		for(int i=0; i<sortedPlayers.size(); i++){
			if(sortedPlayers.get(i).equals(attacker) && sortedPlayers.get( (i + 1) % sortedPlayers.size()).equals(player))
				return true;
		}
		
		return false;
	}
	
	
	
	public utils.Player getAttackedPlayer(){
		for(utils.Player player : players){
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
	
	
	public static utils.Player getCurrentAttacker(){
		for(int i=0; i<staticPlayers.size() ; i++){
			if(staticPlayers.get(i).isMyTurn()){
				return staticPlayers.get(i);
			}
		}
		return null;
	}
	
	public static boolean hasOpenCards(){
		utils.Player me = ClientController.get().getMe();
		
		Public public1 =  (Public)ClientController.get().getZone(Position.Public.BOTMID.getRelativePosition(me.getGlobalPosition()));
		Public public2 =  (Public)ClientController.get().getZone(Position.Public.BOTMIDLEFT.getRelativePosition(me.getGlobalPosition()));
		Public public3 =  (Public)ClientController.get().getZone(Position.Public.BOTMIDRIGHT.getRelativePosition(me.getGlobalPosition()));
		Public public4 =  (Public)ClientController.get().getZone(Position.Public.TOPMID.getRelativePosition(me.getGlobalPosition()));
		Public public5 =  (Public)ClientController.get().getZone(Position.Public.TOPMIDLEFT.getRelativePosition(me.getGlobalPosition()));
		Public public6 =  (Public)ClientController.get().getZone(Position.Public.TOPMIDRIGHT.getRelativePosition(me.getGlobalPosition()));
		
		
		return !(public1.cardsHolding()%2==0 &&
				public2.cardsHolding()%2==0 &&
				public3.cardsHolding()%2==0 &&
				public4.cardsHolding()%2==0 &&
				public5.cardsHolding()%2==0 &&
				public6.cardsHolding()%2==0);
	}

	@Override
	public Integer onRoundEnd() {
		boolean bito=false;
		
		utils.Player me = ClientController.get().getMe();
		
		DeckArea deck = (DeckArea)ClientController.get().getZone(Position.Button.TOPRIGHT.getRelativePosition(me.getGlobalPosition()));
		
		Public public1 =  (Public)ClientController.get().getZone(Position.Public.BOTMID.getRelativePosition(me.getGlobalPosition()));
		Public public2 =  (Public)ClientController.get().getZone(Position.Public.BOTMIDLEFT.getRelativePosition(me.getGlobalPosition()));
		Public public3 =  (Public)ClientController.get().getZone(Position.Public.BOTMIDRIGHT.getRelativePosition(me.getGlobalPosition()));
		Public public4 =  (Public)ClientController.get().getZone(Position.Public.TOPMID.getRelativePosition(me.getGlobalPosition()));
		Public public5 =  (Public)ClientController.get().getZone(Position.Public.TOPMIDLEFT.getRelativePosition(me.getGlobalPosition()));
		Public public6 =  (Public)ClientController.get().getZone(Position.Public.TOPMIDRIGHT.getRelativePosition(me.getGlobalPosition()));
		
		Public junk = (Public)ClientController.get().getZone(Position.Public.RIGHT.getRelativePosition(ClientController.get().getMe().getGlobalPosition()));
		
		Integer answer=-1;

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
			//answer=getNextPlayer(getCurrentAttacker()).getId();
			bito=true;
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
			//answer=getCurrentAttacker().getId();
			bito=false;
			
		}
		for (utils.Player player : getPlayers()){
			if(deck.cardsHolding()==0 && player.cardsHolding()==0)
				winners.add(player);
		}
		
		if(bito){
			answer=getNextPlayer(getCurrentAttacker()).getId();
		}else{
			answer=getCurrentAttacker().getId();
		}
		
		// calculating the number of cards that I need to add in order to reach at least 6 cards in hand.
		int numberOfCardsInHand = ClientController.get().getMe().getCards().size();
		int cardsNeeded = 6 - numberOfCardsInHand;
		
		if(cardsNeeded > 0){
			ClientController.get().requestCard(deckId, cardsNeeded);
		}
		
		initActiveNumbers();
		
		for (utils.Player player : getPlayers()){
			player.setMyTurn(false);
		}
		//ClientController.get().getZone(answer).setGlowColor(glowColor);
		
		
		
		return answer;
	}

	@Override
	protected Queue<Player> setTurns() {
		if(startingPlayer!=null)
			return utils.Turns.clockWise(startingPlayer.getGlobalPosition());
		else
			return utils.Turns.clockWise(Position.Player.BOTTOM);
	}

	@Override
	public int minPlayers() {
		// TODO Auto-generated method stub
		return 4;
	}

	
	Card findRulerCard(ArrayList<Card> deckCards, int numOfPlayers){
		return deckCards.get(numOfPlayers*6);
	}
	
	utils.Player findStartingPlayer(ArrayList<Card> deckCards, Card rulerCard, int numOfPlayers){
		StandartCard ruler = (StandartCard)rulerCard;
		int smallestValue=55;
		int index=-1;
		
		for(int i=0; i<numOfPlayers*6; i++){
			StandartCard sd = (StandartCard)deckCards.get(i);
			if(sd.getColor().equals(ruler.getColor()) && sd.getValue()<smallestValue){
				index=i;
				smallestValue=sd.getValue();
			}
		}
		if(index!=-1)
			return players.get(index % numOfPlayers);
		else
			return null;
	}
	
	@Override
	public void dealCards() {
		int deckSize=deck.getSize();
		int numOfPlayers=players.size();
		ArrayList<Card> deckCards = new ArrayList<Card>();
		
		
//		ArrayList<ArrayList<Card>> playersCards= new ArrayList<ArrayList<Card>>();
//		for (int i=0;i<numOfPlayers;i++){
//			playersCards.add(new ArrayList<Card>());
//		}
//		
//		for(int i=0; i<numOfPlayers; i++){
//			for(int j=0; j<6; j++)
//				playersCards.get(i).add(deck.drawCard());
//		}
		
		
		
		for(Card card : deck.getCards())
			deckCards.add(card);
		
		rulerCard = findRulerCard(deckCards, numOfPlayers);
		startingPlayer = findStartingPlayer(deckCards, rulerCard, numOfPlayers);
		
		//rulerColor = ((StandartCard)rulerCard).getColor();
		
//		for (int i=0;i<players.size();i++){
//			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(playersCards.get(i),players.get(i).getId())));
//		}
		
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(deckCards,Position.Button.TOPRIGHT.getId())));
		
		
		dealCardAnimation(deckId, deckCards, 6);
		
		
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new SetRulerCardAction(rulerCard, Position.Button.TOPRIGHT.getId())));
		
//		for(int i=0 ; i<numOfPlayers * 6 ; i++){
//			ConnectionsManager.getConnectionsManager().sendToAll(new RequestCardMessage(players.get(i % numOfPlayers), deckId, deckCards.get(i)));
//			try {
//				Thread.sleep(400);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
	}

	@Override
	public void setLayouts() {
		
		
		PublicHandler publicArea = new PublicHandler();
		JunkHandler junkHandler = new JunkHandler();
		
		// the public areas.
		droppables.add(new Public(publicArea, Position.Public.BOTMID,DroppableLayout.LayoutType.HEAP));
		droppables.add(new Public(publicArea, Position.Public.BOTMIDLEFT,DroppableLayout.LayoutType.HEAP));
		droppables.add(new Public(publicArea, Position.Public.BOTMIDRIGHT,DroppableLayout.LayoutType.HEAP));
		droppables.add(new Public(publicArea, Position.Public.TOPMID,DroppableLayout.LayoutType.HEAP));
		droppables.add(new Public(publicArea, Position.Public.TOPMIDLEFT,DroppableLayout.LayoutType.HEAP));
		droppables.add(new Public(publicArea, Position.Public.TOPMIDRIGHT,DroppableLayout.LayoutType.HEAP));
		
		// the junk area.
		droppables.add(new Public(junkHandler, Position.Public.RIGHT,DroppableLayout.LayoutType.HEAP));
		
		// the pass button.
		button = new Button(new ButtonHandler(), Position.Button.BOTRIGHT, "");
		buttons.add(button);
		
		
		DeckArea da = new DeckArea(Position.Button.TOPRIGHT);
		droppables.add(da);
		
		deckId = da.getId();
		
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
		return "Durak is a card game that is popular throughout most of the post-Soviet states. The object of the game is to get rid of all one's cards. At the end of the game, the last player with cards in their hand is referred to as the fool (durak).";
	}
	
	@Override
	public void newTurn(utils.Player player) {
		for(utils.Player p : staticPlayers)
			p.setTextColor(Color.BLACK);
		
		player.setTextColor(Color.RED);
		getNextPlayer(player).setTextColor(Color.BLUE);
		
		if(player.equals(ClientController.get().getMe())){
			button.setText("Bito");
		}else if(getNextPlayer(player).equals(ClientController.get().getMe())){
			button.setText("Take");
		}else{
			button.setText("");
		}

	}

}
