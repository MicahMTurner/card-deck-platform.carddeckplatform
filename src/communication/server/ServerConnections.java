package communication.server;



import java.util.ArrayList;

public class ServerConnections {
	
	private static ArrayList<ConnObj> connections = new ArrayList<ConnObj>();
	
	public static void addConnection(ConnObj connObj){
		connections.add(connObj);
	}
	
	public static ArrayList<ConnObj> getAllConections(){
		return connections;
	}
	
	
	
}
