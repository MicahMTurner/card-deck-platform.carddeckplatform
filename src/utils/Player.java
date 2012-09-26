package utils;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Shape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import utils.droppableLayouts.DroppableLayout;
import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.controller.ClientController;
import client.gui.entities.Droppable;



/**
 * this class represents a player in the game
 * @author Yoav
 *
 */
public class Player extends Droppable implements  Comparable<Player>{
	private PlayerEventsHandler handler;	
	private ArrayList<Card> hand;
	boolean myTurn;
	private PlayerInfo playerInfo;
	private Position.Player globalPosition;
	private transient Paint textPaint=null;
	
	public Paint getTextPaint() {
		if(textPaint==null)
			textPaint = new Paint();
		return textPaint;
	}
	
	public void setTextColor(int color){
		getTextPaint().setColor(color);
	}
	
	/**
	 * player constructor
	 * @param playerInfo the info of the player
	 * @param globalPosition position of the player in the table
	 * @param uniqueId player's id
	 * @param handler player's handler
	 * @param layoutType player's layout
	 * @see DroppableLayout
	 */
	public Player(PlayerInfo playerInfo,Position.Player globalPosition,int uniqueId, PlayerEventsHandler handler,DroppableLayout.LayoutType layoutType) {
		super(uniqueId,Position.Player.BOTTOM, layoutType,handler);
		this.playerInfo=playerInfo;		
		this.globalPosition=globalPosition;
		this.position=Position.Player.BOTTOM;	
		this.handler=handler;
		this.hand=new ArrayList<Card>();
		this.myTurn=false;			
		this.image = "playerarea";
	}
	/**
	 * the position of the player around the table
	 * @return global position of the player around the table
	 */
	public Position.Player getGlobalPosition() {
		return globalPosition;
	}
	
	/**
	 * get the player's current position
	 * @return player's current position
	 */
	public Position.Player getPosition() {
		return (Position.Player)position;
	}
	/**
	 * get player's user name
	 * @return player's user name
	 */
	public String getUserName() {
		return playerInfo.getUsername();
	}
	/**
	 * get player's id
	 * @return player's id
	 */
	public int getId() {
		return id;
	}
	
	
	private boolean removeCard(Card card) {
		boolean answer=handler.onCardRemoved(this, card);
		if (answer){
			hand.remove(card);
		}
		return answer;
		
		
	}
	/**
	 * set player's global position around the table
	 * @param globalPosition new player's global position
	 */
	public void setGlobalPosition(Position.Player globalPosition) {
		this.globalPosition = globalPosition;
		
	}
	/**
	 * checks if this is the player's turn
	 * @return true - if it is the player's turn, false - OW
	 */
	public boolean isMyTurn() {
		return myTurn;
	}
	/**
	 * start player's turn, calling onMyTurn function in handler
	 * @see PlayerEventsHandler
	 */
	public void startTurn(){
		myTurn=true;
		ClientController.get().enableUi();
		handler.onMyTurn(this);
	}
	/**
	 * set the player's turn
	 * @param myTurn defines what parameter to set the turn variable to
	 */
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	/**
	 * end player's turn, calling onTurnEnd function in handler.</br>
	 * does nothing if it isn't the player's turn
	 * @see PlayerEventsHandler
	 */
	public void endTurn(){
		if (myTurn!=false){
			
			handler.onTurnEnd(this);
		
			myTurn=false;
			ClientController.sendAPI().endTurn();
		}
	}
	/**
	 * deal card to this player without calling the handler's function
	 */
	@Override
	public void deltCard(Card card) {
		card.setOwner((Position.Player)position);
		hand.add(card);
		handler.onCardAdded(this, this, card);
		card.setLocation(getX(), getY());
		super.rearrange(0);
	}
	/**
	 * get how many cards are in player's hand
	 * @return number of cards the player is holding
	 */
	@Override
	public int cardsHolding() {		
		return hand.size();
	}

	/**
	 * checks if player has no cards
	 * @return true if player has no cards in hand, false OW
	 */
	@Override
	public boolean isEmpty() {
		return hand.isEmpty();
	}

	/**
	 * clears player's hand (delete all cards)
	 */
	@Override
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

			position=newPos;
			super.rearrange(0);

		}		
	}
	/**
	 * happens when a card is being added to this player, calling onCardAdded function in handler
	 * @see PlayerEventsHandler
	 */
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

	/**
	 * happens when a card is being removed to this  player
	 */
	@Override
	public boolean onCardRemoved(Player player, Card card) {
		return removeCard(card);
	}


	@Override
	public List<Card> getMyCards() {		
		return hand;
	}


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
	/**
	 * calls when a round ended, calls handler's onRoundEnd function
	 * @see PlayerEventsHandler
	 */
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

	@Override
	public void putCardOnTop(Card card){
	
	}
	
	@Override
	public void draw(Canvas canvas, Context context) {
		super.draw(canvas, context);
		
		try {
			getTextPaint().setTextSize(20);
			float x=0,y=0;
			Shape s = getShape();
			x = s.getMinX();
			y = s.getMinY();
			canvas.drawText(getUserName(), (int)x, (int)y, getTextPaint());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
