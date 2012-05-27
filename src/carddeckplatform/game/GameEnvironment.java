package carddeckplatform.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.os.Handler;

public class GameEnvironment {
	public enum ConnectionType{TCP , BLUETOOTH}
	
	private DeviceInfo deviceInfo;
	private TcpInfo tcpInfo;
	private BluetoothInfo bluetoothInfo;
	private PlayerInfo playerInfo;
	private ConnectionType connectionType;
	
	private Handler handler;
	
	public Handler getHandler() {
		return handler;
	}
	
	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}
	
	public ConnectionType getConnectionType() {
		return connectionType;
	}
	
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}


	public TcpInfo getTcpInfo() {
		return tcpInfo;
	}


	public BluetoothInfo getBluetoothInfo() {
		return bluetoothInfo;
	}


	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	private static class GameEnvironmentHolder
	{
		private final static GameEnvironment environment=new GameEnvironment();
	}
	
	/**
	 * get Environment instance
	 */
	public static GameEnvironment getGameEnvironment(){
		return GameEnvironmentHolder.environment;
	}
	
	
	private GameEnvironment(){
		deviceInfo = new DeviceInfo();
		tcpInfo = new TcpInfo();
		bluetoothInfo = new BluetoothInfo();
		playerInfo = new PlayerInfo();
		
		handler = new Handler();
	}
	
	public class DeviceInfo{
		private int screenWidth;
		private int screenHeight;
		public int getScreenWidth() {
			return screenWidth;
		}
		public void setScreenWidth(int screenWidth) {
			this.screenWidth = screenWidth;
		}
		public int getScreenHeight() {
			return screenHeight;
		}
		public void setScreenHeight(int screenHeight) {
			this.screenHeight = screenHeight;
		}
		
		
	}
	
	public class TcpInfo{
		private String hostIp;
		private String localIp;
		private int hostPort = 9997;
		private int idPort = 9998;
		
		private ServerSocket tcpServerSocket;
		
		public void initServerSocket(){
			try {
				tcpServerSocket = new ServerSocket(hostPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		public ServerSocket getServerSocket(){
			return tcpServerSocket;
		}
		
		public String getHostIp() {
			return hostIp;
		}
		public void setHostIp(String hostIp) {
			this.hostIp = hostIp;
		}
		public String getLocalIp() {
			return localIp;
		}
		public void setLocalIp(String localIp) {
			this.localIp = localIp;
		}
		public int getHostPort() {
			return hostPort;
		}
		public int getIdPort() {
			return idPort;
		}
		
		
	}
	
	public class BluetoothInfo{
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
	
	public class PlayerInfo{
		private boolean isServer;
		private String username;
		
		
		public boolean isServer() {
			return isServer;
		}
		public void setServer(boolean isServer) {
			this.isServer = isServer;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
		
	}
	
}
