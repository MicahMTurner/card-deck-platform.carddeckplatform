package communication.link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;


import carddeckplatform.game.GameStatus;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.widget.TextView;

public class TcpHostFinder implements HostFinder {
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
	
	private ArrayList<String> connectedDevices = new ArrayList<String>();
	
	
	public TcpHostFinder(WifiManager wifii){
		this.wifii = wifii;
	}
	
	public String intToIp(int i, int offset) {

		String str1 = String.valueOf(((i >> 24 ) + offset) & 0xFF);
		String str2 = ((i >> 16 ) & 0xFF) + ".";
	    String str3 = ((i >> 8 ) & 0xFF) + ".";
	    String str4 = ( i & 0xFF)+ ".";
		   return str4 + str3 + str2 + str1;
	}
	
//	public void checkHosts(){
//		   int timeout=100;
//		   for (int i=1;i<254;i++){
//		       try {
//		    	   if (InetAddress.getByName(intToIp(d.gateway,i)).isReachable(timeout)){
//				       System.out.println(intToIp(d.gateway,i) + " is reachable");
//				       connectedDevices.add(intToIp(d.gateway,i));
//		    	   }
//		       } catch (UnknownHostException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//		       } catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//		       }
//		   }
//		}
	
	@Override
	public ArrayList<HostId> findHosts(){
		Socket socket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		HostId hostId = null;
		
		ArrayList<HostId> hosts = new ArrayList<HostId>();
        d=wifii.getDhcpInfo();
//        checkHosts();
//        s_dns1="DNS 1: "+String.valueOf(intToIp(d.dns1,0)) + " " + String.valueOf(d.dns1);
//        s_dns2="DNS 2: "+String.valueOf(intToIp(d.dns2,0)) + " " + String.valueOf(d.dns2);   
//        s_gateway="Default Gateway: "+String.valueOf(intToIp(d.gateway,0)) + " " + String.valueOf(d.gateway);    
//        s_ipAddress="IP Address: "+String.valueOf(intToIp(d.ipAddress,0)) + " " + String.valueOf(d.ipAddress); 
//        s_leaseDuration="Lease Time: "+String.valueOf(d.leaseDuration);     
//        s_netmask="Subnet Mask: "+String.valueOf(intToIp(d.netmask,0)) + " " + String.valueOf(d.netmask);    
//        s_serverAddress="Server IP: "+String.valueOf(intToIp(d.serverAddress,0)) + " " + String.valueOf(d.serverAddress);
		
        // scans the network and try to find hosts
		for(int i=1; i<254; i++){
			try {
				socket = new Socket();
				SocketAddress sockaddr = new InetSocketAddress(intToIp(d.gateway,i), GameStatus.idPort);
				socket.connect(sockaddr, 10);
				System.out.println(intToIp(d.gateway,i) + " is reachable");
				if(!socket.isConnected())
					continue;
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());		
				hostId = (HostId) in.readObject();
				hostId.setAddress(intToIp(d.gateway,i));
				hosts.add(hostId);
				hostId = null;
				out.close();
				in.close();
				socket.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(intToIp(d.gateway,i) + " is not reachable");
				System.out.println(e.getMessage());
			}
		}		
		return hosts;
	}
}
