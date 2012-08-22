package tutorial;

import client.gui.entities.MetricsConvertion;
import carddeckplatform.game.BitmapHolder;
import carddeckplatform.game.CarddeckplatformActivity;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import handlers.CardEventsHandler;
import utils.Card;
import utils.Point;

public class GuideCard extends Card {
	private static String[] WELCOMEMSG= {"Hello,","welcome to","Card deck platform"};
	private static String[] DROPPABLESMSG={"These are the public","areas. Players can place","cards on these areas just","like lacing cards on a","real table."};
	private static String[] PLAYERSMSG={"These are the","players areas.","Each one represents","a pleayer in the game."," drag me to your area,"," which is the bottom one"};
	private static String[] ROTATEMSG={"To rotate me,","Place one finger on my","face and touch the","screen with another,","move it in the arrow's"," direction."};
	private static String[] DECKMSG={"This is the","game deck.","","Drag me there!"};
	private static String[] GOODJOBMSG={"Excellent! :)"};
	private static String[] BADJOBMSG={"Please try again :("};
	private static String[] HUGEPUBLICMSG={"Wow!!!","what a huge public area!","Take me there!"};
	private static String[] FLIPMSG={"Double click to","flip me over."};
	private static String[] ENDMSG={"Now you","know everything you","need to start beating your","friends in card games."};
	private static String[] FLIPAGAINMSG={"I'm uncomfortable, Flip me again please"};
	private static String[] AUTOHIDEMSG={"Tilt the device","so it is horizontal","and the screen is faced up"};
	private static String[] CANCELAUTOHIDEMSG={"now tilt back the device"};
	
	
	Point cloudScale = new Point(55 , 65);
	private Paint paint;
	private String[] text;
	private boolean speech;
	float textScale;
	Point cloudOffset = new Point(10 , 50);
	Point textOffset = new Point(15 , 16);
	private Point screenScale = new Point();
	
	private Point getTextBounds(String[] txt){
		Point bounds = new Point( 0 , txt.length );
		
		for(String str : txt){
			
			Rect b = new Rect();
			
			paint.getTextBounds(str,0,str.length(),b);
			int width = b.width();
			
			
			
			if(width > bounds.getX())
				bounds.setX(width);
		}
		
		bounds.setX(bounds.getX() * 1.3f);
		bounds.setY(bounds.getY() * textScale);
		
		return bounds;
	}
	
	
	public GuideCard(CardEventsHandler handler,boolean speech) {
		super(handler, "happycard", "back");
		this.scale = new Point(10,20);
		paint = new Paint();
		
		Point textMetrics = new Point(2.8f,0);
		paint.setColor(Color.argb(130, 0, 0, 0));
		textMetrics = MetricsConvertion.pointRelativeToPx(textMetrics);
		
		textScale = textMetrics.getX();
		
		// set the scale according to the relative size.
		paint.setTextSize(textScale);
		
		this.speech=speech;
		
		cloudOffset = MetricsConvertion.pointRelativeToPx(cloudOffset);
		textOffset = MetricsConvertion.pointRelativeToPx(textOffset);
		screenScale.setX(GameEnvironment.get().getDeviceInfo().getScreenWidth());
		screenScale.setY(GameEnvironment.get().getDeviceInfo().getScreenHeight());
	}
	
	@Override
    public void draw(Canvas canvas,Context context) {
		super.draw(canvas, context);
		Point absCloudScale = MetricsConvertion.pointRelativeToPx(cloudScale);
		
		Matrix matrix = new Matrix();
		Bitmap cloud;	
		cloud = BitmapHolder.get().getBitmap("cloud");
		matrix.postScale((float)absCloudScale.getX()/(float)cloud.getWidth(), (float)absCloudScale.getY()/(float)cloud.getHeight());
		
		Point cloudPos = new Point(Math.min(getX() , screenScale.getX() - absCloudScale.getX()), Math.max(getY() - cloudOffset.getY() , 0));
		
		matrix.postTranslate(cloudPos.getX(), cloudPos.getY());
		//canvas.drawBitmap(img, matrix, null);
		if (speech){
			canvas.drawBitmap(cloud, matrix, null);
			getRightText();
			Point bounds = getTextBounds(text);
			for(int i=0; i< text.length ; i++){
			
				//canvas.drawText(text[i], Math.min(getX(), screenScale.getX() - bounds.getX()) + offset.getX() , Math.min(getY(), screenScale.getY() - bounds.getY()) + (i * textScale), paint);
				canvas.drawText(text[i], cloudPos.getX() + textOffset.getX()  ,cloudPos.getY() + textOffset.getY() + (i * textScale) , paint);
			}
		}
		
		
		
		
	}

	private void getRightText() {
		
				if (Tutorial.isBadJob!=null){
					if (Tutorial.isBadJob){		
						text=BADJOBMSG;
					}else{
						text=GOODJOBMSG;
					}
					
				}else{
					switch(Tutorial.currentStage){
						case WELCOME:{
							text=WELCOMEMSG;
							break;
						}
						case AUTOHIDE:{
							text=AUTOHIDEMSG;
							break;
						}
						case CANCELAUTOHIDE:{
							text=CANCELAUTOHIDEMSG;
							break;
						}
						case FLIPAGAIN:{
							text=FLIPAGAINMSG;
							break;
						}
						case DROPPABLES:{
							text=DROPPABLESMSG;
							break;
						}
						case DECK:{
							text=DECKMSG;
							break;
						}
						case PLAYERS:{
							text=PLAYERSMSG;
							break;
						}
						case END:{
							text=ENDMSG;
							break;
						}
						case FLIP:{
							text=FLIPMSG;
							break;
						}
						case HUGEPUBLIC:{
							text=HUGEPUBLICMSG;
							break;
						}
						case ROTATE:{
							text=ROTATEMSG;
							break;
						}
						default:{}
					}
				}
		
	}
	

}
