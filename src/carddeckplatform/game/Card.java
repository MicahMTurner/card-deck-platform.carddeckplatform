package carddeckplatform.game;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

public class Card  {
	private Bitmap img; // the image of the ball
	private int coordX = 0; // the x coordinate at the canvas
	private int coordY = 0; // the y coordinate at the canvas
	private int id; // gives every ball his own id, for now not necessary
	private static int count = 1;
	private boolean goRight = true;
	private boolean goDown = true;
	private float angle = 0;
	private boolean isCarried=false;
	private String carrier = "";
	private Context context;
	
	public void setCarry(String carrier){
		isCarried = true;
		this.carrier = carrier;
	}
	
	public void setUncarry(){
		isCarried = false;
		this.carrier = "";
	}
 
	public float getAngle(){
		return angle;
	}
 
	public void draw(Canvas canvas){
		Matrix matrix = new Matrix();
		matrix.postRotate(getAngle());
		
		
		Bitmap resizedBitmap = Bitmap.createBitmap(getBitmap(), 0, 0, getBitmap().getScaledWidth(canvas) , getBitmap().getScaledHeight(canvas), matrix, true);
		
		
        canvas.drawBitmap(resizedBitmap, getX(), getY(), new Paint());
        
        // if the card is being carried by another player a hand and the name of the carrier would be drawn near the card's image.
        if(isCarried){
        	Paint paint = new Paint(); 
			   
			  
			   
        	// draws the name of the carrier.
            paint.setColor(Color.BLACK); 
            paint.setTextSize(20); 
            canvas.drawText(carrier,getX(), getY(), paint);
 
            // draws the hand.
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hand),getX()-25, getY()+20 , paint);
        }
	}
	
	public void randomizeAngle(){
		Random generator = new Random();
		float randomIndex = generator.nextInt(20);
		randomIndex -= 10;
		angle = randomIndex;
	}
	
	public void setAngle(float angle){
		this.angle = angle;
	}
 	
	public Card(Context context, int drawable) {

		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        img = BitmapFactory.decodeResource(context.getResources(), drawable); 
        id=count;
		count++;

	}
	
	
	
	
	public Card(Context context, int drawable, Point point) {
		this.context = context;
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        img = BitmapFactory.decodeResource(context.getResources(), drawable); 
        id=count;
		count++;
		coordX= point.x;
		coordY = point.y;

	}
	
	
	
	public static int getCount() {
		return count;
	}
	
	void setX(int newValue) {
        coordX = newValue;
    }
	
	public int getX() {
		return coordX;
	}

	void setY(int newValue) {
        coordY = newValue;
   }
	
	public int getY() {
		return coordY;
	}
	
	public int getID() {
		return id;
	}
	
	public Bitmap getBitmap() {
		return img;
	}
	
	public void moveBall(int goX, int goY) {
		// check the borders, and set the direction if a border has reached
		
		if (coordX > 270){
			goRight = false;
		}
		if (coordX < 0){
			goRight = true;
		}
		if (coordY > 400){
			goDown = false;
		}
		if (coordY < 0){
			goDown = true;
		}
		// move the x and y 
		if (goRight){
			coordX += goX;
		}else
		{
			coordX -= goX;
		}
		if (goDown){
			coordY += goY;
		}else
		{
			coordY -= goY;
		}
		
	}
	
}
