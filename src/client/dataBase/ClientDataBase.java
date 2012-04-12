package client.dataBase;

import war.War;
import logic.client.Game;


public class ClientDataBase {
	//-------Singleton implementation--------//
	private static class DataBaseHolder
	{
		private final static ClientDataBase dataBase=new ClientDataBase();
	}
			
					
	/**
	 * get Client Data base instance
	 */
	public static ClientDataBase getDataBase(){
		return DataBaseHolder.dataBase;
	}
	private ClientDataBase() {

	}
	/**
	 * factory
	 */
	public Game getGame(String gameId){
		return new War();
	}
	
	public void addGame(String gameId){
		
	}
			
}
