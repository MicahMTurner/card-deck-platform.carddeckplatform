package client.dataBase;

import java.util.Set;


import freeplay.FreePlay;

import logic.client.Game;


public class ClientDataBase {
	
	private DynamicLoader loader;
	
	Partners  please  use tickets on the google site, if you start working on something. so we can know who works on each subject 
	
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
