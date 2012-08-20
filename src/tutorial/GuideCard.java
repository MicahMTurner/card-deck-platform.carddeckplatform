package tutorial;

import client.gui.entities.MetricsConvertion;
import carddeckplatform.game.BitmapHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import handlers.CardEventsHandler;
import utils.Card;
import utils.Point;

public class GuideCard extends Card {

	Point cloudScale = new Point(30 , 40);
	
	
	
	public GuideCard(CardEventsHandler handler) {
		super(handler, "happycard", "back");
		this.scale = new Point(10,20);
	}
	
	@Override
    public void draw(Canvas canvas,Context context) {
		super.draw(canvas, context);
		Point absCloudScale = MetricsConvertion.pointRelativeToPx(cloudScale);
		Matrix matrix = new Matrix();
		Bitmap img;	
		img = BitmapHolder.get().getBitmap("tut1");
		matrix.postScale((float)absCloudScale.getX()/(float)img.getWidth(), (float)absCloudScale.getY()/(float)img.getHeight());
		matrix.postTranslate(getX()+absCloudScale.getX()/5f, getY()-absCloudScale.getY());
		
		canvas.drawBitmap(img, matrix, null);
		
		
	}
	

}
