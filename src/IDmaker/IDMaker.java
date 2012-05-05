package IDmaker;

import java.util.Hashtable;

import utils.Position;


public class IDMaker {
	private int id;
	private Hashtable<String,Integer> names;
	
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
		id=100;
	}
	public int createId(String name){		
		names.put(name, id);		
		return id++;
	}
	public Integer getId(String name){
		return names.get(name);
	}
	public int getId(){		
		return id++;
	}


	public int getId(Position.Player position) {
		int answer = 0;
		switch(position){
			case TOP:{
				answer=2;
				break;
			}
			case BOTTOM:{
				answer=1;
				break;
			}
			case LEFT:{
				answer=3;
				break;
			}
			case RIGHT:{
				answer=4;
				break;
			}
			default:{}
		}
	
		return answer;
	}
}
