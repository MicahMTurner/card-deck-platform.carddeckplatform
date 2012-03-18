package carddeckplatform.game;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.widget.Toast;

import communication.messages.Message;
import communication.messages.SampleMessage;

public class SampleObserver implements Observer {
	Context cntxt;
	
	
	
	public Context getCntxt() {
		return cntxt;
	}



	public void setCntxt(Context cntxt) {
		this.cntxt = cntxt;
	}



	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		Message message = (Message) data;
		System.out.println("Message received!!");
		
		System.out.println(message.messageType);
	}

}
