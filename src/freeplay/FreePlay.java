package freeplay;

import freeplay.customization.FreePlayProfile;
import handlers.Handler;
import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import android.content.Context;
import android.content.SharedPreferences;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;

import carddeckplatform.game.CarddeckplatformActivity;
import carddeckplatform.game.GameActivity;
import carddeckplatform.game.StaticFunctions;
import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.controller.ClientController;
import client.gui.entities.Droppable;

import utils.Button;
import utils.DeckArea;
import utils.Card;
import utils.Deck;
import utils.GamePrefs;
import utils.Point;
import utils.Position;
import utils.Position.Player;
import utils.droppableLayouts.DroppableLayout;
import utils.Public;
import logic.client.Game;

public class FreePlay extends Game{

	private boolean publicCardsVisible;
	
	@Override
	public Deck getDeck() {
		return new Deck(new CardHandler(), true);
	}

	@Override
	public Queue<Player> setTurns() {
		return null;
	}

	@Override
	public int minPlayers() {
		return 1;
	}


	@Override
	public void dealCards() {
		int size=deck.getSize();
		ArrayList<Card>cards=new ArrayList<Card>();
		for (int i=0;i<size;i++){
			cards.add(deck.drawCard());
		}
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(cards,Position.Button.TOPRIGHT.getId())));
		
		dealCardAnimation(Position.Button.TOPRIGHT.getId(), cards, freePlayProfile.getCardsToDeal());
	}
	
	
	
	@Override
	public void setLayouts() {
		droppables.add(new DeckArea(Position.Button.TOPRIGHT));		
		for(Droppable d : freePlayProfile.getPublics()){
			if(d!=null)
				droppables.add(d);
		}
	}

	@Override
	public String toString() {
		return "free play";
	}


	@Override
	public Integer onRoundEnd() {
		return null;
	}

	@Override
	public utils.Player getPlayerInstance(PlayerInfo playerInfo,
			Player position, int uniqueId) {
		
		PlayerEventsHandler playerHandler = freePlayProfile.getPlayerHandlers().get(position);
		
		utils.Player p = new utils.Player(playerInfo, position, uniqueId, (PlayerEventsHandler)playerHandler, DroppableLayout.LayoutType.LINE);
		//playerHandler.setAttachedPlayer(p);
		
		return p;
	}
	
//	@Override
//	public String getPrefsName(){
//		return "freeplay";
//	}
	
	
	public void applyProfile(FreePlayProfile freePlayProfile){
		this.freePlayProfile = freePlayProfile;
	}

	@Override
	public String instructions() {
		return "";
	}
//	public void loadProfile(){
//		try {
//			SharedPreferences preferences = CarddeckplatformActivity.getContext().getSharedPreferences(getPrefsName(), Context.MODE_PRIVATE);		
//			String numberOfPlayers = preferences.getString("playersNum", String.valueOf(minPlayers()));		
//			numberOfParticipants = Integer.parseInt(numberOfPlayers);
//			
//			publicLayout = preferences.getString("publicLayout", "free");
//			playerCardsVisible = preferences.getBoolean("playerCardsVisible" , false);
//			publicCardsVisible = preferences.getBoolean("publicCardsVisible" , true);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
//		
//		
//		
////		String somePreference = preferences.getString("somePreference", defaultValue);
//	}
//	
//	
//
//	public void applyReceivedProfile(FreePlayProfile gamePrefs){
//		this.publicLayout = new String(gamePrefs.getPublicLayout());
//		this.playerCardsVisible = gamePrefs.getPlayerCardsVisible();
//		this.publicCardsVisible = gamePrefs.getPublicCardsVisible();
//	}
//	
//	@Override
//	public GamePrefs getPrefs() {
//		// TODO Auto-generated method stub
//		return new GamePrefs(publicLayout, playerCardsVisible, publicCardsVisible);
//	}
	
	@Override
	public Stack<Position.Player> getPositions(){
		Stack<Position.Player> availablePositions = super.getPositions();
		Stack<Position.Player> toRemove = new Stack<Position.Player>();
		
		Set<Player> players = freePlayProfile.getPlayerHandlers().keySet();
		
		for(Position.Player pp :  availablePositions){
			if(!players.contains(pp)){
				toRemove.add(pp);
			}
		}
		
		// remove if there is unnecessary items
		if(toRemove.size()>0)
			availablePositions.removeAll(toRemove);
		
		return availablePositions;
		
	}

	@Override
	public int maxPlayers() {
		// TODO Auto-generated method stub
		return 4;
	}


}
