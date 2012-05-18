package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import carddeckplatform.game.GameStatus;
//import communication.entities.Client;
//import communication.entities.TcpClient;


public class ServerConnection implements Runnable{
	private Socket socket;
	//private Client client;
	private Sender sender;
	private Receiver receiver;
	private CountDownLatch cdl = new CountDownLatch(1);
	private  LinkedBlockingQueue<ActionForQueue> commandsQueue;
	private volatile boolean stopped;
	
	//---Singleton implementation---//
		private static class ServerConnectionHolder
		{
			private final static ServerConnection serverConnection=new ServerConnection();
		}
		/**
		 * get server connection instance
		 */
		public static ServerConnection getConnection(){
			return ServerConnectionHolder.serverConnection;
		}

		private abstract class ActionForQueue{			
			public abstract void execute();
		}
		private class OpenConnectionExecutor extends ActionForQueue{

			@Override
			public void execute() {
				try {
					// creates a socket.
					socket = new Socket(GameStatus.hostIp, GameStatus.hostPort);
					// creates an outputstream.
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				
					sender = new TcpSender(out);
					receiver = new TcpReceiver(in);
					receiver.initializeMode();
					sender.initializeMode();
					new Thread(receiver).start();
					cdl.countDown();
				
				} catch (UnknownHostException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					
				} catch (IOException e) {				
					e.printStackTrace();			
				}
				
			}
			
		}
	private class CloseConnectionExecutor extends ActionForQueue{

		@Override
		public void execute() {
			try {				
				sender.closeStream();
				receiver.closeStream();
				socket.close();
			
			} catch (IOException e) {		
				e.printStackTrace();
			}
			
		}
		
	}
		
	
	
	
	
	private ServerConnection(){
		commandsQueue=new LinkedBlockingQueue<ActionForQueue>();
		this.stopped=false;
		new Thread(this).start();
	}
	//---Public methods---//
	
	public void openConnection(){
		//this.stopped=false;
		commandsQueue.add(new OpenConnectionExecutor());
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (!stopped){
			try {
				commandsQueue.take().execute();
			} catch (InterruptedException e) {
			}
		}		
	}
	
	public void closeConnection(){		
		commandsQueue.add(new CloseConnectionExecutor());		
	}
	
	public void shutDown(){
		this.stopped=true;
		//add a new blank command to wake controller and stop it
		commandsQueue.add(new ActionForQueue() {				
			@Override
			public void execute() {	}
		});			
	}
	
	public Sender getMessageSender(){
		return sender;
	}
}
