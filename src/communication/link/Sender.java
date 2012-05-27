package communication.link;

//import com.google.gson.Gson;

import communication.messages.*;

public abstract class Sender {	
	public abstract void send(Message msg);

	//public abstract boolean openConnection();
	public abstract boolean closeStream();

	public abstract void initializeMode();
	
//	public static String parseMessage(Message msg){
//		// TODO Auto-generated method stub
//		String className = msg.messageType;
//		MessageContainer mc = new MessageContainer();
//		Gson gson = new Gson();
//		mc.className = className;
//		mc.classJson = gson.toJson(msg);
//		return gson.toJson(mc);
//	}
	
	
	 
	
}
