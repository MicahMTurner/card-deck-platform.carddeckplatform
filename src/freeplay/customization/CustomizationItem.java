package freeplay.customization;

public interface CustomizationItem {
	enum State{NOT_SELECTED , SELECTED_REVEALED , SELECTED_HIDDEN}
	enum Layout{STACK , LINE}
	
	public void onClick();
	public void longClick();
}
