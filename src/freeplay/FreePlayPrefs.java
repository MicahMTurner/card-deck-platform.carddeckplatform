package freeplay;

public class FreePlayPrefs {
	private String publicLayout;
	private boolean playerCardsVisible;
	private boolean publicCardsVisible;
	
	public FreePlayPrefs(String publicLayout, boolean playerCardsVisible, boolean publicCardsVisible){
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
