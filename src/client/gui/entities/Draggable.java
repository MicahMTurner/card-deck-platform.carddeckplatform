package client.gui.entities;

import logic.card.CardLogic;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public abstract class Draggable extends View {
	protected CardLogic cardLogic;
	private Droppable container;
	
	public Draggable(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public abstract int sensitivityRadius();
	public abstract void onClick();
	public abstract void onDrag();
	public abstract void onRelease();
	public abstract int getId();
	public abstract int getX();
	public abstract int getY();
	public abstract void motionAnimation();
	public abstract void motionAnimation(String str);
	public abstract void clearAnimation();
	
	public CardLogic getCardLogic(){
		return cardLogic;
	}
	
	
	public Droppable getContainer(){
		return container;
	}
	
	public void setContainer(Droppable container){
		this.container = container;
	}
	
	
	public abstract void setLocation(int x, int y);
}
