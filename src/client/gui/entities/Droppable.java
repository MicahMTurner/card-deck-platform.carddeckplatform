package client.gui.entities;

import android.graphics.Canvas;

public interface Droppable {
	public int sensitivityRadius();
	public void onClick();
	public void onHover();
	public void onDrop(Draggable draggable);
	
	public int getX();
	public int getY();
	
	public void draw(Canvas canvas);
}
