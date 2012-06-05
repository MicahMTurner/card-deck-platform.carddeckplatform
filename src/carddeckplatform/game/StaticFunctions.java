package carddeckplatform.game;

import java.util.ArrayList;

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
}
