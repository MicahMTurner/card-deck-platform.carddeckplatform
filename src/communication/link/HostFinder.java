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
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

import android.net.wifi.WifiManager;

public class HostFinder extends Observable implements Runnable{
	private final String UDPSENDAUTHENTICATE="cardDeckPlatfromUDPBroadcast";
	private final String UDPRESPONSEAUTH="cardDeckPlatformUDPBroadcastResponse";	
	private ArrayList<HostGameDetails> hosts;
	//private CountDownLatch cdl;
	private DatagramSocket socket;
	private volatile boolean stop;
	
	private class Broadcast extends TimerTask{		
		@Override
		public void run() {
			//try {
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
			//***----***DO NOT DELETE THIS***----***//
//			//broadcast all over the network intefaces
//			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//			while (interfaces.hasMoreElements()){
//				NetworkInterface networkInterface = interfaces.nextElement();
//				
//				if (networkInterface.isLoopback() || !networkInterface.isUp()){
//					//don't broadcast to loopback interfaces or interfaces that are down
//					continue;
//				}
//				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()){
//					InetAddress broadcast = interfaceAddress.getBroadcast();
//					if (broadcast==null){
//						continue;
//					}
//					//send broadcast package
//					try {
//						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,broadcast
//																	,GameEnvironment.get().getTcpInfo().getBroadcastPort());
//						
//						socket.send(sendPacket);
//					} catch (IOException e) {
//					
//						e.printStackTrace();
//					}
//				}
//			}
		
//		} catch (SocketException e) {
//			e.printStackTrace();
//		} 
	
		}
			
	}
		
	
	public HostFinder() {
		this.stop=true;
		hosts=new ArrayList<HostGameDetails>();
		//this.cdl=new CountDownLatch(1);	
		try {
			socket=new DatagramSocket();				
			socket.setBroadcast(true);
		} catch (SocketException e) {			
			e.printStackTrace();
		}
	}
	public void stop(){
		stop=true;
	}
	
	public void findAvailableHosts(ArrayList<HostGameDetails> hosts){
		Timer timer=new Timer();
		timer.schedule(new Broadcast(),0,500);
		//broadcast();
		while (!stop){
			waitForResponse();
		}
		timer.cancel();
		socket.close();	
	}
	
	private void waitForResponse() {
		try{			
			byte[] rcvBuffer = new byte[100];
			DatagramPacket rcvPacket = new DatagramPacket(rcvBuffer, rcvBuffer.length);
			socket.receive(rcvPacket);
		
			//got a response, check if message is valid
			String message = new String(rcvPacket.getData()).trim();
			if (message.equals(UDPRESPONSEAUTH)){
				
				getHostId(rcvPacket.getAddress());
			}			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void getHostId(InetAddress inetAddress) {
		try {
			Socket socket;
		
			socket = new Socket(inetAddress,GameEnvironment.get().getTcpInfo().getIdPort());		
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			HostGameDetails hostId=(HostGameDetails) in.readObject();
			//hostId.setAddress(inetAddress.getHostAddress());
			this.setChanged();
			this.notifyObservers(hostId);
			in.close();
			socket.close();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
		
		
	}

	private void broadcast() {
		//try {
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
			//***----***DO NOT DELETE THIS***----***//
//			//broadcast all over the network intefaces
//			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//			while (interfaces.hasMoreElements()){
//				NetworkInterface networkInterface = interfaces.nextElement();
//				
//				if (networkInterface.isLoopback() || !networkInterface.isUp()){
//					//don't broadcast to loopback interfaces or interfaces that are down
//					continue;
//				}
//				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()){
//					InetAddress broadcast = interfaceAddress.getBroadcast();
//					if (broadcast==null){
//						continue;
//					}
//					//send broadcast package
//					try {
//						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,broadcast
//																	,GameEnvironment.get().getTcpInfo().getBroadcastPort());
//						
//						socket.send(sendPacket);
//					} catch (IOException e) {
//					
//						e.printStackTrace();
//					}
//				}
//			}
		
//		} catch (SocketException e) {
//			e.printStackTrace();
//		} 
	
	}
	
	public ArrayList<HostGameDetails> findHosts(){	
		stop=false;
		new Thread(this).start();
		//try {
		//	cdl.await();
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//}
		return hosts;
	}
	
	@Override
	public void run() {
		findAvailableHosts(hosts);
		//cdl.countDown();
	}
	
}
