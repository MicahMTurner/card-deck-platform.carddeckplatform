package blackJack;

import java.util.ArrayList;

import blackJack.droppables.DeckAreaLogic;
import blackJack.droppables.MyPlayerAreaLogic;
import blackJack.droppables.PlayerAreaLogic;
import blackJack.droppables.PressButtonLogic;
import blackJack.gui.DeckArea;
import blackJack.gui.PlayerArea;
import blackJack.gui.PressButton;

import client.controller.actions.DealCardAction;

import communication.messages.Message;
import communication.server.ConnectionsManager;

import android.content.Context;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;
import logic.card.CardLogic;
import logic.client.Deck;
import logic.client.Game;
import logic.client.GamePrefs;
import logic.client.LogicDroppable;
import logic.client.LogicDroppable.Type;
import logic.client.Player;
import logic.client.Player.Position;
import logic.host.Host;

public class BlackJack extends Game{
	BlackJackPrefs prefs=new BlackJackPrefs();
	@Override
	protected void setNewTools() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTurns() {
		super.turnsQueue.add(Player.Position.BOTTOM);
		super.turnsQueue.add(Player.Position.TOP);
		
	}

	@Override
	public GamePrefs getPrefs() {
		// TODO Auto-generated method stub
		return prefs;
	}


	@Override
	public String toString() {		
		return "blackJack";
	}

	@Override
	public void buildLayout(Context context, TableView tv, Position position) {
		int width = GameStatus.screenWidth;
		int height = GameStatus.screenHeight;
		if (position.equals(Player.Position.BOTTOM)){
			droppables.add(new MyPlayerAreaLogic(1,LogicDroppable.Type.PLAYER));
			droppables.add(new PlayerAreaLogic(2,LogicDroppable.Type.PLAYER));
			droppables.add(new DeckAreaLogic(3,LogicDroppable.Type.PUBLIC));
			
			tv.addDroppable(new DeckArea(context, 60, height/2, droppables.get(2)));
		}
		else if (position.equals(Player.Position.TOP)){
			droppables.add(new MyPlayerAreaLogic(2,LogicDroppable.Type.PLAYER));
			droppables.add(new PlayerAreaLogic(1,LogicDroppable.Type.PLAYER));
			droppables.add(new DeckAreaLogic(3,LogicDroppable.Type.PUBLIC));
			
			tv.addDroppable(new DeckArea(context, width-100, height/2, droppables.get(2)));
		}
		tv.addDroppable(new PressButton(context, 70, height-100, new PressButtonLogic(4, Type.PUBLIC)));
		tv.addDroppable(new PlayerArea(context,  width/2, height-100, droppables.get(0))); // places my area in the gui.
		tv.addDroppable(new PlayerArea(context,  width/2, 60, droppables.get(1))); // places opponent area in the gui.
		
		
	}

	@Override
	public void dealCards(Deck deck, ArrayList<Player> players) {
		deck.shuffle(2);
		for (int i=0;i<4;i++){//
			CardLogic card=deck.drawCard();
			Player player=players.get(i%Host.players.size());
			card.setOwner(player.getUsername());
			player.getHand().add(card);			
		}	
		ArrayList<CardLogic> newdeck=new ArrayList<CardLogic>(); 
		for (CardLogic card : deck.getCards()){
			newdeck.add(card);
		}
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(newdeck,3)));
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(0).getHand(),2)));
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(1).getHand(),1)));
		
	}
	

}
