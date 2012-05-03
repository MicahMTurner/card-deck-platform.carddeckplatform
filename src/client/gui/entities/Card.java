//package client.gui.entities;
//
//import java.util.Random;
//
//import logic.card.CardLogic;
//
//import client.controller.ClientController;
//import client.controller.actions.DraggableMotionAction;
//import client.controller.actions.EndDraggableMotionAction;
//
//import communication.link.ServerConnection;
//
//import carddeckplatform.game.GameStatus;
//import carddeckplatform.game.R;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//
//public class Card extends Draggable {
//	private Bitmap img; // the image of the ball
//	//private int id; // gives every ball his own id, for now not necessary
//	//private static int count = 1;
//	//private boolean goRight = true;
//	//private boolean goDown = true;
//	private boolean isCarried=false;
//	private String carrier = "";
//	//private Context context;
//
//	public Card(Context context, int drawable, int x, int y){
//		super(context, x, y);
//		this.context = context;
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inJustDecodeBounds = true;
//        img = BitmapFactory.decodeResource(context.getResources(), drawable); 
//        //id=count;
//		//count++;
//	}
//	
//	//public Card(Context context, int drawable, Droppable dropable){
//	//	super(context);
//	//}
//	
//	public int sensitivityRadius() {
//
//		return 30;
//	}
//
//
//	
//		@Override
//	public void motionAnimation() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void motionAnimation(String str) {
//
//		isCarried = true;
//		carrier = str;
//	}
//
//	@Override
//	public void clearAnimation() {
//
//		isCarried = false;
//		carrier = "";
//	}
//
//	
//
//}
