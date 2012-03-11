package communication.messages;

import com.google.gson.annotations.SerializedName;

public class EndCardMotionMessage extends Message {
	
	public EndCardMotionMessage(){
		messageType = "EndCardMotionMessage";
	}
	
	public EndCardMotionMessage(int cardId){
		messageType = "EndCardMotionMessage";
		
		
		this.cardId = cardId;
	
	}
	
	@SerializedName("cardId")
	public int cardId;


}
