package client.dataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import president.President;

import freePlaySingle.FreePlaySingle;
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
		//President president=new President();
		games = new HashMap<String, Game>();
		War war = new War();
		
		//games.put(president.toString(), president);
		games.put(war.toString(), war);
		FreePlay freePlay = new FreePlay();
		games.put(freePlay.toString(), freePlay);
		//BlackJack blackJack=new BlackJack();
		//games.put(blackJack.toString(), blackJack);
		FreePlaySingle freePlaySingle = new FreePlaySingle();
		games.put(freePlaySingle.toString(), freePlaySingle);
		
	}
	/**
	 * factory
	 */
	public Game getGame(String gameName){
		try {
			return games.get(gameName).getClass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return loader.LoadPlugin(gameName);	
		return null;
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
