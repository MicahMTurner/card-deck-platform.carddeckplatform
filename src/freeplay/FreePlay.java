package freeplay;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import java.util.Queue;

import android.content.Context;
import android.content.SharedPreferences;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;

import carddeckplatform.game.CarddeckplatformActivity;
import carddeckplatform.game.GameActivity;
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

	private String publicLayout;
	private boolean playerCardsVisible;
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
	}
	
	
	
	@Override
	public void setLayouts() {
		PublicHandler publicArea = new PublicHandler();	
		publicArea.setPublicCardsVisible(publicCardsVisible);
		droppables.add(new DeckArea(Position.Button.TOPRIGHT));
		if(publicLayout.equals("free")){
			droppables.add(new Public(publicArea, Position.Public.MID,DroppableLayout.LayoutType.NONE , new Point(65,65)));
		} else if(publicLayout.equals("five")){
			droppables.add(new Public(publicArea, Position.Public.LEFT,DroppableLayout.LayoutType.HEAP , new Point(10,13)));
			droppables.add(new Public(publicArea, Position.Public.MIDLEFT,DroppableLayout.LayoutType.HEAP , new Point(10,13)));
			droppables.add(new Public(publicArea, Position.Public.MID,DroppableLayout.LayoutType.HEAP , new Point(10,13)));
			droppables.add(new Public(publicArea, Position.Public.MIDRIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,13)));
			droppables.add(new Public(publicArea, Position.Public.RIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,13)));
		} else if(publicLayout.equals("all")){
			droppables.add(new Public(publicArea, Position.Public.BOT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.BOTMID,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.BOTMIDLEFT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.BOTMIDRIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.LEFT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.MID,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.MIDLEFT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.MIDRIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.RIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.TOP,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.TOPMID,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.TOPMIDLEFT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.TOPMIDRIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
		} else if(publicLayout.equals("cross")){
			droppables.add(new Public(publicArea, Position.Public.MIDLEFT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.MIDRIGHT,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.TOPMID,DroppableLayout.LayoutType.HEAP , new Point(10,11)));
			droppables.add(new Public(publicArea, Position.Public.BOTMID,DroppableLayout.LayoutType.HEAP , new Point(10,11)));			
		}		
	}
	
//	@Override
//	public void getLayouts(ArrayList<Droppable> droppables) {
//		droppables.add(new DeckArea(Position.Button.BOTLEFT));
//		//(new CardHandler(), true,));
//		droppables.add(new Public(new PublicHandler(), Position.Public.MID));
//	}

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
		
		PlayerHandler playerHandler = new PlayerHandler();
		
		utils.Player p = new utils.Player(playerInfo, position, uniqueId, playerHandler, DroppableLayout.LayoutType.LINE);
		playerHandler.setAttachedPlayer(p);
		
		return p;
	}
	
	@Override
	public String getPrefsName(){
		return "freeplay";
	}
	
	@Override
	public void loadPrefs(){
		try {
			SharedPreferences preferences = CarddeckplatformActivity.getContext().getSharedPreferences(getPrefsName(), Context.MODE_PRIVATE);		
			String numberOfPlayers = preferences.getString("playersNum", String.valueOf(minPlayers()));		
			numberOfParticipants = Integer.parseInt(numberOfPlayers);
			
			publicLayout = preferences.getString("publicLayout", "free");
			playerCardsVisible = preferences.getBoolean("playerCardsVisible" , false);
			publicCardsVisible = preferences.getBoolean("publicCardsVisible" , true);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		
//		String somePreference = preferences.getString("somePreference", defaultValue);
	}
	
	
	@Override
	public void applyReceivedPrefs(GamePrefs gamePrefs){
		this.publicLayout = new String(gamePrefs.getPublicLayout());
		this.playerCardsVisible = gamePrefs.getPlayerCardsVisible();
		this.publicCardsVisible = gamePrefs.getPublicCardsVisible();
	}
	
	@Override
	public GamePrefs getPrefs() {
		// TODO Auto-generated method stub
		return new GamePrefs(publicLayout, playerCardsVisible, publicCardsVisible);
	}

	@Override
	public String instructions() {
		// TODO Auto-generated method stub
		return null;
	}

}
