package client.gui.animations;

import android.graphics.Color;
import android.view.animation.Interpolator;

public class ColorInterpolator {
	
	float mScaleFactor= 0.4f;
	float mEnd_Red;
	float mEnd_Green;
	float mEnd_Blue;
	float mEnd_Alpha;
	float mStart_Alpha;
	float mStart_Red;
	float mStart_Green;
	float mStart_Blue;
	
	
	ColorInterpolator(int source,int dest){
		
		mEnd_Alpha=Color.alpha(dest);
		mEnd_Red=Color.red(dest);
		mEnd_Green=Color.green(dest);
		mEnd_Blue=Color.blue(dest);
		mStart_Red=Color.red(source);
		mStart_Green=Color.green(source);
		mStart_Blue=Color.blue(source);
		mStart_Alpha =Color.alpha(source);
	}
	
	
	public int getInterpolatedColor(float t) {
	    if (t<0) {t=0f;}
	    if (t>1) {t=1f;}
	    int lEnd_Red = (int) (mScaleFactor*mEnd_Red);
	    int lEnd_Green = (int) (mScaleFactor*mEnd_Green);
	    int lEnd_Blue = (int) (mScaleFactor*mEnd_Blue);

	    return Color.argb(
	      interpolateInt(mStart_Alpha,mEnd_Alpha-mStart_Alpha,t),
	      interpolateInt(mStart_Red,lEnd_Red,t),
	      interpolateInt(mStart_Green,lEnd_Green,t),
	      interpolateInt(mStart_Blue,lEnd_Blue,t));
	  }
	public int interpolateInt(float a, float  b, float t) {
	    return (int) (a + (b-a)*t);
	 }

}
