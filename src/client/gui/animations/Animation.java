package client.gui.animations;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

public abstract class Animation implements Runnable{
	public void execute(){
		//new Thread(this).start();
		GameEnvironment.get().getExecutor().execute(this);
	}
	@Override
	public void run(){
		animate();
		postAnimation();
	}
	
	protected abstract void animate();
	protected abstract void postAnimation();
}
