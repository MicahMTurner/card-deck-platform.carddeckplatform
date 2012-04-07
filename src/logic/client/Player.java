package logic.client;

import java.util.ArrayList;

import logic.card.Card;

public class Player implements Comparable<Player>{
	
	private String username;
	private String id;
	private boolean ready;
	private ArrayList<Card> hand;
	private boolean playing=true; //still playing or lost.
	
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
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public String getUsername() {
		return username;
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
		hand=new ArrayList<Card>();
	}
	
	@Override
	public int compareTo(Player oPlayer) {
		return this.username.compareTo(oPlayer.username);
	}
}
