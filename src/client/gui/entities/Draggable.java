package client.gui.entities;

import logic.card.CardLogic;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public abstract class Draggable extends View {
	protected CardLogic cardLogic;
	private Droppable container;
	//protected int tempX;
	//protected int tempY;
	protected int prevX;
	protected int prevY;
	protected int x;
	protected int y;
	
	public Draggable(Context context,int x, int y) {
		super(context);
		this.x=x;
		this.y=y;
		
	}
	public abstract int sensitivityRadius();
	public abstract void onClick();
	public abstract void onDrag();
	public abstract void onRelease();
	public abstract int getId();
	public abstract void undoMove();
	public  int getX(){
		return x;
	}
	public  int getY(){
		return y;
	}
	public abstract void motionAnimation();
	public abstract void motionAnimation(String str);
	public abstract void clearAnimation();
	
	public CardLogic getCardLogic(){
		return cardLogic;
	}
	
	public void setCardLogic(CardLogic cardLogic) {
		this.cardLogic = cardLogic;
	}
	
	public Droppable getContainer(){
		return container;
	}
	
	public void setContainer(Droppable container){
		this.container = container;
	}
		
	public void setLocation(int x, int y){
		this.x=x;
		this.y=y;
	}
}
