package pull_model;

import java.util.ArrayList;

/**
 * Implementation of a source agent, which keeps the same opinion
 * (called correct opinion) throughout the execution.
 *
 * @param <T> The type of opinions handled by the agent.
 */
public class SourceAgent<T> extends Agent<T> {
	
	/**
	 * Constructs a SourceAgent with the specified correct opinion.
	 * 
	 * @param initialOpinion The correct opinion of the process.
	 */
	public SourceAgent(T initialOpinion) {
		super(0,initialOpinion);
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void update(ArrayList<T> samples) {
		
	}



}
