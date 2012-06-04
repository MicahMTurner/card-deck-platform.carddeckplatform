package utils.droppableLayouts;

import client.gui.entities.Droppable;

public abstract class LineLayout extends DroppableLayout {


	public LineLayout(Droppable droppable) {
		super(droppable);
	}


	@Override
	public LayoutType getType() {
		return LayoutType.LINE;
	}

}
