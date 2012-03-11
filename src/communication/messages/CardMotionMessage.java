package communication.messages;

import com.google.gson.annotations.SerializedName;

public class CardMotionMessage extends Message {
	public CardMotionMessage(){
		messageType = "CardMotionMessage";
	}
	
	public CardMotionMessage(String username , int cardId , int X , int Y){
		messageType = "CardMotionMessage";
		
		this.username = username;
		this.cardId = cardId;
		this.X = X;
		this.Y = Y;
		
	}
	
	@SerializedName("username")
	public String username;
	
	@SerializedName("cardId")
	public int cardId;
	
	@SerializedName("X")
	public int X;
	
	@SerializedName("Y")
	public int Y;
	
}
