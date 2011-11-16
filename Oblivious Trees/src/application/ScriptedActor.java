package application;

import java.util.concurrent.DelayQueue;

/** Runnable class that performs actions according to provided 
 * script of actions. Script consists of delay queue with actions
 * to perform for the test class.
 */
public class ScriptedActor extends TestActor {
	
	/** ScriptedActor default constructor
	 */
	public ScriptedActor(){
		test = null;
		actions = null;		
	}
	
	// Runnable
	/** run method to perform scripted actions
	 *  calls back to methods in test class to 
	 *  execute appropriate actions.
	 */
	public void run(){
		// if this actor has a test and actions set
		if(this.test!=null && this.actions!=null && !this.actions.isEmpty()){
			// if use timing set then make a delay queue to orchestrate actions
			if (this.useTiming){
				// create a delay queue from the actions list
				DelayQueue<Act> queue = new DelayQueue<Act>();
				for (Act a : this.actions){
					// start delay using script time and set into queue
					a.setDelayStart();
					queue.add(a);
				}
				// process every action in order as they pop from delay queue
				while (!queue.isEmpty()){
					try{
						Act tmp = queue.take();
						test.buttonPush(tmp);
					} catch (InterruptedException e){}
				}
			}
			// run tests back to back at full speed
			else {
				for(int i=0; i<this.actions.size(); i++){
					this.test.buttonPush(this.actions.get(i));
				}
			}
		}
	}
	
}
