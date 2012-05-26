package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;

import client.controller.ClientController;

import communication.messages.Message;

public class Receiver implements Runnable{
	
	private ObjectInputStream in;
	
	//private Handler h = new Handler();
	
	
//	public TcpReceiver(int port){
//		this.port = port;
//	}
	public Receiver(){}
	public Receiver(ObjectInputStream in){
		this.in = in;
	}
	
	@Override
	public void run() {
		
		while(true){
			try {		
				
				//System.out.println("Wait for message");
				Message message = (Message)in.readObject();
				//System.out.println("message received");

				message.actionOnClient();
				//System.out.println("message executed");
			} catch (IOException e) {
				//stream got closed
				System.out.println("stream got closed");
				try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e2){
					
				}
				break;
				
				//System.out.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public boolean closeStream() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	public void initializeMode() {
		try {			
			Message initMessage = (Message)in.readObject();
			initMessage.actionOnClient();
			
		} catch (ClassNotFoundException e) {	
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

}

//	private class Notifier extends AsyncTask<Integer, Message, Long> {
//		   
////		   public Message unParseMessage(String str){
////				Gson gson = new Gson();
////				MessageContainer mc = gson.fromJson(str , MessageContainer.class);
////				return MessageDictionary.getMessage(mc.className, mc.classJson);
////		   }
//		   
//			@Override
//			protected Long doInBackground(Integer... arg0) {
//				while(true){
//					try {		
//						
//						System.out.println("Wait for message");
//						Message message = (Message)in.readObject();
//						System.out.println("message received");
//						onProgressUpdate(message);
//						System.out.println("Message delivared");
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						System.out.println(e.getMessage());
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} 
//				}
//			}
//		
//		protected void onProgressUpdate(Message... progress) {
//			System.out.println("Entered update");
//			final Message message = progress[0];
//			System.out.println("Got message");			
//			h.post(new Runnable(){
//
//				@Override
//				public void run() {
//					
//					//moveCard(cmm.cardId , cmm.X , cmm.Y);
//					//message.clientAction();
//					TcpReceiver.this.setChanged();
//					TcpReceiver.this.notifyObservers(message);
//					System.out.println("executing message");
//					//message.actionOnClient();
//					System.out.println("message executed");
//					
//				}
//				
//			});
//		}
//		   
//		protected void onPostExecute(Long result) {
//			
//	    }
//
//	   }
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public void startListen() {
//		//Notifier n = new Notifier();
//		//n.execute(0);
//		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				
//				while(true){
//					try {		
//						
//						System.out.println("Wait for message");
//						Message message = (Message)in.readObject();
//						System.out.println("message received");
//						//System.out.println("Message delivared");
//						message.actionOnClient();
//						System.out.println("message executed");
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						System.out.println(e.getMessage());
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} 
//				}
//			}
//		}).start();
//	}

