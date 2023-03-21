package pull_model;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Implementation of the "Follow the Trend" algorithm from
 * <a href="https://arxiv.org/abs/2203.11522">Early Adapting to Trends:
 * Self-Stabilizing Information Spread using Passive Communication</a>.
 * 
 * The implementation here is extended to non binary opinions,
 * with the same philosophy.
 * 
 */
public class TrendAgent extends Agent<Integer> {
	
	ArrayList<Integer> lastSample;
	HashMap<Integer,Integer> oldMap,newMap;
	ArrayList<Integer> candidates;
	
	/**
	 * Constructs a TrendAgent with the specified sample size and initial opinion.
	 * 
	 * @param sampleSize The sample size required by the agent, that should be
	 * logarithmic in the population size.
	 * @param initialOpinion The correct opinion of the process.
	 */
	public TrendAgent(int sampleSize, Integer initialOpinion) {
		super(sampleSize, initialOpinion);
		
		lastSample = new ArrayList<Integer>();
		oldMap = new HashMap<Integer,Integer>();
		newMap = new HashMap<Integer,Integer>();
		candidates = new ArrayList<Integer>();
	}
	
	/**
	 * Compares the number of opinions in the current sample with the number in the previous sample.
	 * Updates the opinion accordingly to "follow the trend":
	 * Pick the opinion with the highest derivative.
	 */
	@Override
	public void update(ArrayList<Integer> samples) {
		
		HashMap<Integer,Integer> tmp = oldMap;
		oldMap = newMap;
		newMap = tmp;
		newMap.clear();
		
		for (Integer i : samples) {
			if (newMap.containsKey(i)) newMap.put(i,newMap.get(i)+1);
			else newMap.put(i,1);
		}
		
		candidates.clear();
		int maxDerivative = 0;
		
		for (Integer i : newMap.keySet()) {
			int j = newMap.get(i) - (oldMap.containsKey(i) ? oldMap.get(i) : 0);
			if (j > maxDerivative) {
				maxDerivative = j;
				candidates.clear();
				candidates.add(i);
			} else if (j == maxDerivative) {
				candidates.add(i);
			}
		}
		
		opinion = candidates.get(Utils.random.nextInt(candidates.size()));
		
	}

}
