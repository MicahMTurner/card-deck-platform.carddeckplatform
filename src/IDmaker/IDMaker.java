package IDmaker;

import java.util.Hashtable;


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
		id=1;
	}
	public int createId(String name){
		int tempId=id;
		names.put(name, id);
		id++;
		return tempId;
	}
	public Integer getId(String name){
		return names.get(name);
	}
}
