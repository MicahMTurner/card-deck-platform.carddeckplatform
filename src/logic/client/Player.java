package logic.client;

import java.io.Serializable;
import java.util.ArrayList;

import logic.card.CardLogic;

public class Player implements Comparable<Player> , Serializable{
	public enum Position{
		TOP,BOTTOM,LEFT,RIGHT;
	}
	
	private String username;
	private String id;
	private boolean ready;
	private ArrayList<CardLogic> hand;
	private boolean playing=true; //still playing or lost.
	private Position position;
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	/**
	 * returns how many cards player is currently holding
	 * @return
	 */
	public int cardsHolding(){
		return hand.size();
	}
	
	public void setReady(boolean isReady){
		this.ready=isReady;
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public ArrayList<CardLogic> getHand() {
		return hand;
	}
	
	public String getUsername() {
		return username;
	}
	public void addCard(CardLogic card){
		hand.add(card);
	}
	

	//public void setUsername(String username) {
	//	this.username = username;
	//}

	public String getId() {
		return id;
	}

	public Player(String username, String id){
		this.username = username;
		this.id=id;
		hand=new ArrayList<CardLogic>();
	}
	
	@Override
	public int compareTo(Player oPlayer) {
		return this.username.compareTo(oPlayer.username);
	}
}
