package carddeckplatform.game;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;

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
			
			
		for (Iterator iter = plugins.iterator(); iter.hasNext();) {
			PluginDetails pd = (PluginDetails) iter.next();
			TableRow tr = new TableRow(this);
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
				FileOutputStream output=openFileOutput("file.txt", MODE_PRIVATE);
//				OutputStream output = new FileOutputStream(ROOT+System.getProperty("file.separator")+"testMe");
				
				
				byte data[] = new byte[1024];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					total += count;
					System.out.println((int) (total * 100 / fileLength));
					// publishing the progress....
					publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}
				System.out.println("DOWNLOADED");
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
