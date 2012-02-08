package communication.link;

import com.google.gson.Gson;

import communication.messages.*;

public abstract class Sender {
	public abstract void send(String className , Message msg);

	public abstract boolean openConnection();
	public abstract boolean closeConnection();
	
	public String parseMessage(String className, Message msg){
		// TODO Auto-generated method stub
		MessageContainer mc = new MessageContainer();
		Gson gson = new Gson();
		mc.className = className;
		mc.classJson = gson.toJson(msg);
		return gson.toJson(mc);
	}
	
	
	 
	
}
