package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import carddeckplatform.game.GameStatus;

public class TcpConnector implements Connector {

	private String ipAddress;
	private int port;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket socket;
	
	public TcpConnector(String ipAddress, int port){
		this.ipAddress = ipAddress;
		this.port = port;
	}
	@Override
	public Streams connect() {
		try {
			socket = new Socket(GameStatus.hostIp,GameStatus.hostPort);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			return new Streams(out, in);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// creates an outputstream.
		
		
		return null;
	}
	@Override
	public void disconnect() {
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
