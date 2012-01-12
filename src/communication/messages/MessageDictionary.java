package communication.messages;

import java.util.HashMap;

import com.google.gson.Gson;

public class MessageDictionary {
	
	private static HashMap<String , Message> values = new HashMap<String , Message>();
	
	public static void addValue(Message message){
		values.put(message.messageType, message);
	}
	
	public static Message getMessage(String className , String classJson){
		Gson gson = new Gson();
		String s = values.get(className).getClass().getName();
		Message m = gson.fromJson(classJson, values.get(className).getClass());
		return m;
	}
}
