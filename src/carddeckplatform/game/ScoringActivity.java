package carddeckplatform.game;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TabHost.TabSpec;
import android.widget.TableRow;
import android.widget.TextView;
import client.ranking.db.Game;
import client.ranking.db.ScoringManager;

public class ScoringActivity extends Activity {
	static String DBNAME="scoring";
	ScoringManager scoringManager;
	
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
		ArrayList<Game> games=scoringManager.showAllGames();
		scoringManager.close();
		
		initScoringTable(games,tl);
		
		
	}
	
	public void initScoringTable(ArrayList<Game> games,TableLayout tl){
		
		for(Game game:games){
			TableRow tr=new TableRow(tl.getContext());
//			tr.addView(new TextView(game.))
			
			
		}
		
	}
	
	@Override
	protected void onStart() {
		scoringManager.open();
		
	}
	
	@Override
	protected void onStop() {
		scoringManager.close();
	}
}
