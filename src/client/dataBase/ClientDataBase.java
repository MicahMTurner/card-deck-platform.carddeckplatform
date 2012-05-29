package client.dataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import freeplay.FreePlay;

import war.War;
import logic.client.Game;


public class ClientDataBase {
	private HashMap<String, Game> games;
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
		games = new HashMap<String, Game>();
		War war = new War();
		games.put(war.toString(), war);
		FreePlay freePlay = new FreePlay();
		games.put(freePlay.toString(), freePlay);
		//BlackJack blackJack=new BlackJack();
		//games.put(blackJack.toString(), blackJack);
		
	}
	/**
	 * factory
	 */
	public Game getGame(String gameName){
		//return games.get(gameName);
		//return loader.LoadPlugin(gameName);
		//return new FreePlay();
		return new War();
	}
	
	public void addGame(String gameId){
		
	}
	
	public Set<String> getGamesNames(){
		Set<String> gameNames =new HashSet<String>();
		for(String gameName : games.keySet()){
			gameNames.add(gameName);
		}
		//Set<String>gameNames=loader.getGameNames();
		return gameNames;
	}	
}
