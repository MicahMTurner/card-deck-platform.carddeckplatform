package freeplay.customization;

import handlers.Handler;

public interface CustomizationItem {
	
	public enum Type{PLAYER , PUBLIC , DECK}
	enum Layout{STACK , LINE}
	
	public void onClick();
	public void longClick();
	public Handler createHandler();
	public Type getType();
}
