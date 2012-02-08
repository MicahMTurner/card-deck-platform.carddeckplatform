package communication.messages;

import com.google.gson.annotations.SerializedName;

public class CardMotionMessage extends Message {
	public CardMotionMessage(){
		messageType = "CardMotionMessage";
	}
	
	@SerializedName("cardId")
	public int cardId;
	
	@SerializedName("X")
	public int X;
	
	@SerializedName("Y")
	public int Y;
	
}
