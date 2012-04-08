package logic.client;

import java.util.Stack;

import logic.card.CardLogic;

public abstract class LogicDroppable {
	protected Stack<CardLogic> cards;
	private int id;
	public LogicDroppable(int id) {
		this.id =id;
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
