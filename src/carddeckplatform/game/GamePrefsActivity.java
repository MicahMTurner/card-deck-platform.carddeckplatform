package carddeckplatform.game;

import carddeckplatform.game.gameEnvironment.GameEnvironment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;


public class GamePrefsActivity extends PreferenceActivity  {
	
	
	private String gameName;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		gameName = getIntent().getStringExtra("gamePrefs").toLowerCase();
		getPreferenceManager().setSharedPreferencesName(gameName);	
		int res = this.getResources().getIdentifier(gameName, "xml", "carddeckplatform.game");
		
		if(res!=0)
			addPreferencesFromResource(res);
		else{
			startGame();
			finish();
		}
        setContentView(R.layout.gameprefs);
        
        
        
        Button startBtn = (Button) findViewById(R.id.prefsStartBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startGame();
			}
		});
	}
	
	
	public void startGame(){
		Intent i = new Intent(GamePrefsActivity.this, GameActivity.class);               
        i.putExtra("gameName", getIntent().getStringExtra("gameName"));
        startActivity(i);
	}
}
