package communication.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import communication.link.Streams;

public class TcpAcceptor implements Acceptor {
	private ServerSocket serverSocket;
	
	
	public TcpAcceptor(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}
	
	@Override
	public Streams accept() {
		try {
			Socket clientSocket;
			clientSocket = serverSocket.accept();
			
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			
			return new Streams(out, in);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return null;
	}
}
