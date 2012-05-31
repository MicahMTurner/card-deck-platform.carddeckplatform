package carddeckplatform.game;

import java.util.HashMap;

import client.gui.entities.MetricsConvertion;

import utils.Point;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

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
		
	public Bitmap getBitmap(String bitmapName){
		Context context = GameActivity.getContext();
		Bitmap img=bitmaps.get(bitmapName);
		try {
			if (img==null){			
				int resourceId=context.getResources().getIdentifier(bitmapName, "drawable", "carddeckplatform.game");
				img = BitmapFactory.decodeResource(context.getResources(), resourceId);
				bitmaps.put(bitmapName, img);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return img;
	}
	
	public void scaleBitmap(String bitmapName, Point scale){
		Matrix matrix = new Matrix();
		Bitmap img = getBitmap(bitmapName);
		
		// convert scale to match the display size.
		scale = MetricsConvertion.pointRelativeToPx(scale);
		
		
		// the image wasn't found.
		if(img==null)
			return;
		
		Bitmap resizedBitmap;
		matrix.postScale((float)scale.getX()/(float)img.getWidth(), (float)scale.getY()/(float)img.getHeight());
		resizedBitmap = Bitmap.createBitmap(img, 0, 0, img.getWidth() , img.getHeight(), matrix, true);
		
		bitmaps.remove(bitmapName);
		
		bitmaps.put(bitmapName, resizedBitmap);
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
