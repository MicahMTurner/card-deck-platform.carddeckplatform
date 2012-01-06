package communication.messages;

import com.google.gson.annotations.SerializedName;

public class SampleMessage extends Message {
	
	@SerializedName("name")
	public String name;
	
}
