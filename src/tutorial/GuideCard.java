package tutorial;

import client.gui.entities.MetricsConvertion;
import carddeckplatform.game.BitmapHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import handlers.CardEventsHandler;
import utils.Card;
import utils.Point;

public class GuideCard extends Card {
	private static String WELCOMEMSG="Hello,\nwelcome to\nCard deck platform";
	private static String DROPPABLESMSG="These are the public\n areas. Players can place\ncards on these areas just\nlike lacing cards on a\nreal table.";
	private static String PLAYERSMSG="These are the\nplayers areas.\nEach one represents\na pleayer in the game.\n drag me to your area,\n which is the bottom one";
	private static String ROTATEMSG="To rotate me,\n Place one finger on my\n face and touch the\n screen with another,\n move it in the arrow's\n direction.";
	private static String DECKMSG="This is the\ngame deck.\n\n Drag me there!";
	private static String GOODJOBMSG="Excellent! :)";
	private static String BADJOBMSG="Please try again :(";
	private static String HUGEPUBLICMSG="Wow!!!\n what a huge public area!\n Take me there!";
	private static String FLIPMSG="Double click to\n flip me over.";
	private static String ENDMSG="Now you\n know everything you\n need to start beating your\n friends in card games.";
	private static String FLIPAGAINMSG="I'm uncomfortable, Flip me again please";
	private static String AUTOHIDEMSG="Tilt the device so it is horizontal and the screen is faced up";
	private static String CANCELAUTOHIDEMSG="now tilt back the device";
	
	
	Point cloudScale = new Point(30 , 40);
	private Paint paint;
	private String text;
	private boolean speech;
	
	public GuideCard(CardEventsHandler handler,boolean speech) {
		super(handler, "happycard", "back");
		this.scale = new Point(10,20);
		paint = new Paint();
		this.speech=speech;
		
		
	}
	
	@Override
    public void draw(Canvas canvas,Context context) {
		super.draw(canvas, context);
		Point absCloudScale = MetricsConvertion.pointRelativeToPx(cloudScale);
		Matrix matrix = new Matrix();
		Bitmap img;	
		//img = BitmapHolder.get().getBitmap("tut1");
		//matrix.postScale((float)absCloudScale.getX()/(float)img.getWidth(), (float)absCloudScale.getY()/(float)img.getHeight());
		matrix.postTranslate(getX()+absCloudScale.getX()/5f, getY()-absCloudScale.getY());
		//canvas.drawBitmap(img, matrix, null);
		if (speech){
			getRightText();
			canvas.drawText(text, getX(), getY(), paint);
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
