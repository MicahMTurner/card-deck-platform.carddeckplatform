package IDmaker;

import java.util.Hashtable;

import utils.Position;


public class IDMaker {
	private int id;
	private int playerIds;
	private int publicIds;
	private int buttonIds;
	
	private Hashtable<String,Integer> names;
	private Hashtable<Position,Integer> positions;
	
	//-------Singleton implementation--------//
			private static class IDMakerHolder
			{
				private final static IDMaker idMaker=new IDMaker();
			}
			
					
			/**
			 * get Controller instance
			 */
			public static IDMaker getMaker(){
				return IDMakerHolder.idMaker;
			}

			
	public IDMaker() {
		names=new Hashtable<String,Integer>();
		positions=new Hashtable<Position, Integer>();
		this.buttonIds=60;
		this.playerIds=1;
		this.publicIds=25;
		id=100;
	}
	public int createId(String name){		
		names.put(name, id);		
		return id++;
	}
	
//	public int getId(Position position){
//		positions.put(position, id);
//		return id++;
//	}
	
	public int getId(Position.Player position){
		positions.put(position, id);
		return playerIds++;
	}
	
	public int getId(Position.Public position){
		positions.put(position, id);
		return publicIds++;
	}
	
	public int getId(Position.Button position){
		positions.put(position, id);
		return buttonIds++;
	}
	
	public Integer getId(String name){
		return names.get(name);
	}
	public int getIdByPos(Position position){
		return positions.get(position);
	}
	public int getId(){		
		return id++;
	}


//	public int getId(Position.Player position) {
//		int answer = 0;
//		switch(position){
//			case TOP:{
//				answer=2;
//				break;
//			}
//			case BOTTOM:{
//				answer=1;
//				break;
//			}
//			case LEFT:{
//				answer=3;
//				break;
//			}
//			case RIGHT:{
//				answer=4;
//				break;
//			}
//			default:{}
//		}
//	
//		return answer;
//	}
}
