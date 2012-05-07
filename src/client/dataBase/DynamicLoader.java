package client.dataBase;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import communication.actions.AddPlayerAction;

import logic.client.Game;
import utils.Player;
import utils.Position;
import war.War;


public class DynamicLoader {
	
	

	public static void addJar(String path) throws Exception{
		try {
			String urlPath="jar:file://"+"C:\\googleworkspace\\warGame\\war.jar"+"!/";
			
			URL u = new URL(urlPath);
			URLClassLoader sysLoader = (URLClassLoader) ClassLoader
			.getSystemClassLoader();
			URL urls[] = sysLoader.getURLs();
			for (int i = 0; i < urls.length; i++) {
			if ((urls[i].toString().equalsIgnoreCase(u.toString()))) {
			//if (log.isDebugEnabled()) {
			//log.debug("URL " + u + " is already in the CLASSPATH");
			//}
				return;
			}}
			
			Class sysclass = URLClassLoader.class;
			Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
			method.setAccessible(true);
			method.invoke(sysLoader, new Object[] { u });
			}
			catch (Exception e) {
			e.printStackTrace();
			throw new Exception( "Error, could not add URL to system classloader"+ e.getMessage());
			}
	}
	/**
	 * loading an entire jar file with all its classes
	 * @param path path of jar file to load
	 * @param gameClassName name of the class that extends Game class
	 * @see Game
	 * @return game instance
	 */
	public static Game LoadPlugin(String path,String gameClassName){
		Game game=null;
		try {
		
		File file  = new File("C:\\googleworkspace\\warGame\\war.jar");
		 URL url;		
			url = file.toURI().toURL();
				
		 URL[] urls = new URL[]{url};
		 ClassLoader cl = new URLClassLoader(urls);
		 Class cls = cl.loadClass("war.War");		
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
	
	public static ArrayList<String>getClassNamesFromPackage(String packageName) throws IOException{
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL packageURL;
	    ArrayList<String> names = new ArrayList<String>();;

	    packageName = packageName.replace(".", "/");
	    packageURL = classLoader.getResource("war.jar");

	   // if(packageURL.getProtocol().equals("jar")){
	        String jarFileName;
	        JarFile jf ;
	        Enumeration<JarEntry> jarEntries;
	        String entryName;

	        // build jar file name, then loop through zipped entries
	        jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
	        //jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
	        System.out.println(">"+jarFileName);
	        jf = new JarFile(jarFileName);
	        jarEntries = jf.entries();
	        while(jarEntries.hasMoreElements()){
	            entryName = jarEntries.nextElement().getName();
	            if(entryName.startsWith("war") && entryName.length()>packageName.length()+5){
	                entryName = entryName.substring(4,entryName.lastIndexOf('.'));
	                names.add(entryName);
	            }
	        }

	    // loop through files in classpath
	   // }else{
//	        File folder = new File(packageURL.getFile());
//	        File[] contenuti = folder.listFiles();
//	        String entryName1;
//	        for(File actual: contenuti){
//	            entryName1 = actual.getName();
//	            entryName1 = entryName1.substring(0, entryName1.lastIndexOf('.'));
//	            names.add(entryName1);
//	        }
	        for (String name : names){
	        	LoadPlugin("", name);
	        }
	   // }
	    return names;
	}
}
