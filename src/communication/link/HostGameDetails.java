package communication.link;

import java.io.Serializable;
import java.net.InetAddress;

import client.gui.entities.Droppable;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

public class HostGameDetails implements Serializable,Comparable<HostGameDetails> {
	private String address;
	private String owner;
	private String gameName;
	private int minPlayers;
	private int maxPlayers;
	private String instructions;
	
	
	public HostGameDetails(String owner, String gameName,int minPlayers,int maxPlayers,String instructions) {
		// TODO Auto-generated constructor stub
		this.address = GameEnvironment.get().getTcpInfo().getLocalIp();
		this.owner = owner;
		this.gameName = gameName;
		this.minPlayers=minPlayers;
		this.maxPlayers=maxPlayers;
		this.instructions=instructions;
	}
	

	public String getGameName() {
		return gameName;
	}
	public int getMinPlayers() {
		return minPlayers;
	}
	public int getMaxPlayers() {
		return maxPlayers;
	}
	public String getInstructions() {
		return instructions;
	}
	public String getOwner() {
		return owner;
	}
	
	public String getAddress() {
		return address;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof HostGameDetails)) {
			return false;
		}
		HostGameDetails otherHostGameDetails = (HostGameDetails) other;
		return (this.address.equals(otherHostGameDetails.address));
	}
	
	@Override
	public int compareTo(HostGameDetails another) {
		if (another.address.equals(this.address)){
			return 0;
		}
		return 1;
	}
	
	
}
