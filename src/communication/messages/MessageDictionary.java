package communication.messages;

import com.google.gson.Gson;

public class MessageDictionary {
	public static Message getMessage(String className , String classJson){
		Gson gson = new Gson();
		if(className.equals("SampleMessage"))
			return gson.fromJson(classJson, SampleMessage.class);
		return null;
	}
}
