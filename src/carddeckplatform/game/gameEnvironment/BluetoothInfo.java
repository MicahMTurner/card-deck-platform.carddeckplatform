package carddeckplatform.game.gameEnvironment;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;

public class BluetoothInfo {
	private UUID appUUID;
	private BluetoothDevice hostDevice;
	private BluetoothServerSocket bluetoothServerSocket;
	
	public void initServerSocket(){
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		try {
			bluetoothServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("bebe", appUUID);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BluetoothServerSocket getServerSocket(){
		return bluetoothServerSocket;
	}
	
	public UUID getUUID(){
		return appUUID;
	}
	
	public BluetoothDevice getHostDevice() {
		return hostDevice;
	}
	
	public void setHostDevice(BluetoothDevice hostDevice) {
		this.hostDevice = hostDevice;
	}
	
	public BluetoothInfo(){
		appUUID = new UUID(0x0000110100001000L,0x800000805F9B34FBL);
	}

}
