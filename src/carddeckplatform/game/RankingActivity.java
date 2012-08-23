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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import client.dataBase.DynamicLoader;

public class RankingActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rankinglayout);
		DynamicLoader dl=new DynamicLoader();
		TableLayout tl = (TableLayout) findViewById(R.id.rankTable);
		ArrayList<Pair<String, String>> plugins = dl.getInstalledPlugins();
		for (int i = 0; i < plugins.size(); i++) {
			final Pair<String, String> pair = plugins.get(i);
			TableRow tr = new TableRow(this);

			// adding to the row views
			TextView tv = new TextView(this);
			tv.setText(pair.getFirst());

			RatingBar rb = new RatingBar(this);
			// setting the listener
			rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating,
						boolean fromUser) {
					// TODO Auto-generated method stub
					if (fromUser) {
						float relativeRate = rating / ratingBar.getNumStars();
						long rate = Math.round(relativeRate) * 9 + 1;

						URL url = null;
						HttpURLConnection urlConnection = null;
						try {
							url = new URL(
									"http://cardsplatform.appspot.com/rank?id="
											+ pair.getSecond() + "&rank="
											+ rate);
							urlConnection = (HttpURLConnection) url
									.openConnection();
							InputStream in = new BufferedInputStream(
									urlConnection.getInputStream());
							// readStream(in);
						} catch (MalformedURLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							urlConnection.disconnect();
						}

					}
				}
			});
			
			tr.addView(tv);
			tr.addView(rb);
			tl.addView(tr);
			// START setting layout animation
			AnimationSet set = new AnimationSet(true);

			Animation animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(300);
			set.addAnimation(animation);
			animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
					-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
			animation.setDuration(700);
			set.addAnimation(animation);

			LayoutAnimationController controller = new LayoutAnimationController(
					set, 0.25f);

			tl.setLayoutAnimation(controller);

			// END setting layout animation

		}

	}
}
