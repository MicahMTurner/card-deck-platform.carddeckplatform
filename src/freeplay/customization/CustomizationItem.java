package freeplay.customization;

import handlers.Handler;
import client.gui.entities.Droppable;

public interface CustomizationItem {
	
	public enum Type{PLAYER , PUBLIC , DECK}
	enum Layout{STACK , LINE}
	
	public void onClick();
	public void longClick();
	public Handler createHandler();
	public Type getType();
}
