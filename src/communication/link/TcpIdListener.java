package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

public class TcpIdListener implements Runnable {

	private String owner;
	private String gameName;
	private volatile boolean stop;
	private ServerSocket serverSocket=null;
	
	
	public TcpIdListener(String owner, String gameName){
		this.owner = owner;
		this.stop=false;
		this.gameName = gameName;
		serverSocket=null;
	}
	public void start(){
		stop=false;
		try {
			serverSocket = new ServerSocket(GameEnvironment.get().getTcpInfo().getIdPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}
	public void stop(){
		stop=true;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		if (serverSocket!=null){
			while(!stop){
				try {
					Socket clientSocket;
					clientSocket = serverSocket.accept();
					System.out.println("Got request from " + clientSocket.getLocalAddress().toString());
					ObjectOutputStream out=new ObjectOutputStream(clientSocket.getOutputStream());
				//	ObjectInputStream in=new ObjectInputStream(clientSocket.getInputStream());
				
					out.writeObject(new HostId("", owner, gameName));
					out.close();
					clientSocket.close();
				} catch (IOException e) {}
			}
		}
	}

}
