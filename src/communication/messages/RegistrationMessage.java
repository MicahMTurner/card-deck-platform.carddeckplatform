package communication.messages;

import com.google.gson.annotations.SerializedName;

public class RegistrationMessage extends Message {
	public RegistrationMessage(){
		messageType = "RegistrationMessage";
	}
	
	@SerializedName("clientName")
	public String clientName;
	
	@SerializedName("id")
	public String id;
	
}
