package communication.server;



import java.util.ArrayList;

public class ServerConnections {
	
	private static ArrayList<ServerTask> connections = new ArrayList<ServerTask>();
	
	public static void addConnection(ServerTask serverTask){
		connections.add(serverTask);
	}
	
	public static ArrayList<ServerTask> getAllConections(){
		return connections;
	}
	
	
	
}
