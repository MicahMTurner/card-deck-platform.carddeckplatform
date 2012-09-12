package carddeckplatform.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import client.gui.screens.GameScreen;
import client.ranking.db.Game;
import client.ranking.db.Player;
import client.ranking.db.Round;
import client.ranking.db.ScoringManager;

public class ScoringActivity extends Activity {
	static String DBNAME="scoring";
	ScoringManager scoringManager;
	Game [] games;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scorelayout);
		TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
		tabHost.setup();
		
		
		TabSpec spec1=tabHost.newTabSpec("Tab 1");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Tab 1");

		TabSpec spec2=tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("Tab 2");
		spec2.setContent(R.id.tab2);
		
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		
		TableLayout tl=(TableLayout) findViewById(R.id.scoringtable);
		
		//making the database manager
		scoringManager = new ScoringManager(ScoringActivity.this, DBNAME);
		scoringManager.open();
//		games=scoringManager.showAllGamesAndLastRounds();
		games=makeFictiveGames();
		scoringManager.close();
		
		initScoringTable(games,tl);
		
	}
	
	private Game[] makeFictiveGames() {
		Game [] games= new Game[20];
		long gameId=1,roundId=1;
		String gameType="RankableGame";
		Random rand= new Random();
		
		for(int i=0;i<games.length;i++){
			ArrayList<Player> players= new ArrayList<Player>();
			players.add(new Player("Yoav", rand.nextInt(200)));
			players.add(new Player("David", rand.nextInt(200)));
			players.add(new Player("Michael", rand.nextInt(200)));
			games[i]= new Game(gameId++, roundId, players, new Date().toString(), gameType);
			roundId+=10;
		}
	

		return games;
	}

	public void initScoringTable(Game [] games,TableLayout tl){
		TableRow tr= new TableRow(this);
		
		//Type
		Button b=new Button(this);
		b.setText("Type :");
		tr.addView(b);
		//Date
		b=new Button(this);
		b.setText("Date :");
		tr.addView(b);
		//Players
		b=new Button(this);
		b.setText("Players :");
		tr.addView(b);
		for(int i=0;i<games.length;i++){
			final Game game2=games[i];
			tr=new TableRow(tl.getContext());
			//type
			TextView tv=new TextView(this);
			tv.setText(game2.getGameType());
			tr.addView(tv);
			//date
			tv=new TextView(this);
			tv.setText(game2.getDate());
			tr.addView(tv);
			//players
			tv=new TextView(this);
			tv.setText(game2.getPlayersInfo());
			tr.addView(tv);
			tr.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Dialog dialog = new Dialog(ScoringActivity.this);
					ScrollView scrollView = new ScrollView(ScoringActivity.this);		
					TableLayout table= new TableLayout(ScoringActivity.this);
					table.setColumnStretchable(0,true);
					table.setColumnStretchable(10,true);
					table.setShrinkAllColumns(true);
					LayoutParams params=new LayoutParams(700, 500);
	            	dialog.setContentView(scrollView,params);
//	            	Round [] rounds=scoringManager.showAllRoundsRounds(game2);
	            	Round [] rounds=getFictiveRounds();
	            	for (int i = 0; i < rounds.length; i++) {
						TableRow tr= new TableRow(ScoringActivity.this);
						TextView tv=new TextView(ScoringActivity.this);
						tv.setText(rounds[i].getDate());
						tr.addView(tv);
						for(int j=0;i<rounds[j].getRoundResult().size();j++){
							tv=new TextView(ScoringActivity.this);
							StringBuilder sb=new StringBuilder();
							sb.append(rounds[j].getRoundResult().get(j).getUserName());
							sb.append("(");
							sb.append(rounds[j].getRoundResult().get(j).getScore());
							sb.append(")");
							tv.setText(sb.toString());
							tr.addView(tv);
						}
					}
	            	
				}
			});
		}
		
	}
	Round [] getFictiveRounds(){
		Round [] rounds= new Round[10];
		Random rand= new Random();
		for(int i=0;i<rounds.length;i++){
			rounds[i]= new Round(new Date().toString());
			rounds[i].addUserResult(new Player("Yoav", rand.nextInt(200)));
			rounds[i].addUserResult(new Player("David", rand.nextInt(200)));
			rounds[i].addUserResult(new Player("Michael", rand.nextInt(200)));
		}
		return rounds;
	}
	@Override
	protected void onStart() {
		super.onStart();
		scoringManager.open();
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		scoringManager.close();
	}
}
