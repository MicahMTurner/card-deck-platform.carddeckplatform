package client.gui.entities;

import android.graphics.Canvas;

public interface Draggable {
	public int sensitivityRadius();
	public void onClick();
	public void onDrag();
	public void onRelease();
	public int getId();
	public int getX();
	public int getY();
	public void motionAnimation();
	public void motionAnimation(String str);
	public void clearAnimation();
	
	public void setLocation(int x, int y);
	
	public void draw(Canvas canvas);
}
