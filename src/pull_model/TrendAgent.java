package pull_model;

import java.util.ArrayList;


/**
 * Implementation of the "Follow the Trend" algorithm from
 * <a href="https://arxiv.org/abs/2203.11522">Early Adapting to Trends:
 * Self-Stabilizing Information Spread using Passive Communication</a>.
 * 
 * Should only be used with binary opinions 0 and 1.
 * 
 */
public class TrendAgent extends Agent<Integer> {
	
	/** Number of 1's seen in the previous round */
	private int count;

	/**
	 * Constructs a TrendAgent with the specified sample size and initial opinion.
	 * 
	 * @param sampleSize The sample size required by the agent, that should be
	 * logarithmic in the population size.
	 * @param initialOpinion The correct opinion of the process.
	 */
	public TrendAgent(int sampleSize, int initialOpinion) {
		super(sampleSize,initialOpinion);
	}

	/**
	 * Compares the number of 1's in the current sample with the number in the previous sample.
	 * Updates the opinion accordingly to follow the trend.
	 */
	@Override
	public void update(ArrayList<Integer> samples) {
		
		int newCount = Utils.count(samples,1);
		if (newCount > count) opinion = 1;
		if (newCount < count) opinion = 0;
		count = newCount;
		
	}


}
