package communication.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

import communication.link.Streams;

public class TcpAcceptor implements Acceptor {
	
	
	
	public TcpAcceptor(){

	}
	
	@Override
	public Streams accept() {
		try {
			Socket clientSocket;
			clientSocket = GameEnvironment.get().getTcpInfo().getServerSocket().accept();
			
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			
			return new Streams(out, in);
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
			
		return null;
	}
}
