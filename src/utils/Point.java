package utils;

import java.io.Serializable;


public class Point implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8342492823898197984L;
	float x;
	float y;
	public Point(float x, float y) {
		this.x=x;
		this.y=y;
	}
	public float getX() {
			return x;
	}
	public float getY() {
			return y;
	}	
	public void move(float x,float y){
			this.x=x;
			this.y=y;
	}
	public void setX(float x) {
			this.x = x;
	}
	public void setY(float y) {
			this.y = y;
	}
	
	/************************************************************************************************************************************
	 * 
	 * 		methods  needed  for multi touch  
	 * 
	 * 
	 ************************************************************************************************************************************/
	
	public Point() {
	}

	public Point(Point v) {
		this.x = v.x;
		this.y = v.y;
	}


	public float getLength() {
		return (float)Math.sqrt(x * x + y * y);
	}

//	public Point set(Point other) {
//		x = other.getX();
//		y = other.getY();
//		return this;
//	}

	public Point set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Point add(Point value) {
		this.x += value.getX();
		this.y += value.getY();
		return this;
	}

	public static Point subtract(Point lhs, Point rhs) {
		return new Point(lhs.x - rhs.x, lhs.y - rhs.y);
	}

	public static float getDistance(Point lhs, Point rhs) {
		Point delta = Point.subtract(lhs, rhs);
		return delta.getLength();
	}

	public static float getSignedAngleBetween(Point a, Point b) {
		Point na = getNormalized(a);
		Point nb = getNormalized(b);

		return (float)(Math.atan2(nb.y, nb.x) - Math.atan2(na.y, na.x));
	}

	public static Point getNormalized(Point v) {
		float l = v.getLength();
		if (l == 0)
			return new Point();
		else
			return new Point(v.x / l, v.y / l);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
