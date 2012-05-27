package communication.link;

import java.io.Serializable;

public class HostId implements Serializable {
	private String address;
	private String owner;
	private String gameName;
	
	
	public HostId(String ipAddress, String owner, String gameName) {
		// TODO Auto-generated constructor stub
		this.address = ipAddress;
		this.owner = owner;
		this.gameName = gameName;
	}
	
	
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public void setAddress(String ipAddress) {
		this.address = ipAddress;
	}
	
	
	
	public String getGameName() {
		return gameName;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getAddress() {
		return address;
	}
	
	
}
