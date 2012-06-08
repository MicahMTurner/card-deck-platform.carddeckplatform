package carddeckplatform.game.gameEnvironment;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpInfo {
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
