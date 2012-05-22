package carddeckplatform.game;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapHolder {
	static private HashMap<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();
	
	static public Bitmap getBitmap(String bitmapName){
		return bitmaps.get(bitmapName);
	}
	
	static public void load(Context context){
		String[] colors = {"c", "s" , "d" , "h"};
		int resourceId;
		String id;
		for(int i=0; i<4; i++)
			for(int j=2; j<=14; j++){
				id = colors[i] + String.valueOf(j);
				resourceId = context.getResources().getIdentifier(id, "drawable", "carddeckplatform.game");
				Bitmap frontImg = BitmapFactory.decodeResource(context.getResources(), resourceId);
				
				bitmaps.put(id, frontImg);
			}
		
		resourceId = context.getResources().getIdentifier("back", "drawable", "carddeckplatform.game");
		Bitmap frontImg = BitmapFactory.decodeResource(context.getResources(), resourceId);
		
		bitmaps.put("back", frontImg);
	}
	
	
}
