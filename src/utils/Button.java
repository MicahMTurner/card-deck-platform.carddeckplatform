package utils;

import java.util.AbstractList;

import utils.droppableLayouts.DroppableLayout;
import client.gui.entities.Droppable;
import handlers.ButtonEventsHandler;

public class Button{
	private ButtonEventsHandler handler;
	
	public Button(ButtonEventsHandler handler,Position.Button position) {
		//super(position.getId(), position, new Point(10,13), DroppableLayout.LayoutType.NONE);
		this.handler=handler;
		//image="button.png";
		
	}

	public void onClic(){
		handler.onClick();
	}
//	@Override
//	public boolean onDrop(Player player, Droppable from, Card card) {
//		// TODO Auto-generated method stub
//		return super.onDrop(player, from, card);
//	}
//	@Override
//	public void deltCard(Card card) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected AbstractList<Card> getMyCards() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean onCardAdded(Player player, Card card) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean onCardRemoved(Player player, Card card) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int cardsHolding() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public boolean isEmpty() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void clear() {
//		// TODO Auto-generated method stub
//		
//	}
	
}
