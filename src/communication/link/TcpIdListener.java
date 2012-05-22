package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import carddeckplatform.game.GameStatus;

public class TcpIdListener implements Runnable {

	private String owner;
	private String gameName;
	private ServerSocket serverSocket=null;
	
	
	public TcpIdListener(String owner, String gameName){
		this.owner = owner;
		this.gameName = gameName;
		try {
			serverSocket = new ServerSocket(GameStatus.idPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Socket clientSocket;
				clientSocket = serverSocket.accept();
				System.out.println("Got request from " + clientSocket.getLocalAddress().toString());
				ObjectOutputStream out=new ObjectOutputStream(clientSocket.getOutputStream());
				//ObjectInputStream in=new ObjectInputStream(clientSocket.getInputStream());
				
				out.writeObject(new HostId("", owner, gameName));
				out.close();
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
