package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BlueToothConnector implements Connector {
	
	private BluetoothSocket socket;
	private ObjectOutputStream out=null;
	private ObjectInputStream in=null;
	
	private BluetoothDevice device;
	private UUID destinationId;
	private BluetoothAdapter mBluetoothAdapter;
	
	private int currentUUIDIndex=0;
	
	public BlueToothConnector(BluetoothDevice device){
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
			//throw new Exception("Bluetooth is not supported.");
		}
		this.device = device;
		this.destinationId = GameEnvironment.get().getBluetoothInfo().getUUID(currentUUIDIndex);
	}
	@Override
	public Streams connect() {
		BluetoothSocket tmp = null;
		
		while(currentUUIDIndex<4){
			try {
	            // MY_UUID is the app's UUID string, also used by the server code
	            tmp = device.createRfcommSocketToServiceRecord(destinationId);
	        } catch (IOException e) {}
		
			socket = tmp;
			
			try {
				mBluetoothAdapter.cancelDiscovery();
				socket.connect();
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
				break;
	        } catch (IOException e) {
	        	e.printStackTrace();
	        	currentUUIDIndex++;
		        this.destinationId = GameEnvironment.get().getBluetoothInfo().getUUID(currentUUIDIndex);
	        }
		}
		return new Streams(out, in);
	}

	@Override
	public void disconnect() {
		try {
			out.close();
			in.close();
			socket.close();
			out=null;
			in=null;
			socket=null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
