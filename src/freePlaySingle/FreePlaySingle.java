package freePlaySingle;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import java.util.Queue;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;

import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.controller.ClientController;
import client.gui.entities.Droppable;

import utils.DeckArea;
import utils.Card;
import utils.Deck;
import utils.Point;
import utils.Position;
import utils.Position.Player;
import utils.droppableLayouts.DroppableLayout;
import utils.Public;
import logic.client.Game;

public class FreePlaySingle extends Game{

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
	public int cardsForEachPlayer() {
		// TODO Auto-generated method stub
		return 0;
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
	public void setLayouts(ArrayList<Droppable> publics) {
		droppables.add(new DeckArea(Position.Button.TOPRIGHT));
		droppables.add(new Public(new PublicHandler(), Position.Public.MID,DroppableLayout.LayoutType.NONE , new Point(65,65)));		
	}
	
//	@Override
//	public void getLayouts(ArrayList<Droppable> droppables) {
//		droppables.add(new DeckArea(Position.Button.BOTLEFT));
//		//(new CardHandler(), true,));
//		droppables.add(new Public(new PublicHandler(), Position.Public.MID));
//	}

	@Override
	public String toString() {
		return "free play single player";
	}


	@Override
	public Integer onRoundEnd() {
		return null;
	}

	@Override
	public utils.Player getPlayerInstance(PlayerInfo playerInfo,
			Player position, int uniqueId) {
		return new utils.Player(playerInfo, position, uniqueId, new PlayerHandler(), DroppableLayout.LayoutType.LINE);
	}

}
