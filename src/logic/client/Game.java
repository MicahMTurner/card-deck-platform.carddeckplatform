package logic.client;

import java.util.ArrayList;




public abstract class Game {	
	protected static Player me;
	protected static ArrayList<LogicDroppable> droppables;
	ToolsFactory tools=new DefaultTools();
	Deck cards;
	Table table;
	/**
	 * leave empty if want to use default
	 */
	protected abstract void setNewTools();	
	public static ArrayList<LogicDroppable> getDroppables() {
		return droppables;
	}
	
	public static Player getMe() {
		return me;
	}
	
	public abstract GamePrefs getPrefs();
	public abstract GameLogic getLogic();
	
	public void initiate(){
		cards=tools.createCards();	
		
		//table=tools.createTable();	
		
		
		
	}
	
	public Game() {
		
		// TODO Auto-generated constructor stub
	}
	/*
	public void setGameToolsImp(GameToolsImp toolsImp){
		this.toolsImp=toolsImp;
	}	
	public abstract void run();
	
	
	
	
	
	
	
	protected int minimumPlayers;
	ArrayList<Card> unShuffleDeck;
	protected Hashtable<String ,Player> players;
	protected Queue<Card> deck;
	protected String currentTurn;
	protected int round;
	protected PublicArea publicArea;
	public Game(int minimumPlayers) {
		this.minimumPlayers=minimumPlayers;
		unShuffleDeck=createDeck();
		deck=new LinkedList<Card>();
		publicArea=new PublicArea();
	}
	
	//shuffle deck
	//add players to game
	public abstract void setDeck();
	public abstract boolean setStartingPlayer();
	
	public void addPlayer(Player player){
		players.put(player.getUsername(), player);
	}
	
	public boolean startGame(){
		return minimumPlayers<=players.size(); 
	}
	public Hashtable<String ,Player> getPlayers() {
		return players;
	}
	public void endTurn(){
		round++;
	}
	
	public abstract void endGame();
	
	public ArrayList<Card> createDeck() {
		ArrayList<Card>deck=new ArrayList<Card>();
			for (int j=0;j<14;j++){
				deck.add(new Card(j,"H"));
			}
			for (int j=0;j<14;j++){
				deck.add(new Card(j,"C"));
			}
			for (int j=0;j<14;j++){
				deck.add(new Card(j,"S"));
			}
			for (int j=0;j<14;j++){
				deck.add(new Card(j,"D"));
			}
		return deck;
	}
	*/
}
