package utils.droppableLayouts;

import client.gui.entities.Droppable;
import utils.Point;

public abstract class DroppableLayout {
	public enum Orientation{HORIZONTAL, VERTICAL}
	
	protected Orientation orientation;
	protected Droppable droppable;
	
	
	public DroppableLayout(Droppable droppable, Orientation orientation){
		this.orientation = orientation;
		this.droppable = droppable;
	}
	
	public abstract Point getNextPosition();
	public abstract void rearrange();
}
