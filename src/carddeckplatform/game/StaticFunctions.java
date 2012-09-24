package carddeckplatform.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import carddeckplatform.game.gameEnvironment.GameEnvironment;
import freeplay.customization.FreePlayProfile;

import utils.Point;


import android.os.Handler;



public class StaticFunctions {
	
	
	
	public static ArrayList<Point> midLine(int x0, int y0, int x1, int y1){
		ArrayList<Point> points = new ArrayList<Point>();
		boolean deltaybiggerdeltax = Math.abs(y1 - y0) > Math.abs(x1 - x0);
		
		if(x0==x1){
			if(y0<y1)
				for(int y = y0; y<=y1; y++ ){
					points.add(new Point(x0,y));
					System.out.println("(" + x0 + "," + y + ")");
				}
			else{
				for(int y = y0; y>=y1; y-- ){
					points.add(new Point(x0,y));
					System.out.println("(" + x0 + "," + y + ")");
				}
			}
		}else if(y0==y1){
			if(x0<x1)
				for(int x = x0; x<=x1; x++ ){
					points.add(new Point(x,y0));
					System.out.println("(" + x + "," + y0 + ")");
				}
			else{
				for(int x = x0; x>=x1; x-- ){
					points.add(new Point(x,y0));
					System.out.println("(" + x + "," + y0 + ")");
				}
			}
		}
		else if(x1>x0 && y1>y0 && !deltaybiggerdeltax){	//1 V
			int dy = y1-y0;
			int dx = x1-x0;
			int p=dy-dx/2;
			int y=y0;
			for (int x=x0; x <=x1; x++){
				points.add(new Point(x,y));
				System.out.println("(" + x + "," + y + ")");	
				if(p > 0) {
					y++;
					p += dy-dx;
				}
				else
					p += dy;
			}
			
		}
		else if(x1>x0 && y1>y0 && deltaybiggerdeltax){	//2 V
			int dy = y1-y0;
			int dx = x1-x0;
			int p=dx-dy/2;
			int x=x0;
			for (int y=y0; y <=y1; y++){
				points.add(new Point(x,y));
				System.out.println("(" + x + "," + y + ")");	
				if(p > 0) {
					x++;
					p += dx-dy;
				}
				else
					p += dx;
			}
			
		}else if(x1>x0 && y1<y0 && deltaybiggerdeltax){	//3 V
			int dy = y0-y1;
			int dx = x1-x0;
			int p=dx-dy/2;
			int x=x0;
			for (int y=y0; y >=y1; y--){
				points.add(new Point(x,y));
				System.out.println("(" + x + "," + y + ")");	
				if(p > 0) {
					x++;
					p += dx-dy;
				}
				else
					p += dx;
			}
			
		}else if(x1>x0 && y1<y0 && !deltaybiggerdeltax){	//4 V
			int dy = y0-y1;
			int dx = x1-x0;
			int p=dy-dx/2;
			int y=y0;
			for (int x=x0; x <=x1; x++){
				points.add(new Point(x,y));
				System.out.println("(" + x + "," + y + ")");	
				if(p > 0) {
					y--;
					p += dy-dx;
				}
				else
					p += dy;
			}
			
		}else if(x1<x0 && y1<y0 && !deltaybiggerdeltax){	//5 V
			int dy = y0-y1;
			int dx = x0-x1;
			int p=dy-dx/2;
			int y=y0;
			for (int x=x0; x >=x1; x--){
				points.add(new Point(x,y));
				System.out.println("(" + x + "," + y + ")");	
				if(p > 0) {
					y--;
					p += dy-dx;
				}
				else
					p += dy;
			}
			
		}else if(x1<x0 && y1<y0 && deltaybiggerdeltax){	//6 V
			int dy = y0-y1;
			int dx = x0-x1;
			int p=dx-dy/2;
			int x=x0;
			for (int y=y0; y >=y1; y--){
				points.add(new Point(x,y));
				System.out.println("(" + x + "," + y + ")");	
				if(p > 0) {
					x--;
					p += dx-dy;
				}
				else
					p += dx;
			}
			
		}else if(x1<x0 && y1>y0 && deltaybiggerdeltax){	//7
			int dy = y1-y0;
			int dx = x0-x1;
			int p=dx-dy/2;
			int x=x0;
			for (int y=y0; y <=y1; y++){
				points.add(new Point(x,y));
				System.out.println("(" + x + "," + y + ")");	
				if(p > 0) {
					x--;
					p += dx-dy;
				}
				else
					p += dx;
			}
			
		}
		else{// if(x1<x0 && y1>y0 && !deltaybiggerdeltax)	//8
			int dy = y1-y0;
			int dx = x0-x1;
			int p=dy-dx/2;
			int y=y0;
			for (int x=x0; x >=x1; x--){
				points.add(new Point(x,y));
				System.out.println("(" + x + "," + y + ")");	
				if(p > 0) {
					y++;
					p += dy-dx;
				}
				else
					p += dy;
			}
			
		}
		return points;
	}
	
	
	public static Object readFile(String path){
		Object res=null;
		String appPath = GameEnvironment.path;
		File myFile=new File(appPath + path);
		
		FileInputStream fOut;
		try {
			fOut = new FileInputStream(myFile);
			ObjectInput myOutWriter = new ObjectInputStream(fOut);
			res = myOutWriter.readObject();	
			myOutWriter.close();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void writeFile(Object obj, String path){
		String appPath = GameEnvironment.path;
		File myFile=new File(appPath + path);
		try {
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			ObjectOutput myOutWriter = new ObjectOutputStream(fOut);
			myOutWriter.writeObject(obj);
			myOutWriter.close();
			fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(String path){
		String appPath = GameEnvironment.path;
		File myFile=new File(appPath + path);
		myFile.delete();
		myFile = null;
	}


	public static FileOutputStream getPluginOutputStream(String filename) throws FileNotFoundException {
		return new FileOutputStream(GameEnvironment.path+"plugins/"+filename);
		
		
		
	}
	
}
