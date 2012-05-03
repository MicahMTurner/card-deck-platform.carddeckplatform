package client.gui.entities;

import java.util.ArrayList;

import client.controller.ClientController;

import utils.Card;
import utils.Player;
import utils.Point;
import utils.Position;
import android.content.Context;
import android.view.View;

public abstract class Droppable extends View{
	/**
	 *  **************************************************<br/>
	 *  **************************************************<br/>
	 *  ****18**********************************19********<br/>
	 *  ****************1**2**3**4**5*********************<br/>
	 *  ****************6**7**8**9**10********************<br/>
	 *  ****************11*12*13*14*15********************<br/>
	 *  **************************************************<br/>
	 *  ****16**********************************17********<br/>
	 *  **************************************************<br/>
	 * 
	 * @author Yoav
	 *
	 */
	
	protected int myId;
	protected Point point;	
	//protected Stack<Card> cards = new Stack<Card>();
	protected ArrayList<Card> cards; 
	public abstract int sensitivityRadius();
	//protected Position position;
	
	public void onDrop(Player player,int fromId, Card card){		
		ClientController.sendAPI().cardAdded(cards, myId, fromId);
		addCard(player,card);
	}
	public void addDraggable(Draggable draggable){}
	public abstract void addCard(Player player,Card card);	
	public abstract void removeCard(Player player,Card card);
	public void removeCard(Card card){}
	public void removeDraggable(Draggable draggable){}
	
	public Droppable(Context context){
		super(context);		
		//this.position=position;
		//this.myId=IDMaker.getMaker().getId(position);
	}
	public Point getPoint() {
		return point;
	}
	
	public abstract int getX();
	
	public abstract int getY();
	public int cardsHolding(){
		return cards.size();
	}
	public boolean isEmpty(){
		return cards.isEmpty();
	}
	public Card peek(){
		return cards.get(cards.size()-1);
	}
	//public Stack<Card> getCards() {
	//	return cards;
	//}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void clear(){
		cards.clear();
	}
	public int getMyId() {
		return myId;
	}
	
//	public Position getPosition() {
//		return position;
//	}
//	public void setPosition(Position position) {
//		this.position = position;
//		this.myId=IDMaker.getMaker().getId(position);
//	}
	
		
}
