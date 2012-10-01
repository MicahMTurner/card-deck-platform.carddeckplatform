package communication.link;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

public class TcpIdListener implements Runnable {
	private final String UDPRCVAUTHENTICATE="cardDeckPlatfromUDPBroadcast";
	private final String UDPRESPONSEAUTH="cardDeckPlatformUDPBroadcastResponse";
	private String owner;
	private String gameName;
	private volatile boolean stop;
	private ServerSocket serverSocket;
	private BroadcastListener broadcast;
	private HostGameDetails gameDetails;
	
	private class BroadcastListener implements Runnable{
		private DatagramSocket socket;
		
		public BroadcastListener() {
			socket=null;
		}
		public void stop(){
			this.socket.close();
		}
		public void start(){
			try {
				socket=new DatagramSocket(GameEnvironment.get().getTcpInfo().getBroadcastPort(),InetAddress.getByName("0.0.0.0"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			new Thread(this).start();
		}
		@Override
		public void run() {
			if (socket!=null){
				try {
					while(!stop){
						//receive a packet
						byte[] recvBuf = new byte[100];
						DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
						socket.receive(packet);
						//check if packet is from our app
						String message=new String(packet.getData()).trim();
						if(message.equals(UDPRCVAUTHENTICATE)){
							byte[] sendData=UDPRESPONSEAUTH.getBytes();
							
							//send response
							DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,packet.getAddress(),packet.getPort());
							socket.send(sendPacket);
						}
						
					}
					
				}catch (IOException e) {			
					e.printStackTrace();
				}

			}			
		}
	
	}
	
	public TcpIdListener(HostGameDetails gameDetails){
		this.gameDetails=gameDetails;
		this.stop=false;
		serverSocket=null;
		this.broadcast=new BroadcastListener();
		
	}
	public void start(){
		stop=false;		
		try {
			serverSocket = new ServerSocket(GameEnvironment.get().getTcpInfo().getIdPort());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
		broadcast.start();
		
	}
	public void stop(){
		stop=true;
		
		try {
			serverSocket.close();
			broadcast.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		
		
		if (serverSocket!=null){
			while(!stop){
				try {
					Socket clientSocket;
					clientSocket = serverSocket.accept();
					System.out.println("Got request from " + clientSocket.getLocalAddress().toString());
					ObjectOutputStream out=new ObjectOutputStream(clientSocket.getOutputStream());
				//	ObjectInputStream in=new ObjectInputStream(clientSocket.getInputStream());
				
					out.writeObject(gameDetails);
					out.close();
					clientSocket.close();
				} catch (IOException e) {}
			}
		}
	}

}
