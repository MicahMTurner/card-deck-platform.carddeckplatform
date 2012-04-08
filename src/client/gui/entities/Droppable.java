package client.gui.entities;

import android.content.Context;
import android.graphics.Canvas;

public abstract class Droppable {
	protected int x;
	protected int y;
	protected Context context;
 	
	
	public abstract int sensitivityRadius();
	public abstract void onClick();
	public abstract void onHover();
	public abstract void onDrop(Draggable draggable);
	
	public abstract int getX();
	public abstract int getY();
	
	public abstract void draw(Canvas canvas);
}
