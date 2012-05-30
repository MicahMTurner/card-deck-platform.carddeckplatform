package utils;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import communication.link.ServerConnection;
import communication.messages.SwapRequestMessage;



import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.controller.ClientController;
import client.controller.LivePosition;
import client.gui.entities.Droppable;




public class Player extends Droppable implements  Comparable<Player>{
	private PlayerEventsHandler handler;	
	private ArrayList<Card> hand;
	boolean myTurn;
	private PlayerInfo playerInfo;
	private Position.Player globalPosition;
	
	
	
	
	
	public Player(PlayerInfo playerInfo,Position.Player globalPosition,int uniqueId, PlayerEventsHandler handler) {
		super(uniqueId,Position.Player.BOTTOM);
		this.playerInfo=playerInfo;		
		this.globalPosition=globalPosition;
		this.position=Position.Player.BOTTOM;	
		this.handler=handler;
		this.hand=new ArrayList<Card>();
		this.myTurn=false;	
		
	}
	
	public Position.Player getGlobalPosition() {
		return globalPosition;
	}
	public PlayerEventsHandler getHandler() {
		return handler;
	}
	public Position.Player getPosition() {
		return (Position.Player)position;
	}
	public String getUserName() {
		return playerInfo.getUsername();
	}
	public int getId() {
		return id;
	}
	public void addCard(Card card) {
		card.setOwner((Position.Player)position);		
		hand.add(card);		
		handler.onCardAdded(this, card);		
	}
	
//	public void setCoord(Point coord) {
//		this.coord = coord;
//	}
	private void removeCard(Card card) {
		hand.remove(card);
		handler.onCardRemoved(this, card);
		
	}
	
	public void setGlobalPosition(Position.Player globalPosition) {
		this.globalPosition = globalPosition;
		
	}
	
	public boolean isMyTurn() {
		return myTurn;
	}
	public void startTurn(){
		myTurn=true;		
		ClientController.get().enableUi();
	}
	public void endTurn(){
		myTurn=false;		
		ClientController.sendAPI().endTurn(globalPosition);
	}
	public void deltCard(Card card) {
		card.setOwner((Position.Player)position);
		hand.add(card);	
		card.setLocation(getX(), getY());
	}
	public int cardsHolding() {		
		return hand.size();
	}

	
	public boolean isEmpty() {
		return hand.isEmpty();
	}

	
	public void clear() {
		hand.clear();
		
	}
	@Override
	public int compareTo(Player another) {
		return this.globalPosition.compareTo(another.getGlobalPosition());
	}
	
	public void setRelativePosition(utils.Position.Player devicePlayerGlobalPos) {
		Position.Player newPos=this.globalPosition.getRelativePosition(devicePlayerGlobalPos);;
		
		if (!(this.globalPosition.equals(devicePlayerGlobalPos))){			
			for (Card card : hand){
				CardTransformation.get().transform(card,position,newPos);
			}
			position=newPos;
		}		
	}
	@Override
	public int sensitivityRadius() {		
		return 50;
	}
	@Override
	public void addCard(Player player, Card card) {
		addCard(card);
	}
	@Override
	public void removeCard(Player player, Card card) {
		removeCard(card);
	}
	@Override
	public void draw(Canvas canvas, Context context) {
		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), 0x7f02002e),getX()-28,getY()-27,null);
		
	}

	@Override
	public ArrayList<Card> getCards() {		
		return hand;
	}

//	public void setAzimute(double newAzimute) {
//		playerInfo.setAzimute(globalPosition,newAzimute);
//	}
	public Double getAzimute() {
		return playerInfo.getAzimute();
	}

}
