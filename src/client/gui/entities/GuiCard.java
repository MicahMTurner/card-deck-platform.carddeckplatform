//package client.gui.entities;
//
//import java.util.Random;
//
//import utils.Card;
//import utils.Point;
//
//
//import client.controller.ClientController;
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
//import android.graphics.drawable.BitmapDrawable;
//
//public class GuiCard extends Draggable {
//
//	private final Card card;
//
//	
//	public GuiCard(Card card) {
//		this.card = card;
//	}
//
//	public Card getCard() {
//		return card;
//	}
//
//	@Override
//	public int sensitivityRadius() {
//		return 30;
//	}
//
//	@Override
//	public void draw(Canvas canvas, Context context) {
//		Bitmap resizedBitmap=null;				
//		Matrix matrix = new Matrix();
//		matrix.postRotate(card.getAngle());
//		int resourceId;
//		if(card.isRevealed()){		
//			resourceId=context.getResources().getIdentifier(card.getFrontImg(), "drawable", "carddeckplatform.game");
//			Bitmap frontImg = BitmapFactory.decodeResource(context.getResources(), resourceId);	
//			resizedBitmap = Bitmap.createBitmap(frontImg, 0, 0, frontImg.getScaledWidth(canvas) , frontImg.getScaledHeight(canvas), matrix, true);
//		}else{
//			resourceId=context.getResources().getIdentifier(card.getBackImg(), "drawable", "carddeckplatform.game");
//			Bitmap backImg = BitmapFactory.decodeResource(context.getResources(), resourceId);
//			resizedBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.back), 0, 0, backImg.getScaledWidth(canvas) , backImg.getScaledHeight(canvas), matrix, true);
//		}
//		canvas.drawBitmap(resizedBitmap, getX()-25, getY()-20, new Paint());
//		
//		        
//		
//		
//		// if the card is being carried by another player a hand and the name of the carrier would be drawn near the card's image.
//        if(this.isCarried()){
//        	Paint paint = new Paint(); 		   
//        	// draws the name of the carrier.
//            paint.setColor(android.graphics.Color.BLACK); 
//            paint.setTextSize(20); 
//            canvas.drawText(getCarrier(),getX()-25, getY()-20, paint);
//            // draws the hand.
//            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hand),getX()-30, getY()+20 , paint);
//        }        
//		
//	}
//
//	@Override
//	public int getMyId() {
//		return card.getId();
//	}
//
//	@Override
//	public Point getCoord() {
//		
//		return card.getCoord();
//	}
//
//	@Override
//	public void setLocation(int x, int y) {
//		card.setCoord(x,y);
//		
//	}
//
//	
//	
//	
//	
//	
//	
//
//	
//
//}
