package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import communication.messages.Message;

//import communication.entities.Client;
//import communication.entities.TcpClient;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import carddeckplatform.game.gameEnvironment.GameEnvironment.ConnectionType;


public class ServerConnection implements Runnable{
	private Socket socket;
	//private Client client;
	private Sender sender;
	private Receiver receiver;
	private CountDownLatch cdl = new CountDownLatch(1);
	private  LinkedBlockingQueue<ActionForQueue> commandsQueue;
	private volatile boolean stopped;
	private Connector connector;
	
	
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
		private class SendMessageExecutor extends ActionForQueue{
			private Message msg;
			public SendMessageExecutor(Message msg) {
				this.msg=msg;
			}
			@Override
			public void execute() {
				sender.send(msg);
				
			}
		}
		private class OpenConnectionExecutor extends ActionForQueue{

			@Override
			public void execute() {
				// uses TCP if specified or if the current player is the hosting player.
				if(GameEnvironment.get().getConnectionType()==ConnectionType.TCP || GameEnvironment.get().getPlayerInfo().isServer())
					connector = new TcpConnector(GameEnvironment.get().getTcpInfo().getHostIp(),GameEnvironment.get().getTcpInfo().getHostPort());
				else if(GameEnvironment.get().getConnectionType()==ConnectionType.BLUETOOTH)
					connector = new BlueToothConnector(GameEnvironment.get().getBluetoothInfo().getHostDevice(), GameEnvironment.get().getBluetoothInfo().getUUID());
				Streams stream = connector.connect();
				if (stream!=null){
				ObjectOutputStream out = stream.getOut();
				ObjectInputStream in = stream.getIn();
				
				sender = new Sender(out);
				receiver = new Receiver(in);
				receiver.initializeMode();
				sender.initializeMode();
				//GameEnvironment.getGameEnvironment().getHandler().post(receiver);
				new Thread(receiver).start();
				
				}
				cdl.countDown();
			}		
				
		
		}
			
		
	private class CloseConnectionExecutor extends ActionForQueue{

		@Override
		public void execute() {
			try {	
				if (socket!=null){
					sender.closeStream();
					receiver.closeStream();
					socket.close();
				}
			
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
	
	public void openConnection() throws IOException{
		//this.stopped=false;
		commandsQueue.add(new OpenConnectionExecutor());
		try {
			cdl.await();
			if (sender==null || receiver==null){
				throw new IOException("Host is unreachable");
			}
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
	

	public void send(Message msg){
		commandsQueue.add(new SendMessageExecutor(msg));
	}
}
