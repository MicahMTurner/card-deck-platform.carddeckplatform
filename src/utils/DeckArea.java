package utils;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import utils.droppableLayouts.DroppableLayout;

import android.content.Context;
import android.graphics.Canvas;

import IDmaker.IDMaker;

import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;



public class DeckArea extends Droppable{
	
	public DeckArea(Position.Button position) {
		super(position.getId(),position, new Point(10,13),DroppableLayout.LayoutType.HEAP);
		this.image = "playerarea";
	}

	//change to queue?
	public LinkedList<Card> cards = new LinkedList<Card>();
	
	@Override
	public AbstractList<Card> getMyCards() {
		return cards;
	}
		


	public int getSize(){
		return cards.size();
	}

	@Override
	public void deltCard(Card card) {
		cards.push(card);
		card.setLocation(getX(), getY());
		
	}

	@Override
	public boolean onCardAdded(Player player, Card card) {
		card.hide();
		card.setLocation(getX(), getY());
		card.setAngle(0);
		cards.addLast(card);
		return true;
		
	}

	@Override
	public boolean onCardRemoved(Player player, Card card) {
		cards.remove(card);
		return true;
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



	@Override
	public void onRoundEnd(Player player) {
	}



	@Override
	public Card peek() {
		return cards.peek();
	}
//
}
