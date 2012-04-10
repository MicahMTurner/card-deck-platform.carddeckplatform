package communication.link;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;

import carddeckplatform.game.CarddeckplatformActivity;
import carddeckplatform.game.StaticFunctions;

import android.os.AsyncTask;
import android.os.Handler;

//import com.google.gson.Gson;

import communication.messages.Message;
import communication.messages.MessageContainer;
//import communication.messages.MessageDictionary;

public class TcpReceiver extends Receiver{
	private int port;
	private ObjectInputStream in;
	
	
	
	public TcpReceiver(){}
//	public TcpReceiver(int port){
//		this.port = port;
//	}
	
	public TcpReceiver(ObjectInputStream in){
		this.in = in;
	}
	
	private class Notifier extends AsyncTask<Integer, Message, Long> {
		   
//		   public Message unParseMessage(String str){
//				Gson gson = new Gson();
//				MessageContainer mc = gson.fromJson(str , MessageContainer.class);
//				return MessageDictionary.getMessage(mc.className, mc.classJson);
//		   }
		   
			@Override
			protected Long doInBackground(Integer... arg0) {
				while(true){
					try {		
						
						System.out.println("Wait for message");
						Message message = (Message)in.readObject();
						System.out.println("message received");
						onProgressUpdate(message);
						System.out.println("Message delivared");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
		
		protected void onProgressUpdate(Message... progress) {
			System.out.println("Entered update");
			final Message message = progress[0];
			System.out.println("Got message");			
			CarddeckplatformActivity.h.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					//moveCard(cmm.cardId , cmm.X , cmm.Y);
					//message.clientAction();
					TcpReceiver.this.setChanged();
					TcpReceiver.this.notifyObservers(message);
					
				}
				
			});
		}
		   
		protected void onPostExecute(Long result) {
			
	    }

	   }
	
	
	
	
	
	
	
	
	
	
	@Override
	public void startListen() {
		Notifier n = new Notifier();
		n.execute(0);
	}

}
