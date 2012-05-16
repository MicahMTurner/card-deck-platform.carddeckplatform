package logic.client;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import android.content.Context;
import android.graphics.Canvas;

import IDmaker.IDMaker;

import client.gui.entities.Droppable;

import utils.Card;
import utils.Player;
import utils.Position;


public abstract class AbstractDeck extends Droppable{
	
	public AbstractDeck(Position.Button position) {
		super(IDMaker.getMaker().getId(),position);
	}

	//change to queue?
	public Stack<Card> cards = new Stack<Card>();
	
	@Override
	public AbstractList<Card> getCards() {
		return cards;
	}
		
	/**
	 * 	pick card in random place and swap with card in random place 
	 *	do that size of deck times (or maybe twice the size?)
	 *  @param timesToShuffle how many times user wants to shuffle
	 */
	public void shuffle(int timesToShuffle){
		Random random=new Random();
		int limit=cards.size();
		int randomPlace;
		for (int j=0;j<timesToShuffle;j++){
			for (int i=0;i<limit;i++){
				randomPlace=random.nextInt(limit);
				swap(i,randomPlace);
				cards.get(i);
			}
		}
	}

	public int getSize(){
		return cards.size();
	}
	private void swap(int i, int randomPlace) {
		Collections.swap(cards,i,randomPlace);
	}

	public Card drawCard() {
		return cards.pop();
		
	}

	@Override
	public int sensitivityRadius() {
		return 30;
	}

	@Override
	public void deltCard(Card card) {
		// TODO Auto-generated method stub
		
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

	@Override
	public int getX() {
		return 100;
	}

	@Override
	public int getY() {
		return 100;
	}

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
