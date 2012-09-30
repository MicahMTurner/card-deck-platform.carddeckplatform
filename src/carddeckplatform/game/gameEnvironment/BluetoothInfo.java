package carddeckplatform.game.gameEnvironment;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import carddeckplatform.game.CarddeckplatformActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;

public class BluetoothInfo {
	private UUID appUUID;
	private UUID appUUIDs[] = new UUID[4];
	private BluetoothDevice hostDevice;
	private BluetoothServerSocket bluetoothServerSocket;
	private final BluetoothServerSocket bluetoothServerSockets[] = new BluetoothServerSocket[4];
	private int currentServerSocketIndex;
	private int postfix=0;
	
	public void initServerSocket(){
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		resetSockets();
		Random rand = new Random();
		postfix++;
		//while(true){
			try {
				//bluetoothServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("bebe", appUUID);
				
				bluetoothServerSockets[0] = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("cdp" + String.valueOf(postfix), appUUIDs[0]);
				bluetoothServerSockets[1] = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("cdp" + String.valueOf(postfix), appUUIDs[1]);
				bluetoothServerSockets[2] = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("cdp" + String.valueOf(postfix), appUUIDs[2]);
				bluetoothServerSockets[3] = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("cdp" + String.valueOf(postfix), appUUIDs[3]);
				
				currentServerSocketIndex=0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
	}
	
	public void resetSockets(){
		
		for(BluetoothServerSocket bss : bluetoothServerSockets){
			try {
				bss.close();
			} catch (Exception e) {}
			bss=null;
		}
	}
	
	public void increaseCurrentServerSocketIndex(){
		currentServerSocketIndex++;
	}
	
	public int getCurrentServerSocketIndex() {
		return currentServerSocketIndex;
	}
	
	public BluetoothServerSocket getServerSocket(int index){
		//return bluetoothServerSocket;
		return bluetoothServerSockets[index];
	}
	
	public UUID getUUID(int index){
		//return appUUID;
		return appUUIDs[index];
	}
	
	public BluetoothDevice getHostDevice() {
		return hostDevice;
	}
	
	public void setHostDevice(BluetoothDevice hostDevice) {
		this.hostDevice = hostDevice;
	}
	
	public BluetoothInfo(){						
		//UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		//appUUID =     UUID.fromString("1");
		appUUIDs[0] = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		appUUIDs[1] = new UUID(0x0000110100001000L,0x80000000000001FBL);
		appUUIDs[2] = new UUID(0x0000110100001000L,0x80000000000002FBL);
		appUUIDs[3] = new UUID(0x0000110100001000L,0x80000000000003FBL);
	}

}
