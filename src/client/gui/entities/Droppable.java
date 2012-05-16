package client.gui.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

import utils.Card;
import utils.Player;
import utils.Point;
import android.content.Context;
import android.graphics.Canvas;
import client.controller.ClientController;

public abstract class Droppable implements Serializable{
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
	protected int id;
	//protected Position position;
	
	public void onDrop(Player player,Droppable from, Card card){
		from.removeCard(player,card);
		card.setCoord(getX(), getY());
		ClientController.sendAPI().cardAdded(card, from.getId(),id,player);
		addCard(player,card);						
		
	}
	
	public void onCardAdded(Player byWhom, Card card) {
		card.setCoord(getX(),getY());
		addCard(byWhom, card);
	}
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Droppable)) { 
            return false;
        }
        Droppable otherDroppable = (Droppable)other;
        return this.id==otherDroppable.id;		
	}
	public abstract void deltCard(Card card);
	public abstract ArrayList<Card> getCards();
	public abstract void addCard(Player player,Card card);	
	public abstract void removeCard(Player player,Card card);
	public abstract void draw(Canvas canvas,Context context);
	public Droppable(int id){
		this.id=id;
		
		//this.cards=new ArrayList<Card>();		
		//this.point=new Point(190,175);
		//this.myId=IDMaker.getMaker().getId(position);
	}

	
	public abstract int getX();
	public abstract int getY();	
	public abstract int cardsHolding();
	public abstract boolean isEmpty();

	public abstract void clear();
	public int getId(){
		return id;
	}

	
		
}
