package client.dataBase;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.twmacinta.util.MD5;

import communication.actions.AddPlayerAction;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

import logic.client.Game;
import utils.Pair;
import utils.Player;
import utils.Position;
import war.War;


public class DynamicLoader {
	final static String PLUGINDIR="/sdcard/plugins/";
	private HashMap<String, String> mapping;
	
	public DynamicLoader() {
		mapping=new HashMap<String, String>();
	}
	
	private void mapGame(String gameName, URL[] urls){
		try{				
				ClassLoader cl = new URLClassLoader(urls);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(cl.getResource("info.xml").openStream());
				NodeList classPathNode=doc.getElementsByTagName("classPath");
				if (classPathNode.getLength()==1){
					mapping.put(gameName, 
							classPathNode.item(0).getFirstChild().getNodeValue().trim());
				}			
		 }
		 catch (Exception e){	
			 e.printStackTrace();
		 }
	}
	
	public Set<String> getGameNames(){
		mapPlugins();
		return mapping.keySet();
	}
	
	
	private void mapPlugins(){
		try {
			File folder = new File(PLUGINDIR);		
			for (File file : folder.listFiles()){		
				URL url=file.toURI().toURL();						
				URL[] urls = new URL[]{url};
				String gameName=file.getName().substring(0, file.getName().lastIndexOf("."));				
				mapGame(gameName.toString(),urls);
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
	public Game LoadPlugin(String gameName){		
		
		Game game=null;
		try {
			
			//get the classPath that extends Game class
			String classPath=mapping.get(gameName);
			
			//check if never mapped that game before
			if (classPath==null){
				/*we are not the host, therefore we didn't
				 *map all plug-ins.*/
				File file = new File(PLUGINDIR+"/"+gameName+".jar");
				URL url = file.toURI().toURL();				
				URL[] urls = new URL[]{url};				
				mapGame(gameName,urls);
			}
			 PathClassLoader classLoader= new PathClassLoader(PLUGINDIR+"/"+gameName+".jar", getClass().getClassLoader());
			//DexClassLoader classLoader = new DexClassLoader(
			//		(PLUGINDIR+"/"+gameName+".jar"), PLUGINDIR, null, getClass().getClassLoader());
			//Class<?> cls = classLoader.loadClass(mapping.get(gameName));
			//ClassLoader cl = new URLClassLoader(urls);	
			Class<?> cls = classLoader.loadClass(mapping.get(gameName));		
			game=(Game)cls.newInstance();
		 
		 
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {			
			e.printStackTrace();
		}		
		return game;
	}
	//maybe if we return HASH-MAP , the performance would be better
	public ArrayList<Pair<String, String>> getFileNamesAndMD5(){
		
		ArrayList<Pair<String, String>>namesAndMD5 = new ArrayList<Pair<String,String>>();
		//go over mapping game names
		for (String gameName : mapping.keySet()){
			//add game name ".jar" , create new file instance from plugin dir.
			Pair<String,String> nameAndMD5= new Pair<String, String>(gameName+".jar", calcMd5(new File(PLUGINDIR+"/"+gameName+".jar")));
			namesAndMD5.add(nameAndMD5);
		}
		return namesAndMD5;
	}
	
	public String calcMd5(File file){
		
		try {
			return MD5.asHex(MD5.getHash(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
}
