package client.gui.entities;

import utils.Point;
import carddeckplatform.game.gameEnvironment.GameEnvironment;

/**
 * Performs metrics conversions.
 * used create a singular coordinate system and convert points that came from other coordinate system.
 * @author Irzh
 *
 */
public class MetricsConvertion {
	private static double metrics=100;
	
	/**
	 * converts relative point to point in pixels.
	 * @param p
	 * @return
	 */
	public static Point pointPxToRelative(Point p){
		
		double x = p.getX();
		double y = p.getY();
		double width = GameEnvironment.get().getDeviceInfo().getScreenWidth();
		double height = GameEnvironment.get().getDeviceInfo().getScreenHeight();
		
		Point newP = new Point((int)((x/width)*metrics), (int)((y/height)*metrics));
		return newP;
	}
	
	/**
	 * converts point in pixels to relative point.
	 * @param p
	 * @return
	 */
	public static Point pointRelativeToPx(Point p){
		double x = p.getX();
		double y = p.getY();
		double width = GameEnvironment.get().getDeviceInfo().getScreenWidth();
		double height = GameEnvironment.get().getDeviceInfo().getScreenHeight();
		
		Point newP = new Point((int)((x*width)/metrics), (int)((y*height)/metrics));
		return newP;
	}
	
	/**
	 * get RELATIVE point that represents the relative coordinate system of the top player and convert it to bottom
	 * player's (me) relative coordinate system.
	 * @param p
	 * @return	Point in  
	 */
	public static Point fromTop(Point p){
		return new Point((float)(metrics-p.getX()) , (float)(metrics-p.getY()));
	}
	
	/**
	 * get RELATIVE point that represents the relative coordinate system of the right player and convert it to bottom
	 * player's (me) relative coordinate system.
	 * @param p
	 * @return	Point in  
	 */
	public static Point fromRight(Point p){
		return new Point((float)(p.getY()) , (float)(metrics-p.getX()));
	}
	
	/**
	 * get RELATIVE point that represents the relative coordinate system of the left player and convert it to bottom
	 * player's (me) relative coordinate system.
	 * @param p
	 * @return	Point in  
	 */
	public static Point fromLeft(Point p){
		return new Point((float)(metrics-p.getY()) , (float)(p.getX()));
	}
}
