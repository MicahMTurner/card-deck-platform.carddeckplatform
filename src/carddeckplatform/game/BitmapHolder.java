package carddeckplatform.game;

import java.util.HashMap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapHolder {
	private HashMap<String, Bitmap> bitmaps;
	
	//-------Singleton implementation--------//
	private static class BitmapHolderHolder
	{
		private final static BitmapHolder bitmapHolder=new BitmapHolder();
	}
				
						
	/**
	 * get Client Bitmap Holder instance
	 */
	public static BitmapHolder get(){
		return BitmapHolderHolder.bitmapHolder;
	}
	
	private BitmapHolder() {
		this.bitmaps=new HashMap<String, Bitmap>();
	}
		
	public Bitmap getBitmap(String bitmapName,Context context){
		Bitmap img=bitmaps.get(bitmapName);
		if (img==null){			
			int resourceId=context.getResources().getIdentifier(bitmapName, "drawable", "carddeckplatform.game");
			img = BitmapFactory.decodeResource(context.getResources(), resourceId);
			bitmaps.put(bitmapName, img);
		}
		return img;
	}
	
//	static public void load(Context context){
//		String[] colors = {"c", "s" , "d" , "h"};
//		int resourceId;
//		String id;
//		for(int i=0; i<4; i++)
//			for(int j=2; j<=14; j++){
//				id = colors[i] + String.valueOf(j);
//				resourceId = context.getResources().getIdentifier(id, "drawable", "carddeckplatform.game");
//				Bitmap frontImg = BitmapFactory.decodeResource(context.getResources(), resourceId);
//				
//				bitmaps.put(id, frontImg);
//			}
//		
//		resourceId = context.getResources().getIdentifier("back", "drawable", "carddeckplatform.game");
//		Bitmap frontImg = BitmapFactory.decodeResource(context.getResources(), resourceId);
//		
//		bitmaps.put("back", frontImg);
//	}
	
	
}
