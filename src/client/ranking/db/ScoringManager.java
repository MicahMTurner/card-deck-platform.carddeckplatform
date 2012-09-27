package client.ranking.db;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoringManager {

	RankQLiteOpenHelper dbHelper = null;
	private SQLiteDatabase database;

	public ScoringManager(Context context,String dbName) {
		if (dbHelper == null)
			dbHelper = new RankQLiteOpenHelper(context, dbName, null, 1);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();

	}

	public void close() {
		dbHelper.close();
	}

	public Game createNewGame(ArrayList<Player> players,String gameType) {
		// making the game
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		ContentValues initialValues = new ContentValues();
		String fDate = dateFormat.format(date);
		initialValues.put(GamesColumns.GAME_DATE_CREATED, fDate);
		initialValues.put(GamesColumns.GAME_TYPE, gameType);
		long gameId = database.insert(Tables.GAMES, null, initialValues);
		// inserting users to User Table
		for (Player player : players) {
			ContentValues user = new ContentValues();
			user.put(UsersColumns.USER_NAME, player.getUserName());
			player.setUserId(database.insert(Tables.USERS, null, user));
		}
		// inserting round row
		ContentValues round = new ContentValues();
		round.put(RoundsColumns.ROUND_DCREATED, fDate);
		round.put(RoundsColumns.ROUND_DUPDATED, fDate);
		round.put(RoundsColumns.ROUND_GAME_ID, gameId);

		long roundId = database.insert(Tables.ROUNDS, null, round);

		// inserting round details
		for (Player player : players) {
			ContentValues score = new ContentValues();
			score.put(RoundsDetailsColumns.RDETAIL_POINT, player.getScore());
			score.put(RoundsDetailsColumns.RDETAIL_USERID, player.getUserId());
			score.put(RoundsDetailsColumns.RDETAIL_ID, roundId);
			database.insert(Tables.ROUNDS_DETAILS, null, score);
		}

		return new Game(gameId, roundId, players,fDate,gameType);

	}

	public void makeNewRound(Game game, ArrayList<Long> newScores)
			throws Exception {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String fDate = dateFormat.format(date);
		// inserting round row
		ContentValues round = new ContentValues();
		round.put(RoundsColumns.ROUND_DCREATED, fDate);
		round.put(RoundsColumns.ROUND_DUPDATED, fDate);
		round.put(RoundsColumns.ROUND_GAME_ID, game.getGameId());

		long roundId = database.insert(Tables.ROUNDS, null, round);

		// inserting round details
		ArrayList<Player> players = game.getPlayers();
		if (newScores.size() != players.size())
			throw new Exception("Number of Players is not equal to the number of newScores");

		for (int i = 0; i < players.size(); i++)
			players.get(i).setScore(newScores.get(i));
		for (Player player : players) {
			ContentValues score = new ContentValues();
			score.put(RoundsDetailsColumns.RDETAIL_POINT, player.getScore());
			score.put(RoundsDetailsColumns.RDETAIL_USERID, player.getUserId());
			score.put(RoundsDetailsColumns.RDETAIL_ID, roundId);
			database.insert(Tables.ROUNDS_DETAILS, null, score);
		}

	}
	
//	public ArrayList<Game> showAllGames(){
//		Cursor cursor=database.rawQuery("Select "+GamesColumns.GAME_DATE_CREATED+","+GamesColumns.GAME_ID+","+GamesColumns.GAME_TYPE+
//										" from "+Tables.GAMES, null);
//		ArrayList<Game> games= new ArrayList<Game>();
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			String name = cursor.getString(0);
//			games.add(new Game(cursor.getLong(1), cursor.getString(0)));
//			System.out.println(name);
//			cursor.moveToNext();
//		}
//		return games;
//		
//		
//	}
//	public ArrayList<Game> showAllGamesByType(String type){
//		Cursor cursor=database.rawQuery("Select "+GamesColumns.GAME_DATE_CREATED+","+GamesColumns.GAME_ID+","+GamesColumns.GAME_TYPE+
//										" from "+Tables.GAMES
//										+" where "+GamesColumns.GAME_TYPE+"="+type
//										, null);
//		ArrayList<Game> games= new ArrayList<Game>();
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			String name = cursor.getString(0);
//			games.add(new Game(cursor.getLong(1), cursor.getString(0)));
//			System.out.println(name);
//			cursor.moveToNext();
//		}
//		return games;
		
		
//	}
	public void showLastRounds(){
		Cursor cursor=database.rawQuery(
			"select "+RoundsColumns.ROUND_ID+ ","+RoundsColumns.ROUND_GAME_ID+ ","+"max("+RoundsColumns.ROUND_DUPDATED+")"+
			" from "+Tables.ROUNDS +
			" GROUP BY "+RoundsColumns.ROUND_GAME_ID
			, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			System.out.println(cursor.getString(0));
			cursor.moveToNext();
		}
	}
	public Round[] showAllRoundsRounds(Game game){
		Cursor cursor=database.rawQuery(
				"select R.roundId,R.dateUpdated,UD.userName,UD.point "+
				" from "+
				"( "+
				"select roundId,dateUpdated "+
				"from Rounds "+
				"where gameId="+game.getGameId()+
				") "+
				"R "+
				"join "+
				"( "+
				"select RD.userId,U.userName,RD.roundId,RD.point point "+
				"from RoundsDetails RD join Users U on RD.userId=U.userId "+
				") "+
				"UD "+
				"on UD.roundId=R.roundId "+
				"Order By R.dateUpdated"
				,null);
		HashMap<Long, Round> rounds=new HashMap<Long, Round>();
		ArrayList<Long> roundsId=new ArrayList<Long>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			long roundId=cursor.getLong(0);
			if(rounds.containsKey(roundId)){
				rounds.get(roundsId).addUserResult(new Player(cursor.getString(2), cursor.getInt(3)));
			}
			else{
				Round round=new Round(cursor.getString(1));
				round.addUserResult(new Player(cursor.getString(2), cursor.getInt(3)));
				rounds.put(roundId, round);
				roundsId.add(roundId);
			}
			cursor.moveToNext();
		}
		
		Round [] roundsArr=new Round[roundsId.size()] ;
		for(int i=0;i<roundsId.size();i++)
			roundsArr[i]=rounds.get(roundsId.get(i));
		
		return roundsArr;
	}
	
	
	/**
	 * 
	 *  select
		AllRound.roundId RoundId,AllRound.gameId gameid,AllRound.type type,AllRound.date date,RD.userId,U.userName,RD.point
		from
		(
		select
		R.roundId,R.gameId, max(R.dateUpdated) date,g.gameType type
		from
		GAMES g join ROUNDS R on G.gameId=R.gameId
		group by R.gameId
		)
		
		AllRound
		join
		RoundsDetails RD on AllRound.roundId=RD.roundId
		join
		USERS U on RD.userId=U.userId
		ORDER BY AllRound.date DESC

	 * 
	 * 
	 * 
	 * @param game
	 */
	
	
	
	public Game [] showAllGamesAndLastRounds(){
		Cursor cursor=database.rawQuery(
				"select "+
				"AllRound.roundId RoundId,AllRound.gameId gameid,AllRound.type type,AllRound.date date,RD.userId,U.userName,RD.point "+
				"from "+
				"( "+
				"select "+
				"R.roundId,R.gameId, max(R.dateUpdated) date,g.gameType type "+
				"from "+
				"GAMES g join ROUNDS R on G.gameId=R.gameId "+
				"group by R.gameId "+
				") "+

				"AllRound "+
				"join "+
				"RoundsDetails RD on AllRound.roundId=RD.roundId "+
				"join "+
				"USERS U on RD.userId=U.userId "+
				"ORDER BY AllRound.date DESC "
				
				,null);
		
		
		
		
		
		
		cursor.moveToFirst();
		HashMap<Long, Game> games= new HashMap<Long, Game>();
		while (!cursor.isAfterLast()){
			long gameId=cursor.getLong(1);
			if(!games.containsKey(gameId)){
				long roundId=cursor.getLong(0);
				String gameType=cursor.getString(2);
				String date=cursor.getString(3);
				ArrayList<Player> players=new ArrayList<Player>();
				players.add(new Player(cursor.getString(5), cursor.getInt(6)));
				Game game= new Game(gameId, roundId, players, date, gameType);
				games.put(gameId, game);
			}
			else
				games.get(gameId).add(new Player(cursor.getString(5), cursor.getInt(6)));
			
			cursor.moveToNext();
		}
		Game [] gameArray=new Game[games.values().size()];
		games.values().toArray(gameArray);
		return gameArray;
	}
	
	
	
	public void removeGame(Game game){
		String [] columns={ RoundsColumns.ROUND_ID};
		Cursor cursor =database.query(Tables.ROUNDS, columns, RoundsColumns.ROUND_GAME_ID+"="+game.getGameId(), null, null, null, null);
		database.delete(Tables.GAMES,GamesColumns.GAME_ID+"="+game.getGameId() 	, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			long roundId=cursor.getLong(0);
			System.out.println("roundId:"+roundId);
			database.delete(Tables.ROUNDS_DETAILS,RoundsDetailsColumns.RDETAIL_ID+"="+roundId, null);
			cursor.moveToNext();
		}
		database.delete(Tables.ROUNDS,RoundsColumns.ROUND_GAME_ID+"="+game.getGameId() 	, null);
	}
	

	/********************************************************************************************************
	 * 
	 * internal classes +interfaces
	 * 
	 * 
	 ******************************************************************************************************** 
	 * */

	public class RankQLiteOpenHelper extends SQLiteOpenHelper {

		public RankQLiteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE " + Tables.USERS + " ("
					+ UsersColumns.USER_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ UsersColumns.USER_NAME + " TEXT nvarchar(50) NOT NULL"
					+ ")");

			db.execSQL("CREATE TABLE " + Tables.GAMES + " ("
					+ GamesColumns.GAME_ID + " INTEGER PRIMARY KEY,"
					+ GamesColumns.GAME_DATE_CREATED + "datetime,"
					+ GamesColumns.GAME_TYPE +" TEXT nvarchar(50) NOT NULL"
					+ ")");

			db.execSQL("CREATE TABLE " + Tables.ROUNDS + " ("
					+ RoundsColumns.ROUND_ID + " INTEGER PRIMARY KEY,"
					+ RoundsColumns.ROUND_GAME_ID + "INTEGER, "
					+ RoundsColumns.ROUND_DCREATED + " datetime,"
					+ RoundsColumns.ROUND_DUPDATED + " datetime,"
					+ " FOREIGN KEY(" + RoundsColumns.ROUND_GAME_ID
					+ ") REFERENCES " + Tables.GAMES + "("
					+ GamesColumns.GAME_ID + ")"

					+ ")");
			db.execSQL("CREATE TABLE " + Tables.ROUNDS_DETAILS + " ("
					+ RoundsDetailsColumns.RDETAIL_ID + " INTEGER,"
					+ RoundsDetailsColumns.RDETAIL_POINT + " INTEGER,"
					+ RoundsDetailsColumns.RDETAIL_USERID + " INTEGER,"
					+ "PRIMARY KEY (" + RoundsDetailsColumns.RDETAIL_ID + ","
					+ RoundsDetailsColumns.RDETAIL_USERID + ")" + ")");

			System.out.println("created database");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + Tables.GAMES);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.USERS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.ROUNDS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.ROUNDS_DETAILS);
			onCreate(db);
			System.out.println("updated");

		}

	}

	interface Tables {
		String USERS = "Users";
		String GAMES = "Games";
		String ROUNDS = "Rounds";
		String ROUNDS_DETAILS = "RoundsDetails";
	}

	interface UsersColumns {
		String USER_ID = "userId";
		String USER_NAME = "userName";
	}

	interface GamesColumns {
		String GAME_DATE_CREATED = "dateCreated ";
		String GAME_ID = "gameId";
		String GAME_TYPE = "gameType";
	}

	interface RoundsColumns {
		String ROUND_ID = "roundId";
		String ROUND_GAME_ID = "gameId ";
		String ROUND_DCREATED = "dateCreated";
		String ROUND_DUPDATED = "dateUpdated ";
	}

	interface RoundsDetailsColumns {
		String RDETAIL_ID = "roundId";
		String RDETAIL_USERID = "userId";
		String RDETAIL_POINT = "point";
	}

}
