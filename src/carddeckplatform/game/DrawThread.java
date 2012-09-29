package carddeckplatform.game;

import client.gui.entities.Table;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
	protected SurfaceHolder surfaceHolder;

	protected boolean running = false;

	protected Table table;

	public void setRunning(boolean value) {
		running = value;
	}

	public DrawThread(SurfaceHolder surfaceHolder, Table table) {
		this.surfaceHolder = surfaceHolder;
		this.table = table;
	}

	@Override
	public void run() {
		Canvas c;
		while (running) {
			try {
				// Don't hog the entire CPU
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
			c = null;
			try {

				c = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					// System.out.println(c.getDensity());
					try {
						table.draw(c);// draw it
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			} finally {
				if (c != null) {
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}