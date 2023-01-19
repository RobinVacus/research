package pull_model;

import java.util.ArrayList;

/**
 * Proxy whose purpose is to add "laziness" to another dynamics.
 * 
 * Every LazyAgent encapsulates an instance of another {@link Agent},
 * whose {@link Agent#update(ArrayList) update} method will only be called
 * every {@link clockDuration} activations.
 *
 * @param <T> The type of opinions handled by the encapsulated agent.
 */
public class LazyAgent<T> extends Agent<T> {
	
	private Agent<T> encapsulatedAgent;
	private int clock;
	
	/** Number of udpates required before the encapsulated agent is updated. */
	public final int clockDuration;

	/**
	 * Constructs a LazyAgent that will execute the {@link Agent#update(ArrayList) update}
	 * method of the encapsulated agent every {@link clockDuration} activations.
	 * 
	 * @param encapsulatedAgent
	 * @param clockDuration
	 */
	public LazyAgent(Agent<T> encapsulatedAgent, int clockDuration) {
		
		super(encapsulatedAgent.sampleSize,encapsulatedAgent.output());
		
		this.encapsulatedAgent = encapsulatedAgent;		
		this.clockDuration = clockDuration;
		this.clock = Utils.random.nextInt(clockDuration);
	}

	@Override
	public void update(ArrayList<T> samples) {
		
		if (clock > 0) {
			clock --;
			return;
		}
		
		clock = clockDuration-1;
		encapsulatedAgent.update(samples);
		this.opinion = encapsulatedAgent.output();
		
		
	}

}
