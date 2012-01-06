package communication.messages;

import com.google.gson.annotations.SerializedName;

public abstract class Message {
	@SerializedName("messageType")
	public String messageType;
}
