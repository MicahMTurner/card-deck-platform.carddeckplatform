package communication.messages;

import java.util.HashMap;

import com.google.gson.Gson;

public class MessageDictionary {
	
	private static HashMap<String , Message> values = new HashMap<String , Message>();
	private static boolean isFirstRun=true;
	
	private static void createDictionary(){
		MessageDictionary.addValue(new SampleMessage());
        MessageDictionary.addValue(new RegistrationMessage());
        MessageDictionary.addValue(new CardMotionMessage());
        MessageDictionary.addValue(new EndCardMotionMessage());
	}
	
	public static void addValue(Message message){
		values.put(message.messageType, message);
	}
	
	public static Message getMessage(String className , String classJson){		
		if(isFirstRun){
			createDictionary();
			isFirstRun=false;
		}
		
		Gson gson = new Gson();
		String s = values.get(className).getClass().getName();
		Message m = gson.fromJson(classJson, values.get(className).getClass());
		return m;
	}
}
