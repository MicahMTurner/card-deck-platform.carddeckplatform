package communication.server;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import carddeckplatform.game.GameStatus;

public class TmpServer implements Runnable {
	public void run(){
		try {	
			ServerSocket serverSocket = new ServerSocket(GameStatus.hostPort);
			Socket clientSocket;
			while(true){
				
				System.out.println("Listening to port " + GameStatus.hostPort + " Waiting for messages...");
				
				clientSocket = serverSocket.accept();
	
				System.out.println("connection request from from " + clientSocket.getRemoteSocketAddress().toString());
				
				final ObjectOutputStream out=new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectInputStream in=new ObjectInputStream(clientSocket.getInputStream());
				
				ServerTask serverTask = new ServerTask(clientSocket.getRemoteSocketAddress().toString(),in, out);
				ServerConnections.addConnection(serverTask);
				
			    new Thread(serverTask).start();
		    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// init game.
		// start game.
	}
}
