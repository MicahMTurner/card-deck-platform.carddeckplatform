package utils;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import android.content.Context;
import android.graphics.Canvas;

import IDmaker.IDMaker;

import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;



public class AbstractDeck extends Droppable{
	
	public AbstractDeck(Position.Button position) {
		super(position.getId(),position);
	}

	//change to queue?
	public Stack<Card> cards = new Stack<Card>();
	
	@Override
	public AbstractList<Card> getCards() {
		return cards;
	}
		


	public int getSize(){
		return cards.size();
	}
	

	@Override
	public int sensitivityRadius() {
		return 30;
	}

	@Override
	public void deltCard(Card card) {
		cards.push(card);
		card.setLocation(getX(), getY());
		
	}

	@Override
	public void addCard(Player player, Card card) {
		card.setLocation(getX(), getY());
		cards.push(card);
	}

	@Override
	public void removeCard(Player player, Card card) {
		cards.remove(card);
		
	}

	@Override
	public void draw(Canvas canvas, Context context) {
		
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

	@Override
	public int cardsHolding() {
		return cards.size();
	}

	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	@Override
	public void clear() {
		cards.clear();
		
	}
}
