package client.gui.animations;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


public class AnimationController {
	private static Timer timer;
	
	// locks.
	private static final Object nLock = new Object();
	private static final Object gLock = new Object();
	private static final Object aLock = new Object();
	
	// the animation list.
	private static LinkedList<AnimationTask> animationTasks = new LinkedList<AnimationTask>();
	// the newly born animations.
	private static LinkedList<AnimationTask> newlyBorn = new LinkedList<AnimationTask>();
	// the animation gravy yard.
	private static LinkedList<AnimationTask> graveyard = new LinkedList<AnimationTask>();

	
	// this anonymous timer task, handles the updating
	// of every registered object in the objects list
	private static TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// remove all the animations from the grave yard (the animations that were stopped).
			synchronized (gLock) {
				synchronized (aLock) {
					animationTasks.removeAll(graveyard);
					graveyard.clear();
				}
			}
			
			// add all newly born animations to the task list. 
			synchronized (nLock) {
				synchronized (aLock) {
					animationTasks.addAll(newlyBorn);
					newlyBorn.clear();
					
				}
			}
			
			// executes animations.
			for(AnimationTask animationTask : animationTasks){
				animationTask.update();
			}
		}
	};	
	
	
	//static initialization that executes only once
	static {
		timer = new Timer("Timer-Animations");
		timer.scheduleAtFixedRate(task, 0, 40);
	}
	
	/**
	 * adds a new animation.
	 * @param animationTask
	 */
	public static void registerAnimation(AnimationTask animationTask){
		newlyBorn.add(animationTask);
	}
	
	/**
	 * removes animation.
	 * @param animationTask
	 */
	public static void unregisterAnimation(AnimationTask animationTask){
		graveyard.add(animationTask);
	}
	
}
