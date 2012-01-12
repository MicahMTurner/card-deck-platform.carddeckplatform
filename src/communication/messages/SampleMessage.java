package communication.messages;

import com.google.gson.annotations.SerializedName;

public class SampleMessage extends Message {
	
	public SampleMessage(){
		messageType = "SampleMessage";
	}
	
	@SerializedName("name")
	public String name;

	
}
