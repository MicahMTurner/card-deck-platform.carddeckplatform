package communication.server;

import java.util.HashMap;

import communication.entities.Client;

public class ClientMapping {
	private static HashMap<String , Client> clients = new HashMap<String , Client>();
	
	/**
	 * addClient - adds client to the list.
	 * @param client
	 */
	public static void addClient(Client client){
		clients.put(client.getName(), client);
	}
	
	/**
	 * getClient - gets a client by its name.
	 * @param name
	 * @return Client
	 */
	public static Client getClient(String name){
		return clients.get(name);
	}
	
	/**
	 * getAllClients - gets all clients.
	 * @return Client[]
	 */
	public static Client[] getAllClients(){
		int size = clients.size();
		Client[] clientArray = new Client[size];
		int i=0;
		for(Client c : clients.values()){
			clientArray[i] = c;
			i++;
		}
		return clientArray;
	}
	
}
