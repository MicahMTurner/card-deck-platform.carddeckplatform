package client.gui.entities;

import logic.card.CardLogic;
import logic.client.LogicDroppable;
import carddeckplatform.game.R;
import android.content.Context;
import android.graphics.Canvas;

public class PublicPlace extends Droppable {
	
	public PublicPlace(Context context, int x,int y){
		this.x = x;
		this.y = y;
		this.context = context;
	}
	
	@Override
	public int sensitivityRadius() {
		// TODO Auto-generated method stub
		return 300;
	}
	
	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHover() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addDraggable(Draggable draggable) {
		draggable.setLocation(getX(), getY());
		draggable.setContainer(this);
		logicDroppable.addCard(draggable.getCardLogic());
	}

	@Override
	public void onDrop(Draggable draggable) {
		// TODO Auto-generated method stub
		addDraggable(draggable);
		logicDroppable.onDropHandler(draggable.getCardLogic());
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CardLogic getDraggable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeDraggable(Draggable draggable) {
		// TODO Auto-generated method stub
		logicDroppable.getCards().remove(draggable.getCardLogic());
	}



}
