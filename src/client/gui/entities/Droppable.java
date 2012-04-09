package client.gui.entities;

import logic.card.CardLogic;
import logic.client.LogicDroppable;
import android.content.Context;
import android.graphics.Canvas;

public abstract class Droppable {
	protected LogicDroppable logicDroppable;
	protected int x;
	protected int y;
	protected Context context;
 	
	
	public abstract int sensitivityRadius();
	public abstract void onClick();
	public abstract void onHover();
	public abstract void onDrop(Draggable draggable);
	
	public abstract void addDraggable(Draggable draggable);
	public abstract void removeDraggable(Draggable draggable);
	
	public abstract CardLogic getDraggable();
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public abstract void draw(Canvas canvas);
	
}
