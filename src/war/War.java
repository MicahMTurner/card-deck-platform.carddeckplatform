package war;

import java.util.ArrayList;

import communication.messages.Message;
import communication.server.ConnectionsManager;

import client.controller.actions.DealCardAction;
import client.gui.entities.Droppable;
import android.content.Context;
import war.droppables.MyPlayerAreaLogic;
import war.droppables.PlayerAreaLogic;
import war.droppables.PublicAreaLogic;
import war.gui.PlayerArea;
import war.gui.PublicPlace;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;
import logic.builtIn.defaultCards.Club;
import logic.builtIn.defaultCards.Diamond;
import logic.builtIn.defaultCards.Heart;
import logic.builtIn.defaultCards.Spade;
import logic.card.CardLogic;
import logic.client.Deck;
import logic.client.Game;
import logic.client.LogicDroppable;
import logic.client.Player;
import logic.host.Host;



public class War extends Game{
	private WarPrefs prefs=new WarPrefs();
	private static boolean tie=false;	
	public War() {
		// TODO Auto-generated constructor stub
		
	}

	public WarPrefs getPrefs() {
		return prefs;
	}
	@Override
	protected void setNewTools() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void buildLayout(Context context, TableView tv, Player.Position position) {
		int width = GameStatus.screenWidth;
		int height = GameStatus.screenHeight;
		
		if (position.equals(Player.Position.BOTTOM)){
			droppables.add(new PublicAreaLogic(1,LogicDroppable.Type.PUBLIC));
			droppables.add(new PublicAreaLogic(2,LogicDroppable.Type.PUBLIC));
			droppables.add(new PlayerAreaLogic(3,LogicDroppable.Type.PLAYER));
			droppables.add(new MyPlayerAreaLogic(4,LogicDroppable.Type.PLAYER));
		}
		else if (position.equals(Player.Position.TOP)){
			droppables.add(new PublicAreaLogic(2,LogicDroppable.Type.PUBLIC));
			droppables.add(new PublicAreaLogic(1,LogicDroppable.Type.PUBLIC));
			droppables.add(new PlayerAreaLogic(4,LogicDroppable.Type.PLAYER));
			droppables.add(new MyPlayerAreaLogic(3,LogicDroppable.Type.PLAYER));
		}
		
		tv.addDroppable(new PublicPlace(context, width/3, height/2, droppables.get(0)));
		tv.addDroppable(new PublicPlace(context, 2*(width/3), height/2,droppables.get(1)));
		tv.addDroppable(new PlayerArea(context, width/2, 60, droppables.get(2)));
		tv.addDroppable(new PlayerArea(context, width/2, height-100, droppables.get(3)));
		
		
		
	}
	@Override
	public String toString() {
		return "war";
	}
	@Override
	public void setTurns() {		
		super.turnsQueue.add(Player.Position.BOTTOM);
		super.turnsQueue.add(Player.Position.TOP);
		
	}
	public static void setTie(boolean isTie) {
		tie=isTie;
		
	}
	public static boolean isTie() {
		return tie;
	}
	@Override
	public void dealCards(Deck deck, ArrayList<Player> players) {
		deck.shuffle(2);
		int size=deck.getSize();
		//for (int i=0;i<2;i++){
		//	CardLogic card=deck.drawCard();
		//	Player player=players.get(i%Host.players.size());
		//	card.setOwner(player.getUsername());
		//	player.getHand().add(card);			
		//}

		//make presentation deal
		Player player1=players.get(0);
		Player player2=players.get(1);
		
		player1.addCard(new Spade(12, 65));
		player1.addCard(new Spade(5, 64));
		player1.addCard(new Diamond(5, 63));
		player1.addCard(new Spade(3, 62));
		player1.addCard(new Club(2, 61));
		player1.addCard(new Diamond(6, 60));
		
		player2.addCard(new Heart(9,71));
		player2.addCard(new Heart(3,70));
		player2.addCard(new Club(5,69));
		player2.addCard(new Spade(7,68));
		player2.addCard(new Spade(8,67));
		player2.addCard(new Spade(4,66));
		
		
		
		
		
		
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(0).getHand(),4)));
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(1).getHand(),3)));
		
		
		
	}
}
