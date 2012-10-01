package client.dataBase;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import logic.client.Game;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import utils.Pair;
import android.content.Context;
import android.os.AsyncTask;
import carddeckplatform.game.CarddeckplatformActivity;
import carddeckplatform.game.ClassLoaderDelegate;
import carddeckplatform.game.PluginDetails;
import carddeckplatform.game.StaticFunctions;
import carddeckplatform.game.gameEnvironment.GameEnvironment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twmacinta.util.MD5;

import dalvik.system.DexClassLoader;


public class DynamicLoader {
	//mapping between game name and class path.
	final static String PLUGINDIR = GameEnvironment.path + "plugins";
	private HashMap<String, String> mapping;
	//add mapping between game instance and game name. 
	//change host, so it is holding the same game instance as the client? or sending on end turn, who ended the turn
	//and then see if he is last in queue... (or first and then remove him to end of line.. something like that...)
	//private HashMap<String,Game> gameInstance;
	private Collection<PluginDetails> plugins = new ArrayList<PluginDetails>();
	private CountDownLatch cdl;
	public DynamicLoader() {
		mapping = new HashMap<String, String>();
		//gameInstance = new HashMap<String, Game>();
	}

	private void mapGame(String gameName, URL[] urls) {
		try {
			ClassLoader cl = new URLClassLoader(urls);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(cl.getResource("info.xml")
					.openStream());
			NodeList classPathNode = doc.getElementsByTagName("classPath");
			if (classPathNode.getLength() == 1) {
				mapping.put(gameName, classPathNode.item(0).getFirstChild()
						.getNodeValue().trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Set<String> getGameNames() {
		mapping.clear();
		//gameInstance.clear();
		mapPlugins();
		return new HashSet<String>(mapping.keySet());
	}

	private void mapPlugins() {
		try {
			File folder = new File(PLUGINDIR);
			for (File file : folder.listFiles()) {
				URL url = file.toURI().toURL();
				URL[] urls = new URL[] { url };
				System.out.println(file.getName());
				String gameName = file.getName().substring(0,
						file.getName().lastIndexOf("."));
				mapGame(gameName.toString(), urls);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * loading an entire jar file with all its classes
	 * @param path path of jar file to load
	 * @param gameClassName name of the class that extends Game class
	 * @see Game
	 * @return game instance
	 */
	public Game LoadPlugin(String gameName) {

		Game game = null;
		try {

			// get the classPath that extends Game class
			String classPath = mapping.get(gameName);

			// check if never mapped that game before
			if (classPath == null) {
				/*
				 * we are not the host, therefore we didn'tmap all plug-ins.
				 */
				File file = new File(PLUGINDIR + "/" + gameName + ".jar");
				URL url = file.toURI().toURL();
				URL[] urls = new URL[] { url };
				mapGame(gameName, urls);
				// check if game exists in plug-in dir(managed to map him)
				if (mapping.get(gameName) == null) {
					// game doesn't exists, download it automatically
					cdl=new CountDownLatch(1);
					downloadGame(CarddeckplatformActivity.getContext(),
							gameName);
					cdl.await();
					mapPlugins();
				}
			}
			//if (gameInstance.get(gameName)==null){
				initalizeClassLoader(gameName);
			
				Class<?> cls =ClassLoaderDelegate.getDelegate().loadClass(mapping.get(gameName));
				game=(Game)cls.newInstance();
			//}else{
				//game= gameInstance.get(gameName);
			//}

			System.out.println("loading class: "+getClass().getClassLoader().toString());

			
			
		 
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		return game;
	}
	
	private void initalizeClassLoader (String gameName){
		String jarFile = PLUGINDIR+"/"+gameName+".jar";
		DexClassLoader child = new DexClassLoader (
				jarFile, GameEnvironment.path+"temp", null, getClass().getClassLoader());
		ClassLoaderDelegate.getInstance().setClassLoader(child);
	}
	

	private void downloadGame(final Context context, String gamename) {
		try {
			executeHttpGet();
		} catch (Exception e) {
			e.printStackTrace();
		}

		PluginDetails gamepd = null;
		// getting the plugindetails
		for (PluginDetails pd : this.plugins) {
			if (pd.getFilename().compareTo(gamename+".jar") == 0) {
				gamepd = pd;
				break;
			}
		}
		final PluginDetails gamepd2=gamepd;
		new DownloadFile().execute(gamepd2);
		

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

	
	public ArrayList<Pair<String, String>> getInstalledPlugins() {

		ArrayList<Pair<String, String>> namesAndMD5 = new ArrayList<Pair<String, String>>();
		mapPlugins();
		// go over mapping game names
		for (String gameName : mapping.keySet()) {
			// add game name ".jar" , create new file instance from plugin dir.
			Pair<String, String> nameAndMD5 = new Pair<String, String>(gameName, 
					calcMd5(new File(PLUGINDIR + "/" + gameName	+ ".jar")));
			namesAndMD5.add(nameAndMD5);
		}
		return namesAndMD5;
	}

	public String calcMd5(File file) {

		try {
			return MD5.asHex(MD5.getHash(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private class DownloadFile extends
			AsyncTask<PluginDetails, Integer, String> {
		@Override
		protected String doInBackground(PluginDetails... pluginDetail) {
			try {

				URL url = new URL("http://cardsplatform.appspot.com"
						+ pluginDetail[0].getAddress());
				URLConnection connection = url.openConnection();
				connection.connect();
				// this will be useful so that you can show a typical 0-100%
				// progress bar
				//long fileLength = pluginDetail[0].getSize();

				// download the file
				InputStream input = new BufferedInputStream(
						connection.getInputStream());
				FileOutputStream output = StaticFunctions
						.getPluginOutputStream(pluginDetail[0].getFilename());

				byte data[] = new byte[4096];
				//long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					//total += count;
					//
					// publishing the progress....
					//publishProgress((int) (total * 100 / fileLength));
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
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
		}

		@Override
		protected void onPostExecute(String result) {
			cdl.countDown();
		}

	}

}
