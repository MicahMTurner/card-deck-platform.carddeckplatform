package blackJack.gui;

import logic.card.CardLogic;
import logic.client.LogicDroppable;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;

public class DeckArea extends Droppable{
	public DeckArea(Context context, int x,int y, LogicDroppable logicDroppable){
		super(context,x,y,logicDroppable);
	}
	@Override
	public int sensitivityRadius() {
		// TODO Auto-generated method stub
		return 0;
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
	public void onDrop(Draggable draggable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDraggable(Draggable draggable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDraggable(Draggable draggable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CardLogic getDraggable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText("Deck Area", getX(), getY(), new Paint());
		
	}

}
