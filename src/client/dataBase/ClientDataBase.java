package client.dataBase;

import java.util.Set;

import logic.client.Game;
import freeplay.FreePlay;


public class ClientDataBase {
	
	private DynamicLoader loader;
	
	
	
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
		loader=new DynamicLoader();

	}
	/**
	 * factory
	 */
	public Game getGame(String gameName){
		
		if (gameName.equals("free play")){
			return new FreePlay();
		}		
		return loader.LoadPlugin(gameName);	
	}
	
	
	public Set<String> getGamesNames(){
		Set<String>gameNames=loader.getGameNames();
		gameNames.add("free play");
		return gameNames;
	}	
}
