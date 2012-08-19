package carddeckplatform.game;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MarketActivity extends Activity {
	Collection<PluginDetails> plugins = new ArrayList<PluginDetails>();

	String ROOT = "data";
	ProgressDialog mProgressDialog;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	int fileSize;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloadplugin);
		TableLayout tl = (TableLayout) findViewById(R.id.markettable);
		Thread thread= new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					executeHttpGet();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//START setting layout animation
		AnimationSet set = new AnimationSet(true);

		  Animation animation = new AlphaAnimation(0.0f, 1.0f);
		  animation.setDuration(300);
		  set.addAnimation(animation);

		  animation = new TranslateAnimation(
		      Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
		      Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
		  );
		  animation.setDuration(700);
		  set.addAnimation(animation);

		  LayoutAnimationController controller =
		      new LayoutAnimationController(set, 0.25f);
		//END setting layout animation
		
		
		
		tl.setLayoutAnimation(controller);
		TableRow tr = new TableRow(this);
		tr.setGravity(Gravity.CENTER);
		
		TextView textView1 = new TextView(this);
		textView1.setText("Plugin");
		textView1.setTypeface(null,Typeface.BOLD_ITALIC);
		tr.addView(textView1);
		
		
		TextView textView2 = new TextView(this);
		textView2.setText("Rating");
		textView2.setTypeface(null,Typeface.BOLD_ITALIC);
		tr.addView(textView2);
		
		
		for (Iterator iter = plugins.iterator(); iter.hasNext();) {
			PluginDetails pd = (PluginDetails) iter.next();
			tr = new TableRow(this);
			tr.setGravity(Gravity.CENTER);
			
			Button button = new Button(this);
			button.setText(pd.getName());
			button.setGravity(Gravity.CENTER);
			addListenerOnButton(button, pd);
			tr.addView(button);

			// add his rank

			RatingBar rb = new RatingBar(this);
			rb.setEnabled(false);
			rb.setRating((float) ((float) pd.rank * 0.5));
			tr.addView(rb);
			tl.addView(tr);
		}
	}

	public void executeHttpGet() throws Exception {

		BufferedReader in = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(
					"http://cardsplatform.appspot.com/cardeckplatform_details"));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String json = sb.toString();
			System.out.println(json);
			Gson gson = new Gson();
			Type collectionType = new TypeToken<Collection<PluginDetails>>() {
			}.getType();
			this.plugins = gson.fromJson(json, collectionType);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void addListenerOnButton(Button btnStartProgress, final PluginDetails url) {
		btnStartProgress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// instantiate it within the onCreate method
				mProgressDialog = new ProgressDialog(MarketActivity.this);
				mProgressDialog.setMessage("Downloading File");
				mProgressDialog.setIndeterminate(false);
				mProgressDialog.setMax(100);
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

				// execute this when the downloader must be fired
				DownloadFile downloadFile = new DownloadFile();
				downloadFile.execute(url);
			}
		});

	}

	private class DownloadFile extends AsyncTask<PluginDetails, Integer, String> {
		@Override
		protected String doInBackground(PluginDetails... pluginDetail) {
			try {
				
				URL url = new URL("http://cardsplatform.appspot.com"+pluginDetail[0].getAddress());
				URLConnection connection = url.openConnection();
				connection.connect();
				// this will be useful so that you can show a typical 0-100%
				// progress bar
				long fileLength = pluginDetail[0].getSize();

				// download the file
				InputStream input = new BufferedInputStream(connection.getInputStream());
				FileOutputStream output=StaticFunctions.getPluginOutputStream(pluginDetail[0].getFilename());
				
				
				byte data[] = new byte[4096];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					total += count;
					System.out.println((int) (total * 100 / fileLength));
					// publishing the progress....
					publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}
				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			mProgressDialog.show();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			mProgressDialog.setProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			mProgressDialog.dismiss();
		}

	}
	

}
