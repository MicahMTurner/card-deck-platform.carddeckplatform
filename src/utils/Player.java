package utils;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import communication.link.ServerConnection;
import communication.messages.SwapRequestMessage;



import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import utils.droppableLayouts.DroppableLayout;
import utils.droppableLayouts.line.BottomLineLayout;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import carddeckplatform.game.BitmapHolder;
import carddeckplatform.game.R;
import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.controller.ClientController;
import client.controller.LivePosition;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;




public class Player extends Droppable implements  Comparable<Player>{
	private PlayerEventsHandler handler;	
	private ArrayList<Card> hand;
	boolean myTurn;
	private PlayerInfo playerInfo;
	private Position.Player globalPosition;
	
	
	
	
	
	public Player(PlayerInfo playerInfo,Position.Player globalPosition,int uniqueId, PlayerEventsHandler handler,DroppableLayout.LayoutType layoutType) {
		super(uniqueId,Position.Player.BOTTOM, layoutType);
		this.playerInfo=playerInfo;		
		this.globalPosition=globalPosition;
		this.position=Position.Player.BOTTOM;	
		this.handler=handler;
		this.hand=new ArrayList<Card>();
		this.myTurn=false;	
		
		this.image = "playerarea";
		
		//this.droppableLayout = new BottomLineLayout(this);
		//BitmapHolder.get().scaleBitmap(image, this.scale);
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
	
	
//	public void setCoord(Point coord) {
//		this.coord = coord;
//	}
	private boolean removeCard(Card card) {
		boolean answer=handler.onCardRemoved(this, card);
		if (answer){
			hand.remove(card);
		}
		return answer;
		
		
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
		handler.onMyTurn(this);
	}
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	public void endTurn(){
		if (myTurn!=false){
			
			handler.onTurnEnd(this);
		
			myTurn=false;
			ClientController.sendAPI().endTurn(id);
		}
	}
	public void deltCard(Card card) {
		card.setOwner((Position.Player)position);
		hand.add(card);
		handler.onCardAdded(this, this, card);
		card.setLocation(getX(), getY());
		super.rearrange(0);
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
//			for (Card card : hand){
//				CardTransformation.get().transform(card,position,newPos);
//			}
			position=newPos;
			super.rearrange(0);
			
			//TODO:CHANGE THIS!!!!!
			//this.droppableLayout = new BottomLineLayout(this);
		}		
	}
	
	@Override
	public boolean onCardAdded(Player player, Card card) {
		if(!getCards().contains(card))	// if from some reason the card is already in the the player area.
			hand.add(card);
		boolean answer=handler.onCardAdded(this, player , card);
		if (answer || player==null){
			card.setOwner((Position.Player)position);		
			
		}else{
			hand.remove(card);
		}
		return answer;
	}
//	public boolean addCard(Card card) {
//		
//		if (answer){
//			card.setOwner((Position.Player)position);		
//			hand.add(card);
//		}
//		return answer; 		
//	}
	@Override
	public boolean onCardRemoved(Player player, Card card) {
		return removeCard(card);
	}
//	@Override
//	public void draw(Canvas canvas, Context context) {
//		Bitmap img = BitmapHolder.get().getBitmap(image);
//		canvas.drawBitmap(img,getX()-img.getWidth() / 2,getY()-img.getHeight() / 2,null);
//		
//	}

	@Override
	public List<Card> getMyCards() {		
		return hand;
	}

//	public void setAzimute(double newAzimute) {
//		playerInfo.setAzimute(globalPosition,newAzimute);
//	}
	public Double getAzimute() {
		return playerInfo.getAzimute();
	}
	
	@Override
	public Point getScale(){
		if(position==Position.Player.LEFT || position==Position.Player.RIGHT){
			return (new Point(scale.getY() , scale.getX()));
		}
		else
			return scale;
	}

	@Override
	public void onRoundEnd(Player player) {
		handler.onRoundEnd(this);
	}

	@Override
	public Card peek() {
		return hand.get(0);
	}

	@Override
	public void AddInPlace(Card card,int place) {
		this.hand.add(place, card);
		
	}

}
