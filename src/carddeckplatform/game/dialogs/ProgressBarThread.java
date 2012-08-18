package carddeckplatform.game.dialogs;


public class ProgressBarThread implements Runnable{
	ActionWhileWaiting action;
	public ProgressBarThread(ActionWhileWaiting action) {
		this.action=action;
	}
	@Override
	public void run() {
		action.execute();
		
	}

}
