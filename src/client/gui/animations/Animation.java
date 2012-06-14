package client.gui.animations;

import java.util.concurrent.CountDownLatch;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

public abstract class Animation implements Runnable{	
	private CountDownLatch cdl;
	public Animation() {		
		this.cdl=new CountDownLatch(1);
	}
	public void execute(){
		//new Thread(this).start();//
		GameEnvironment.get().getExecutor().execute(this);
	}
	@Override
	public void run(){
		animate();
		postAnimation();
		cdl.countDown();		
	}
	
	protected abstract void animate();
	protected abstract void postAnimation();
	public void waitForMe(){		
		try {
			cdl.await();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}		
	}
}
