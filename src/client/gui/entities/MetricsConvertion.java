package client.gui.entities;

import carddeckplatform.game.GameEnvironment;
import utils.Point;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class MetricsConvertion {
//	/**
//	 * This method convets dp unit to equivalent device specific value in pixels. 
//	 * 
//	 * @param dp A value in dp(Device independent pixels) unit. Which we need to convert into pixels
//	 * @param context Context to get resources and device specific display metrics
//	 * @return A float value to represent Pixels equivalent to dp according to device
//	 */
//	public static float convertDpToPixel(float dp){	
//		DisplayMetrics metrics = GameStatus.metrics;
//	    float px = dp * (metrics.densityDpi/160f);
//	    return px;
//	}
//	/**
//	 * This method converts device specific pixels to device independent pixels.
//	 * 
//	 * @param px A value in px (pixels) unit. Which we need to convert into dp
//	 * @param context Context to get resources and device specific display metrics
//	 * @return A float value to represent db equivalent to px value
//	 */
//	public static float convertPixelToDp(float px){
//	    DisplayMetrics metrics = GameStatus.metrics;
//	    float dp = px / (metrics.densityDpi / 160f);
//	    return dp;
//
//	}
	
	public static Point pointPxToRelative(Point p){
		
		double x = p.getX();
		double y = p.getY();
		double width = GameEnvironment.getGameEnvironment().getDeviceInfo().getScreenWidth();
		double height = GameEnvironment.getGameEnvironment().getDeviceInfo().getScreenHeight();
		
		Point newP = new Point((int)((x/width)*100.0), (int)((y/height)*100.0));
		return newP;
	}
	
	public static Point pointRelativeToPx(Point p){
		double x = p.getX();
		double y = p.getY();
		double width = GameEnvironment.getGameEnvironment().getDeviceInfo().getScreenWidth();
		double height = GameEnvironment.getGameEnvironment().getDeviceInfo().getScreenHeight();
		
		Point newP = new Point((int)((x*width)/100.0), (int)((y*height)/100.0));
		return newP;
	}
}
