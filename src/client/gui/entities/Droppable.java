package client.gui.entities;

import java.util.ArrayList;
import java.util.Stack;

import utils.Card;
import utils.Player;
import utils.Point;
import android.content.Context;
import android.graphics.Canvas;
import client.controller.ClientController;

public abstract class Droppable{
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
	
	
	//protected Point point;	
	//public Stack<GuiCard>guiCards=new Stack<GuiCard>(); 
	//protected Stack<Card> cards = new Stack<Card>();
	//protected ArrayList<Card> cards; 
	public abstract int sensitivityRadius();
	//protected Position position;
	
	public void onDrop(Player player,Droppable from, Card card){
		card.setCoord(getX(), getY());
		addCard(player,card);
		ArrayList<Card>cards=new ArrayList<Card>();
		cards.add(card);
		from.removeCard(player,card);		
		ClientController.sendAPI().cardAdded(card, from.getMyId(),getMyId(),player);
	}
	
	public void onCardAdded(Player byWhom, Card card) {
		card.setCoord(getX(), getY());
		addCard(byWhom, card);
	}
	
	public abstract void deltCard(Card card);
	public void addDraggable(Draggable draggable){}	
	public abstract void addCard(Player player,Card card);	
	public abstract void removeCard(Player player,Card card);
	public void removeCard(Card card){}
	public void removeDraggable(Draggable draggable){}
	public abstract void draw(Canvas canvas,Context context);
	public Droppable(){
		//this.cards=new ArrayList<Card>();		
		//this.point=new Point(190,175);
		//this.myId=IDMaker.getMaker().getId(position);
	}
	//public Point getPoint() {
	//	return point;
	//}
	
	public abstract int getX();
	public abstract int getY();
	public abstract int cardsHolding();
	public abstract boolean isEmpty();
	//public Card peek(){
	//	return cards.get(cards.size()-1);
	//}
	//public Stack<Card> getCards() {
	//	return cards;
	//}
	//public ArrayList<Card> getCards() {
	//	return cards;
	//}
	public abstract void clear();
	public abstract int getMyId();

	
//	public Position getPosition() {
//		return position;
//	}
//	public void setPosition(Position position) {
//		this.position = position;
//		this.myId=IDMaker.getMaker().getId(position);
//	}
	
		
}
