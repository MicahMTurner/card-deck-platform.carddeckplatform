package carddeckplatform.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
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
	static public String DBNAME = "scoring";
	ScoringManager scoringManager;
	
	Game[] games;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scorelayout);
		// TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
		// tabHost.setup();
		//
		//
		// TabSpec spec1=tabHost.newTabSpec("Tab 1");
		// spec1.setContent(R.id.tab1);
		// spec1.setIndicator("Tab 1");
		//
		// TabSpec spec2=tabHost.newTabSpec("Tab 2");
		// spec2.setIndicator("Tab 2");
		// spec2.setContent(R.id.tab2);
		//
		// tabHost.addTab(spec1);
		// tabHost.addTab(spec2);

		TableLayout tl = (TableLayout) findViewById(R.id.scoringtable);

		// making the database manager
		scoringManager = new ScoringManager(ScoringActivity.this, DBNAME);
		scoringManager.open();
		// games=scoringManager.showAllGamesAndLastRounds();
		games = makeFictiveGames();
		scoringManager.close();

		initScoringTable(games, tl);

	}

	private Game[] makeFictiveGames() {
		Game[] games = new Game[20];
		long gameId = 1, roundId = 1;
		String gameType = "RankableGame";
		Random rand = new Random();

		for (int i = 0; i < games.length; i++) {
			ArrayList<Player> players = new ArrayList<Player>();
			players.add(new Player("Yoav", rand.nextInt(200)));
			players.add(new Player("David", rand.nextInt(200)));
			players.add(new Player("Michael", rand.nextInt(200)));
			games[i] = new Game(gameId++, roundId, players,
					new Date().toString(), gameType);
			roundId += 10;
		}

		return games;
	}
	private static Float scale=(float) 1;
	
	public static int dpToPixel(int dp, Context context) {
	    if (scale == null)
	        scale = context.getResources().getDisplayMetrics().density;
	    return (int) ((float) dp * scale);
	}
	
	public void initScoringTable(Game[] games, TableLayout tl) {
		//layout params
		TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.FILL_PARENT,
				TableLayout.LayoutParams.WRAP_CONTENT);
		
		int leftMargin = 10;
		int topMargin = 2;
		int rightMargin = 10;
		int bottomMargin = 2;

		tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
				bottomMargin);
		int headerFontSize=23;
		int fontSize=20;
		//tablerow params
		final android.widget.TableRow.LayoutParams tableRowparams = new android.widget.TableRow.LayoutParams();
		tableRowparams.rightMargin = dpToPixel(10, ScoringActivity.this); // right-margin = 10dp
		
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(tableRowParams);
				// Type
		TextView tv = new TextView(this);
		tv.setText("Type :");
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, headerFontSize);
		tr.addView(tv);
		// Date
		tv = new TextView(this);
		tv.setText("Date :");
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, headerFontSize);
		tr.addView(tv);
		// Players
		tv = new TextView(this);
		tv.setText("Players :");
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, headerFontSize);
		tr.addView(tv);
		
		tl.addView(tr);
		for (int i = 0; i < games.length; i++) {
			final Game game2 = games[i];
			tr = new TableRow(tl.getContext());
			tr.setLayoutParams(tableRowParams);
			// type
			tv = new TextView(this);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
			tv.setText(game2.getGameType());
			tv.setLayoutParams(tableRowparams);
			tr.addView(tv);
			// date
			tv = new TextView(this);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
			tv.setText(game2.getDate());
			tv.setLayoutParams(tableRowparams);
			tr.addView(tv);
			// players
			tv = new TextView(this);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
			tv.setText(game2.getPlayersInfo());
			tv.setLayoutParams(tableRowparams);
			tr.addView(tv);
			tr.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Dialog dialog = new Dialog(ScoringActivity.this);
					ScrollView scrollView = new ScrollView(ScoringActivity.this);
					TableLayout table = new TableLayout(ScoringActivity.this);
					scrollView.addView(table);
					table.setColumnStretchable(0, true);
					table.setColumnStretchable(10, true);
					table.setShrinkAllColumns(true);
					LayoutParams params = new LayoutParams(700, 500);
					dialog.setContentView(scrollView, params);
					// Round []
					// rounds=scoringManager.showAllRoundsRounds(game2);
					Round[] rounds = getFictiveRounds();
					for (int i = 0; i < rounds.length; i++) {
						TableRow tr = new TableRow(ScoringActivity.this);
						TextView tv = new TextView(ScoringActivity.this);
						tv.setText(rounds[i].getDate());
						tv.setLayoutParams(tableRowparams);
						tr.addView(tv);
						for (int j = 0; j < rounds[i].getRoundResult().size(); j++) {
							tv = new TextView(ScoringActivity.this);
							tv.setLayoutParams(tableRowparams);
							StringBuilder sb = new StringBuilder();
							sb.append(rounds[i].getRoundResult().get(j)
									.getUserName());
							sb.append("(");
							sb.append(rounds[i].getRoundResult().get(j)
									.getScore());
							sb.append(")");
							tv.setText(sb.toString());
							tr.addView(tv);
						}
						table.addView(tr);
						//seperator
						View lineseperator = new View(ScoringActivity.this);
						lineseperator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
						lineseperator.setBackgroundColor(Color.rgb(51, 51, 51));
						table.addView(lineseperator);
					}
					dialog.show();
				}
			});
			tl.addView(tr, new TableRow.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			//seperator
			View lineseperator = new View(this);
			lineseperator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
			lineseperator.setBackgroundColor(Color.rgb(51, 51, 51));
			tl.addView(lineseperator);
		}

	}

	Round[] getFictiveRounds() {
		Round[] rounds = new Round[10];
		Random rand = new Random();
		for (int i = 0; i < rounds.length; i++) {
			rounds[i] = new Round(new Date().toString());
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
