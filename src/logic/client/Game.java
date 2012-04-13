package logic.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import war.actions.TurnAction;

import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;

import android.content.Context;




public abstract class Game {	
	//i'm first in the list
	private ArrayList<Player> players = new ArrayList<Player>();
	protected static Queue<Player.Position> turnsQueue=new LinkedList<Player.Position>();
	protected static ArrayList<LogicDroppable> droppables = new ArrayList<LogicDroppable>();
	
	ToolsFactory tools=new DefaultTools();
	Deck cards;
	Table table;
	/**
	 * leave empty if want to use default
	 */
	protected abstract void setNewTools();	
	public abstract void setTurns();
	
	public static ArrayList<LogicDroppable> getDroppables() {
		return droppables;
	}
	
	public Player getMe() {
		return players.get(0);
	}
	public void addMe(){
		players.add(GameStatus.me);
	}
	public abstract GamePrefs getPrefs();
	public abstract GameLogic getLogic();
	
	@Override
	/**
	 * returns the game id
	 */
	public abstract String toString();
	
	public void initiate(){
		cards=tools.createCards();	
	}
	public Deck getCards() {
		return cards;
	}
	public abstract void buildLayout(Context context, TableView tv, Player.Position position);
	
	public Game() {
		setTurns();
	}
	public static Player.Position nextInTurn(){
		Player.Position next=turnsQueue.poll();
		turnsQueue.add(next);
		return next;
		
	}
	
	public void addPlayer(Player newPlayer) {
		players.add(newPlayer);
		
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}

}
