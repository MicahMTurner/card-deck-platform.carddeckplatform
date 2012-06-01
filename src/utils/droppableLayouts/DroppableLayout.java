package utils.droppableLayouts;

import java.io.Serializable;

import client.gui.entities.Droppable;
import utils.Card;
import utils.Point;

public abstract class DroppableLayout implements Serializable {
	public enum Orientation{HORIZONTAL, VERTICAL}
	
	protected Orientation orientation;
	protected Droppable droppable;
	
	
	public DroppableLayout(Droppable droppable){
		this.orientation = orientation;
		this.droppable = droppable;
	}
	
	public abstract void rearrange();
	public void animate(Card card, Point dest, double finalAngle) {
		// TODO add animation here.
		
	}
}
