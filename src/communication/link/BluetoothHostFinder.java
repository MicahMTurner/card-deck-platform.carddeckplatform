package communication.link;

import java.util.ArrayList;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class BluetoothHostFinder extends HostFinder {

	@Override
	public void findAvailableHosts(ArrayList<HostId> hosts) {
		// TODO Auto-generated method stub
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        	System.out.println("Device does not support Bluetooth");
        }
		
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		
		
	}

}
