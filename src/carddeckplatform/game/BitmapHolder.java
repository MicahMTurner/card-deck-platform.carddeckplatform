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
			if (img==null){			
				int resourceId=context.getResources().getIdentifier(bitmapName, "drawable", "carddeckplatform.game");
				img = BitmapFactory.decodeResource(context.getResources(), resourceId);
				bitmaps.put(bitmapName, img);
			}
		
		
		return img;
	}
	
	/**
	 * change the size of an image.
	 * @param bitmapName
	 * @param scale
	 */
	public void scaleBitmap(String bitmapName, Point scale){
		Matrix matrix = new Matrix();
		Bitmap img = getBitmap(bitmapName);
		// the image wasn't found.
		if(img==null)
			return;
		// convert scale to match the display size.
		scale = MetricsConvertion.pointRelativeToPx(scale);
		// if the image is already scaled do nothing.
		if(img.getWidth() == scale.getX() && img.getHeight() == scale.getY())
			return;
		
		Bitmap newBitmap;
		matrix.postScale((float)scale.getX()/(float)img.getWidth(), (float)scale.getY()/(float)img.getHeight());
		newBitmap = Bitmap.createBitmap(img, 0, 0, img.getWidth() , img.getHeight(), matrix, true);
		
		replaceBitmap(bitmapName, newBitmap);
	}
	
	
	public void rotateBitmap(String bitmapName, float angle){
		Matrix matrix = new Matrix();
		Bitmap img = getBitmap(bitmapName);
		// the image wasn't found.
		if(img==null)
			return;
		
		Bitmap newBitmap;
		matrix.postRotate(angle);
		newBitmap = Bitmap.createBitmap(img, 0, 0, img.getWidth() , img.getHeight(), matrix, true);
		
		replaceBitmap(bitmapName, newBitmap);
	}
	
	private void replaceBitmap(String bitmapName , Bitmap img){
		bitmaps.remove(bitmapName);
		bitmaps.put(bitmapName, img);
	}
	
}
