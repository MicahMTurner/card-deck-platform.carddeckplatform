package client.gui.animations;

public abstract class Animation implements Runnable{
	public void execute(){
		new Thread(this).start();
	}
	@Override
	public void run(){
		animate();
		postAnimation();
	}
	
	protected abstract void animate();
	protected abstract void postAnimation();
}
