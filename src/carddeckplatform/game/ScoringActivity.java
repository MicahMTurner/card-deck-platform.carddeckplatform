package carddeckplatform.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import logic.host.Host;

import communication.actions.StopLivePositionAction;
import communication.link.ServerConnection;
import communication.messages.Message;
import communication.messages.RestartMessage;
import communication.server.ConnectionsManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.controller.LivePosition;
import client.ranking.db.Game;
import client.ranking.db.Player;
import client.ranking.db.Round;
import client.ranking.db.ScoringManager;

public class ScoringActivity extends Activity {
	public static String DBNAME = "scoring";
	ScoringManager scoringManager;
	TableLayout tl;
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

		tl = (TableLayout) findViewById(R.id.scoringtable);

		// making the database manager
		scoringManager = new ScoringManager(ScoringActivity.this, DBNAME);
		scoringManager.open();
		 games=scoringManager.showAllGamesAndLastRounds();
//		games = makeFictiveGames();
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
	
	public void initScoringTable(Game[] games, final TableLayout tl) {
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
		tr.addView(makeBlankView());
		// Date
		tv = new TextView(this);
		tv.setText("Date :");
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, headerFontSize);
		tr.addView(tv);
		tr.addView(makeBlankView());
		// Players
		tv = new TextView(this);
		tv.setText("Players :");
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, headerFontSize);
		tr.addView(tv);
		tr.addView(makeBlankView());
		tl.addView(tr);
		for (int i = 0; i < games.length; i++) {
			final Game game2 = games[i];
			tr = new TableRow(tl.getContext());
//			tr.setLayoutParams(tableRowParams);
			// type
			tv = new TextView(this);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
			tv.setText(game2.getGameType());
//			tv.setLayoutParams(tableRowparams);
			tr.addView(tv);
			tr.addView(makeBlankView());
			// date
			tv = new TextView(this);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize-3);
			tv.setText(game2.getDate());
//			tv.setLayoutParams(tableRowparams);
			tr.addView(tv);
			tr.addView(makeBlankView());
			// players
			tv = new TextView(this);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
			tv.setText(game2.getPlayersInfo());
//			tv.setLayoutParams(tableRowparams);
			tr.addView(tv);
			tr.addView(makeBlankView());
			tr.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Dialog dialog = new Dialog(ScoringActivity.this,R.style.startGameDialogTheme);
					dialog.setContentView(R.layout.scoredialog);
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
					TableLayout table=(TableLayout) dialog.findViewById(R.id.dialogscoringtable);
					Round[] rounds=scoringManager.showAllRoundsRounds(game2);
					
					//making headers
					TableRow tr= new TableRow(ScoringActivity.this);
					TextView tv= new TextView(ScoringActivity.this);
					tv.setText("Round");
					tr.addView(tv);
					tr.addView(makeBlankView());
					for(int i=0;i<rounds[0].getRoundResult().size();i++){
						tv= new TextView(ScoringActivity.this);
						tv.setText(rounds[0].getRoundResult().get(i).getUserName());
						tr.addView(tv);
						tr.addView(makeBlankView());
					}
					table.addView(tr);
					View lineseperator = new View(ScoringActivity.this);
					lineseperator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
					lineseperator.setBackgroundColor(Color.rgb(100, 100, 100));
					table.addView(lineseperator);
					//making the table
					for(int i=0;i<rounds.length;i++){
						tr= new TableRow(ScoringActivity.this);
						tv= new TextView(ScoringActivity.this);
						tv.setText(i+"");
						tr.addView(tv);
						tr.addView(makeBlankView());
						Round round=rounds[i];
						for(int j=0;j<round.getRoundResult().size();j++){
							tv= new TextView(ScoringActivity.this);
							tv.setText(round.getRoundResult().get(j).getScore()+"");
							tr.addView(tv);
							tr.addView(makeBlankView());
						}
						lineseperator = new View(ScoringActivity.this);
						lineseperator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
						lineseperator.setBackgroundColor(Color.rgb(51, 51, 51));
						table.addView(tr);
						table.addView(lineseperator);
					}
					dialog.show();
					
				}
			});
			
			final TableRow tr2=tr;
			tr.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ScoringActivity.this);
					builder.setMessage("Are you sure you want to delete?")
					       .setCancelable(false)
					       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   scoringManager.removeGame(game2);
					        	   tl.removeView(tr2);
					        	   dialog.dismiss();
					           }
					       })
					       .setNegativeButton("No", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                dialog.cancel();
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
					return true;
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
	
	public View makeBlankView(){
		View view= new View(ScoringActivity.this);
		view.setLayoutParams(new TableRow.LayoutParams(10, 10));

		return view;
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(0, Menu.FIRST, Menu.NONE, "Delete All").setIcon(R.drawable.exit);
    	return true;
    }
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {    	
    	switch(item.getItemId()){
    		case Menu.FIRST:
    			Toast.makeText(this, "Delete All", 2000).show();
    			if(this.games!=null){
    				for(Game game:games){
    					scoringManager.removeGame(game);
    				}
    				this.tl.removeAllViews();
    				TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
    						TableLayout.LayoutParams.FILL_PARENT,
    						TableLayout.LayoutParams.WRAP_CONTENT);
    				
    				int leftMargin = 10;
    				int topMargin = 2;
    				int rightMargin = 10;
    				int bottomMargin = 2;

    				tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
    						bottomMargin);
    				android.widget.TableRow.LayoutParams tableRowparams = new android.widget.TableRow.LayoutParams();
    				tableRowparams.rightMargin = dpToPixel(10, ScoringActivity.this); // right-margin = 10dp
    				
    				TableRow tr = new TableRow(this);
    				tr.setLayoutParams(tableRowParams);
    						// Type
    				TextView tv = new TextView(this);
    				tv.setText("Type :");
    				int headerFontSize=20;
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
    			}
    			return true;
    		
    		
    		
    		
    		default:{    			 			
    		}
    			return true;
    	}
	}
}
