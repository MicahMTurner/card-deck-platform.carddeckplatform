package utils;

import java.io.Serializable;

public class GamePrefs implements Serializable {
	private String publicLayout;
	private boolean playerCardsVisible;
	private boolean publicCardsVisible;
	
	public GamePrefs(String publicLayout, boolean playerCardsVisible, boolean publicCardsVisible){
		this.publicLayout = publicLayout;
		this.playerCardsVisible = playerCardsVisible;
		this.publicCardsVisible = publicCardsVisible;
	}
	
	public String getPublicLayout() {
		return publicLayout;
	}
	
	public boolean getPlayerCardsVisible() {
		return playerCardsVisible;
	}
	
	public boolean getPublicCardsVisible() {
		return publicCardsVisible;
	}
}
