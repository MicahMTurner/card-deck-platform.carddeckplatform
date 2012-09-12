package freeplay.customization;

import handlers.Handler;
import handlers.PlayerEventsHandler;
import handlers.PublicEventsHandler;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import utils.Player;
import utils.Position;
import utils.Public;
import utils.droppableLayouts.DroppableLayout;

import carddeckplatform.game.StaticFunctions;
import carddeckplatform.game.EditView.Mode;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.gui.entities.Droppable;
import freeplay.customization.CustomizationItem.Type;

public class FreePlayProfile implements Serializable {
	
	private final static String profileDir = "profiles";
	private String profileName="";
	private ArrayBlockingQueue<Droppable> droppables;
	private Mode mode;
	private int cardsToDeal=0;
	
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	public String getProfileName() {
		return profileName;
	}
	
	public void setDroppables(ArrayBlockingQueue<Droppable> droppables) {
		this.droppables = droppables;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public ArrayBlockingQueue<Droppable> getDroppables() {
		return droppables;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setCardsToDeal(int cardsToDeal) {
		this.cardsToDeal = cardsToDeal;
	}
	
	public int getCardsToDeal() {
		return cardsToDeal;
	}
	
	public static FreePlayProfile loadProfile(String name){
		return (FreePlayProfile)StaticFunctions.readFile(profileDir + "/" + name + ".fpp");
	}
	
	public static void saveProfile(String name, FreePlayProfile profile){
		profile.setProfileName(name);
		StaticFunctions.writeFile(profile, profileDir + "/" + name + ".fpp");
	}
	
	public static ArrayList<FreePlayProfile> getAllProfiles(){
		ArrayList<FreePlayProfile> profiles = new ArrayList<FreePlayProfile>();
		
		File folder = new File(GameEnvironment.path + "/" + profileDir);
		File[] listOfFiles = folder.listFiles();
		
		for(File f : listOfFiles){
			profiles.add((FreePlayProfile)StaticFunctions.readFile(profileDir + "/" + f.getName()));
		}
		
		return profiles;
	}
	
	public ArrayList<Droppable> getPublics(){
		ArrayList<Droppable> res = new ArrayList<Droppable>();
		
		for(Droppable d : droppables)
			if(((CustomizationItem)d).getType()==Type.PUBLIC && ((CustomizationItem)d).createHandler()!=null)
				if(mode==Mode.MANY_SMALL)
					res.add(new Public((PublicEventsHandler)((CustomizationItem)d).createHandler(), (Position.Public)d.getPosition(), DroppableLayout.LayoutType.HEAP));
				else
					res.add(new Public((PublicEventsHandler)((CustomizationItem)d).createHandler(), (Position.Public)d.getPosition(), DroppableLayout.LayoutType.NONE));
		return res;
	}
	
	public HashMap<Position.Player, PlayerEventsHandler> getPlayerHandlers(){
		HashMap<Position.Player, PlayerEventsHandler> res = new HashMap<Position.Player, PlayerEventsHandler>();
		
		for(Droppable d : droppables)
			if(((CustomizationItem)d).getType()==Type.PLAYER && ((CustomizationItem)d).createHandler()!=null)
				res.put((Position.Player)d.getPosition(), (PlayerEventsHandler)((CustomizationItem)d).createHandler());
		return res;
	}
}
