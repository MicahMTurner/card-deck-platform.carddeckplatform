package client.gui.entities;

import logic.card.CardLogic;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public abstract class Draggable extends View {
	protected CardLogic cardLogic;
	
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
	public abstract CardLogic getCardLogic();
	
	public abstract void setLocation(int x, int y);
	
	public abstract void draw(Canvas canvas);
}
