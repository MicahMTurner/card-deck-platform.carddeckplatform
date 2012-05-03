package utils;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import client.controller.ClientController;
import client.gui.entities.Droppable;

public class Player extends Droppable implements Comparable<Player>{
	
	PlayerEventsHandler handler;
	
	private String username;

	
	private Position.Player position;
	
	public Player(String username,Position.Player position,PlayerEventsHandler handler) {
		super(null);
		this.username=username;
		this.position=position;
		this.handler=handler;		
	}
	@Override
	public int sensitivityRadius() {		
		return 30;
	}
	@Override
	public void addCard(Player player,Card card){
		cards.add(card);
		handler.onCardAdded(this, card);
	}
	public void removeCard(Player player,Card card){
		cards.remove(card);
		handler.onCardRemoved(this, card);
		ArrayList<Card>cards=new ArrayList<Card>();
		cards.add(card);
		ClientController.sendAPI().cardRemoved(cards, username);
	}
	public void endTurn(){		
		handler.onTurnEnd(this);
		ClientController.sendAPI().endTurn(position);
	}
	public void roundEnded(){
		handler.onRoundEnd(this);		
	}
	
	public Position.Player getPosition() {
		return position;
	}
	public void setPosition(Position.Player position) {
		this.position = position;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public int compareTo(Player oPlayer) {
		return this.position.compareTo(oPlayer.position);
	}
	@Override
	public int getX() {		
		return position.getX();
	}
	@Override
	public int getY() {	
		return position.getY();
	}

}
