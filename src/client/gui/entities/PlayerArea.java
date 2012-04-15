//package client.gui.entities;
//
//import logic.card.CardLogic;
//import android.graphics.Canvas;
//
//public class PlayerArea extends Droppable {
//
//	@Override
//	public int sensitivityRadius() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void onClick() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onHover() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onDrop(Draggable draggable) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void addDraggable(Draggable draggable) {		
//		draggable.setLocation(getX(), getY());
//
//		draggable.setContainer(this);
//		
//		logicDroppable.addCard(draggable.getCardLogic());
//	}
//
//	@Override
//	public void draw(Canvas canvas) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public CardLogic getDraggable() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void removeDraggable(Draggable draggable) {
//		// TODO Auto-generated method stub
//		logicDroppable.getCards().remove(draggable.getCardLogic());
//	}
//
//}
