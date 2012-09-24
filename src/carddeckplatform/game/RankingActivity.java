package carddeckplatform.game;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import utils.Pair;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import client.controller.ClientController;
import client.dataBase.DynamicLoader;

public class RankingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.rankinglayout);
		DynamicLoader dl = new DynamicLoader();
		TableLayout tl = (TableLayout) findViewById(R.id.rankTable);
		ArrayList<Pair<String, String>> plugins = dl.getInstalledPlugins();
		plugins = dl.getInstalledPlugins();
		for (int i = 0; i < plugins.size(); i++) {
			final Pair<String, String> pair = plugins.get(i);
			TableRow tr = new TableRow(this);
			tr.setGravity(Gravity.CENTER);

			// adding to the row views
			TextView tv = new TextView(this);
			tv.setText(pair.getFirst());
			tv.setTypeface(null, Typeface.BOLD);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
			Button button = new Button(this);
			final MyButtonListener listener = new MyButtonListener(pair);
			button.setOnClickListener(listener);
			button.setText("Send");

			RatingBar rb = new RatingBar(this);
			// setting the listener
			rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

				@Override
				public void onRatingChanged(final RatingBar ratingBar,
						final float rating, boolean fromUser) {
					if (fromUser) {
						float relativeRate = rating / ratingBar.getNumStars();
						long rate = Math.round(relativeRate) * 9 + 1;
						listener.rate = rate;
					}
				}
			});

			tr.addView(tv);
			tr.addView(rb);
			tr.addView(button);
			tl.addView(tr);

			// START setting layout animation
			AnimationSet set = new AnimationSet(true);

			Animation animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(300);
			set.addAnimation(animation);
			animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
					0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, -1.0f,
					Animation.RELATIVE_TO_SELF, 0.0f);
			animation.setDuration(700);
			set.addAnimation(animation);

			LayoutAnimationController controller = new LayoutAnimationController(
					set, 0.25f);

			tl.setLayoutAnimation(controller);

			// END setting layout animation

		}

	}

	private class MyButtonListener implements OnClickListener {
		public long rate;
		private Pair<String, String> pair;
		boolean sentFlag = false;

		public MyButtonListener(Pair<String, String> pair) {
			rate = 5;
			this.pair = pair;
		}

		@Override
		public void onClick(View v) {
			// sending just once
			if (sentFlag == true) {
				return;
			}
			sentFlag = true;
			final Dialog dialog = new Dialog(RankingActivity.this);
			dialog.setTitle("Done");
			dialog.setContentView(R.layout.ranked);
			Button button = (Button) dialog.findViewById(R.id.rankedCloseBtn);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();

			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {

					URL url = null;
					HttpURLConnection urlConnection = null;
					try {
						url = new URL(
								"http://cardsplatform.appspot.com/rank?id="
										+ pair.getSecond() + "&rank=" + rate);
						System.out.println("URL:"
								+ "http://cardsplatform.appspot.com/rank?id="
								+ pair.getSecond() + "&rank=" + rate);
						urlConnection = (HttpURLConnection) url
								.openConnection();

						 InputStream in = new
						 BufferedInputStream(urlConnection.getInputStream());
					} catch (MalformedURLException e2) {
						e2.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} finally {
						urlConnection.disconnect();
					}
				}
			});
			thread.start();
		}

	}
}
