package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.widget.TextView;
import carddeckplatform.game.gameEnvironment.GameEnvironment;

public class TcpHostFinder extends HostFinder{
	private final String UDPSENDAUTHENTICATE="cardDeckPlatfromUDPBroadcast";
	private final String UDPRESPONSEAUTH="cardDeckPlatformUDPBroadcastResponse";
	
	public String   s_dns1 ;
	public String   s_dns2;     
	public String   s_gateway;  
	public String   s_ipAddress;    
	public String   s_leaseDuration;    
	public String   s_netmask;  
	public String   s_serverAddress;
	TextView info;
	DhcpInfo d;
	WifiManager wifii;
	DatagramSocket socket;
	
	private ArrayList<String> connectedDevices = new ArrayList<String>();
	
	
	public TcpHostFinder(WifiManager wifii){
		this.wifii = wifii;
		try {
			socket=new DatagramSocket();				
			socket.setBroadcast(true);
		} catch (SocketException e) {			
			e.printStackTrace();
		}
	}

	@Override
	public void findAvailableHosts(ArrayList<HostGameDetails> hosts){
		broadcast();
		//while (!stop){
		//	hosts.add(waitForResponse());
		//}
		
	}

	private void waitForResponse() {
		try{
			byte[] rcvBuffer = new byte[15000];
			DatagramPacket rcvPacket = new DatagramPacket(rcvBuffer, rcvBuffer.length);
			socket.receive(rcvPacket);
		
			//got a response, check if message is valid
			String message = new String(rcvPacket.getData()).trim();
			if (message.equals(UDPRESPONSEAUTH)){
				
				getHostId(rcvPacket.getAddress(),rcvPacket.getPort());
			}
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void getHostId(InetAddress inetAddress,int port) {
		try {
			Socket socket;
		
			socket = new Socket(inetAddress,port);		
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			HostGameDetails hostId=(HostGameDetails) in.readObject();
			//hostId.setAddress(inetAddress.getHostAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void broadcast() {
		try {
			byte[] sendData=UDPSENDAUTHENTICATE.getBytes();
			//tyring broadcasting to 255.255.255.255
			try{
				DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length,InetAddress.getByName("255.255.255.255")
															,GameEnvironment.get().getTcpInfo().getBroadcastPort());
				socket.send(sendPacket);
			}catch (UnknownHostException e) {			
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//broadcast all over the network intefaces
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()){
				NetworkInterface networkInterface = interfaces.nextElement();
				
				if (networkInterface.isLoopback() || !networkInterface.isUp()){
					//don't broadcast to loopback interfaces or interfaces that are down
					continue;
				}
				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()){
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast==null){
						continue;
					}
					//send broadcast package
					try {
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,broadcast
																	,GameEnvironment.get().getTcpInfo().getBroadcastPort());
						
						socket.send(sendPacket);
					} catch (IOException e) {
					
						e.printStackTrace();
					}
				}
			}
		socket.close();	
		} catch (SocketException e) {
			e.printStackTrace();
		} 
	
	}
}
