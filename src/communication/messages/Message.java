package communication.messages;

import carddeckplatform.game.GameStatus;

import com.google.gson.annotations.SerializedName;

public abstract class Message {
	@SerializedName("messageType")
	public String messageType;
	
	public Object sender=GameStatus.localIp;
	

}
