package utils;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import client.controller.ClientController;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;




public class Player extends Droppable implements  Comparable<Player>{
	private PlayerEventsHandler handler;
	private String userName;
	private ArrayList<Card> hand;
	boolean myTurn;
	//private Point coord;
	private Position.Player position;
	private Position.Player globalPosition;
	
	
	
	
	
	public Player(String userName,Position.Player globalPosition, PlayerEventsHandler handler) {
		super(globalPosition.getId(),globalPosition.sitMe(globalPosition));
		this.userName=userName;
		this.globalPosition=globalPosition;
		this.position=globalPosition.sitMe(globalPosition);		
		this.handler=handler;
		this.hand=new ArrayList<Card>();
		this.myTurn=false;
		//addObserver(ClientController.get());
	}
	
	public Position.Player getGlobalPosition() {
		return globalPosition;
	}
	public PlayerEventsHandler getHandler() {
		return handler;
	}
	public Position.Player getPosition() {
		return position;
	}
	public String getUserName() {
		return userName;
	}
	public int getId() {
		return id;
	}
	public void addCard(Card card) {
		card.setOwner(position);		
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
		card.setOwner(position);
		hand.add(card);			
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
		return 30;
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
//	@Override
//	public Point getCoord() {		
//		return coord;
//	}
	@Override
	public ArrayList<Card> getCards() {		
		return hand;
	}
//	@Override
//	public int getX() {
//		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getX();		
//	}
//
//	@Override
//	public int getY() {
//		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getY();		
//	}
	
}
