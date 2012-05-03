package logic.client;

import java.io.Serializable;
import java.util.Stack;

import logic.card.CardLogic;

public abstract class LogicDroppable implements Serializable{
	public enum Type{
		PLAYER,PUBLIC;
	}
	
	protected Stack<CardLogic> cards;
	protected Type type;
	private int id;
	public LogicDroppable(int id,Type type) {
		this.id =id;
		this.type=type;
		this.cards=new Stack<CardLogic>();
	}
	public Type getType() {
		return type;
	}
	public abstract void onClickHandler();
	public abstract void onDropHandler(CardLogic card);
	public abstract void addCard(CardLogic card);
	
	public int getId(){
		return id;
	}
	
	
	
	public Stack<CardLogic> getCards(){
		return cards;
	}
}
