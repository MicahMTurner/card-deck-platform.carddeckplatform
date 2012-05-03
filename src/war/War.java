package war;

import java.util.ArrayList;
import java.util.Queue;

import client.gui.entities.Droppable;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;


import utils.Card;
import utils.Player;
import utils.Position;
import utils.Public;

import logic.client.Game;




public class War extends Game{
	static public boolean tie=false;
	@Override
	protected void setNewTools() {}

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
		int size=cards.getSize();
		for (int i=0;i<size;i++){
			Card card=cards.drawCard();
			Player player=players.get(i%players.size());
			card.setOwner(player.getUsername());
			player.getCards().add(card);
			
		}
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(0).getCards(),false,false,-1,players.get(0).getMyId())));
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(1).getCards(),false,false,-1,players.get(1).getMyId())));
		
	}

	@Override
	public void getLayouts(ArrayList<Public> publics){//,ArrayList<Button>buttons) {
		publics.add(new Public(new PublicHandler(),Position.Public.MIDLEFT));
		publics.add(new Public(new PublicHandler(),Position.Public.MIDRIGHT));
	}

	@Override
	protected Player createPlayer(String userName,
			utils.Position.Player position) {
		
		return new Player(userName,position,new PlayerHandler());
	}
	
}
	/*
	private static boolean tie=false;	
	public War() {		
		
	}
	
	@Override
	protected void setNewTools() {		
		
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
	public void dealCards(AbstractDeck deck, ArrayList<Player> players) {
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
		CardLogic card;
		
		//card=new Diamond(6, 73);
		//card.setOwner(player1.getUsername());
		//player1.addCard(card);
		card=new Spade(14, 81);
		card.setOwner(player1.getUsername());
		player1.addCard(card);
		card=new Spade(14, 80);
		card.setOwner(player1.getUsername());
		player1.addCard(card);
		card=new Spade(12, 65);
		card.setOwner(player1.getUsername());
		player1.addCard(card);
		card=new Spade(5, 64);
		card.setOwner(player1.getUsername());
		player1.addCard(card);
		card=new Diamond(12, 63);
		card.setOwner(player1.getUsername());
		player1.addCard(card);
		card=new Spade(5, 62);
		card.setOwner(player1.getUsername());
		player1.addCard(card);
		card=new Club(5, 61);
		card.setOwner(player1.getUsername());
		player1.addCard(card);
		card=new Diamond(3, 60);
		card.setOwner(player1.getUsername());
		player1.addCard(card);

		
		//card=new Spade(4,72);
		//card.setOwner(player2.getUsername());
		//player2.addCard(card);		
		card=new Heart(3,71);
		card.setOwner(player2.getUsername());
		player2.addCard(card);
		card=new Heart(2,70);
		card.setOwner(player2.getUsername());
		player2.addCard(card);
		card=new Club(9,69);
		card.setOwner(player2.getUsername());
		player2.addCard(card);
		card=new Spade(5,68);
		card.setOwner(player2.getUsername());
		player2.addCard(card);
		card=new Spade(5,67);
		card.setOwner(player2.getUsername());
		player2.addCard(card);
		card=new Spade(3,66);
		card.setOwner(player2.getUsername());
		player2.addCard(card);		
		
		
		
		
		
		
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(0).getHand(),4)));
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(1).getHand(),3)));
		
		
		
	}
}
*/