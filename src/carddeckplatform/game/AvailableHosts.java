package carddeckplatform.game;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import carddeckplatform.game.gameEnvironment.GameEnvironment;

import communication.link.HostGameDetails;

public class AvailableHosts implements Observer{
	private TableLayout table;
	private int ids;
	private ScrollView scrollView;
	private Context context;
	private ArrayList<HostGameDetails>foundHostIds;
	private Dialog dialog;
	private class enterGameClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			GameEnvironment.get().getTcpInfo().setHostIp(foundHostIds.get(v.getId()-2).getAddress());			
			Intent i = new Intent(context, GameActivity.class);
			context.startActivity(i);
			dialog.dismiss();
			
		}
		
	}
	private class showInstructionsClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			((ImageView)v).setImageResource(R.drawable.info);
			final Dialog dialog=new Dialog(context);
			dialog.setContentView(R.layout.instructionsdialog);
			dialog.setTitle("Instructions");
			
			TextView instructions=(TextView) dialog.findViewById(R.id.instructionsText);
			instructions.setText(foundHostIds.get(v.getId()-2).getInstructions());
			Button closeButton = (Button) dialog.findViewById(R.id.closingButton);
			closeButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
				}
			});

			dialog.show();
			
			
		}
		
	}
	public View getTable(){
		return scrollView;
	}
	private int getId(){
		return ids++;
	}

	public AvailableHosts(Context context, Dialog dialog){
		this.context=context;		
		this.dialog=dialog;
		ids=0;
		scrollView = new ScrollView(context);		
		table= new TableLayout(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		scrollView.setBackgroundResource(R.drawable.availablehostbkgrnd);		
		foundHostIds=new ArrayList<HostGameDetails>();
		table.setColumnStretchable(0,true);
		table.setColumnStretchable(10,true);
		table.setShrinkAllColumns(true);
		
		//make title row
		TableRow rowTitle = new TableRow(context);	
		rowTitle.setId(getId());
		rowTitle.setGravity(Gravity.CENTER);
		rowTitle.setPadding(0, 0, 0, 10);
		rowTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
		TextView title= new TextView(context);
		title.setText("Available Games");
		title.setGravity(Gravity.CENTER);		
		title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);  
	    title.setTypeface(Typeface.SERIF, Typeface.BOLD); 
	    TableRow.LayoutParams params = new TableRow.LayoutParams();  
	    params.span = 11;  
	  	rowTitle.addView(title,params);
	  	table.addView(rowTitle);
	  	
	  	//make mapping row
	  	TableRow mappingRow = new TableRow(context);	  	
	  	mappingRow.setPadding(0, 0, 0, 10);	  	
	    params.span = 4; 
	    mappingRow.setId(getId());
	  	mappingRow.addView(getTextView("Game Name"));
	  	mappingRow.addView(getTextView("Min player"),params);
	  	mappingRow.addView(getTextView(""));
	  	mappingRow.addView(getTextView("Max player"),params);
	  	mappingRow.addView(getTextView("Rules"));	  	
	  	table.addView(mappingRow);

		scrollView.setSmoothScrollingEnabled(true);
		scrollView.setScrollbarFadingEnabled(true);
		scrollView.addView(table);		

	}

	
	private View getTextView(String text) {		
		TextView textView = new TextView(context);
		textView.setText(text);
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12); 		
		textView.setTypeface(Typeface.SERIF, Typeface.BOLD);  
		return textView;
	}
	@Override
	public void update(Observable observable, final Object data) {
		table.post(new Runnable() {	
			@Override
			public void run() {
				
				HostGameDetails gameDetails=(HostGameDetails)data;
				if (!foundHostIds.contains(gameDetails)){
					foundHostIds.add(gameDetails);
					TableRow availableGame=new TableRow(context);					
					availableGame.setId(getId());
					availableGame.setLayoutParams(new LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
					availableGame.setOnClickListener(new enterGameClickListener());
					availableGame.setPadding(0, 0, 0, 0);
					availableGame.setBackgroundDrawable(context.getResources().getDrawable(android.R.drawable.list_selector_background));
					availableGame.setGravity(Gravity.CENTER);
					//make game name + owner
					TextView gameId=new TextView(context);
					gameId.setText(gameDetails.getGameName()+" By "+gameDetails.getOwner());
					gameId.setGravity(Gravity.CENTER);
					
					gameId.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);  
					gameId.setTypeface(Typeface.SERIF, Typeface.BOLD);
					availableGame.addView(gameId);
					
					//make min players
					addPlayersDetails(gameDetails.getMinPlayers(),availableGame);
				
					
					//make separator
					ImageView separator=new ImageView(context);
					separator.setImageResource(R.drawable.separator);
					separator.setPadding(5, 0, 5, 0);					
					availableGame.addView(separator);
					
					//make max players
					addPlayersDetails(gameDetails.getMaxPlayers(),availableGame);
				
					//make instructionIcon
					ImageView instructionIcon = new ImageView(context);
					instructionIcon.setOnClickListener(new showInstructionsClickListener());
					instructionIcon.setImageResource(R.drawable.info);
					instructionIcon.setPadding(40, 0, 40, 0);
					instructionIcon.setId(availableGame.getId());
					//change color when pressed
					
					instructionIcon.setOnTouchListener(new View.OnTouchListener(){
					
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							if (event.getActionMasked()==MotionEvent.ACTION_DOWN 
									|| event.getActionMasked()==MotionEvent.ACTION_MOVE){
								((ImageView)v).setImageResource(R.drawable.pressedinfo);
								return true;
							}else if (event.getActionMasked()==MotionEvent.ACTION_UP){
								v.performClick();
							}else{						
								((ImageView)v).setImageResource(R.drawable.info);
							}
							
							
							return true;
						}
						
					});
					
					availableGame.addView(instructionIcon);	

					table.addView(availableGame);
				}
				
			}

			private void addPlayersDetails(int players,
					TableRow availableGame) {
				int i=0;
				for (;i<players;i++){
					ImageView playerImage=new ImageView(context);
					playerImage.setImageResource(R.drawable.man);					
					availableGame.addView(playerImage);
				}
				for (;i<4;i++){
					availableGame.addView(new ImageView(context));
				}
				
			}
		});
	}
	

}
