package carddeckplatform.game.gameEnvironment;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Paint;
import android.os.Environment;
import android.os.Handler;

public class GameEnvironment {
	
	public enum ConnectionType{TCP , BLUETOOTH}
	
	private DeviceInfo deviceInfo;
	private TcpInfo tcpInfo;
	private BluetoothInfo bluetoothInfo;
	private PlayerInfo playerInfo;
	private ConnectionType connectionType;	
	private Handler handler;
	private Paint paint;
	private ExecutorService executor;
	public final static String path=Environment.getExternalStoragePublicDirectory("carddeckplatform").getAbsolutePath()+"/";
	
	
	static{
		try {
			new File(path).mkdirs();		
			new File(path + "profiles/").mkdirs();
			new File(path + "plugins/").mkdirs();			
		} catch (Exception e) {
			
		}
	}
	
	
	/*---Singleton implementation---*/
	private static class GameEnvironmentHolder
	{
		private final static GameEnvironment environment=new GameEnvironment();
	}
	
	/**
	 * get Environment instance
	 */
	public static GameEnvironment get(){
		return GameEnvironmentHolder.environment;
	}
	
	
	private GameEnvironment(){
		deviceInfo = new DeviceInfo();
		tcpInfo = new TcpInfo();
		bluetoothInfo = new BluetoothInfo();
		playerInfo = new PlayerInfo();
		executor=Executors.newCachedThreadPool();
		handler = new Handler();
		paint = new Paint();
	}

	public Paint getPaint(){
		return paint;
	}
	
	public ExecutorService getExecutor() {		
		return executor;
	}
	
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
		
}
