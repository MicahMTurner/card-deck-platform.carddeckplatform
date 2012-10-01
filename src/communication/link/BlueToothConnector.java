package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import carddeckplatform.game.gameEnvironment.GameEnvironment;

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
		
//		mBluetoothAdapter.disable();
//		mBluetoothAdapter.enable();
	}
	@Override
	public Streams connect() {
		BluetoothSocket tmp = null;
		
		
		String str = device.getName();
		String str2 =  device.getAddress();
		
		
		while(currentUUIDIndex<3){
			try {
	            // MY_UUID is the app's UUID string, also used by the server code
//	            tmp = device.createRfcommSocketToServiceRecord(destinationId);
				
				Method m = device.getClass().getMethod("createRfcommSocketToServiceRecord", new Class[] {int.class});
		         tmp = (BluetoothSocket) m.invoke(device, destinationId);
//	        } catch (IOException e) {} catch (SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			socket = tmp;
			
			try {

				
//				Thread.sleep(10000);
				
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
			in.close();
			in=null;
			System.out.println("--------BLUETOOTH in disconnected----------");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("--------BLUETOOTH in didn't disconnected----------");
		}
		
		try {
			out.close();	
			out=null;

			System.out.println("--------BLUETOOTH out disconnected----------");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("--------BLUETOOTH out didn't disconnected----------");
		}
		
		try {
			socket.close();
			socket=null;
			System.out.println("--------BLUETOOTH socket disconnected----------");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("--------BLUETOOTH socket didn't disconnected----------");
		}
	}

}
