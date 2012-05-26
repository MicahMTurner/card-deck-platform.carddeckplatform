package carddeckplatform.game;

public class GameEnvironment {
	private DeviceInfo deviceInfo;
	private TcpInfo tcpInfo;
	private BluetoothInfo bluetoothInfo;
	private PlayerInfo playerInfo;
	
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
