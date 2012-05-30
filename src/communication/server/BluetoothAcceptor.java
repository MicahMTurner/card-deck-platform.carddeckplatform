package communication.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import communication.link.Streams;

public class BluetoothAcceptor implements Acceptor {
	public BluetoothAcceptor(){
	}
	
	@Override
	public Streams accept() {
		// TODO Auto-generated method stub
		BluetoothSocket clientSocket;
		try {
			//clientSocket = serverSocket.accept();
			clientSocket = GameEnvironment.get().getBluetoothInfo().getServerSocket().accept();
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			
			return new Streams(out, in);
		} catch (IOException e) {
			// TODO: handle exception
		}
		return null;
	}

}
