package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BlueToothConnector implements Connector {
	
	private BluetoothSocket socket;
	private ObjectOutputStream out=null;
	private ObjectInputStream in=null;
	
	private BluetoothDevice device;
	private UUID destinationId;
	
	public BlueToothConnector(BluetoothDevice device, UUID destinationId){
		
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
			//throw new Exception("Bluetooth is not supported.");
		}
		this.device = device;
		this.destinationId = destinationId;
	}
	@Override
	public Streams connect() {
		BluetoothSocket tmp = null;
		
		try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(destinationId);
        } catch (IOException e) { }
		
		socket = tmp;
		
		try {
			socket.connect();
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) { }
		return new Streams(out, in);
	}

	@Override
	public void disconnect() {
		try {
			socket.close();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
