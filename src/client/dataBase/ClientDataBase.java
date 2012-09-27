package client.dataBase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import logic.client.Game;
import president.President;
import war.War;
import durak.Durak;

import freeplay.FreePlay;


import logic.client.Game;


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
		if (gameName.equals("Durak")){
			return new Durak();
		}
		if(gameName.equals("war")){
			return new War();
		}
		if(gameName.equals("president")){
			return new President();
		}
		
		return loader.LoadPlugin(gameName);	
	}
	
	
	public Set<String> getGamesNames(){
		Set<String>gameNames=loader.getGameNames();
		gameNames.add("free play");
		gameNames.add("Durak");
		gameNames.add("war");
		gameNames.add("president");
		return gameNames;
	}	
}
