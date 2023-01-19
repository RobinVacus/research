package pull_model;

import java.util.ArrayList;

/**
 * 
 * Contains the {@link main(String[]) main} method.
 *
 */
public class Simulation {
	
	/**
	 * Computes the average convergence time in parallel rounds.
	 * 
	 * @param <T> The type of opinions handled by the agents.
	 * @param n The population size.
	 * @param init A description of the initial configuration.
	 * @param iterations The number of times over which the average should be computed.
	 * @return The average convergence time, in parallel rounds.
	 */
	public static <T> double avgConvergenceTime(int n, Initialization<T> init, int iterations) {
		
		double result = 0;
		
		for (int i=0 ; i<iterations ; i++) {
			
			Dynamics<T> core = init.initialize(n);
			core.run();
			result += core.getTime();
			
		}
		
		return result/iterations;
		
	}
	
	/**
	 * Return an {@link Initialization} of the "lazy follow the trend" dynamics
	 * with a unique source agent, and a sample size equal to
	 * 10 log n.
	 * 
	 * 
	 * @param randomOpinions If this is true, opinions will be drawn uniformly at random.
	 * Otherwise, they will be taken to be different from the correct opinion.
	 * @return An initialization of the "lazy follow the trend" dynamics.
	 */
	public static Initialization<Integer> FollowTheTrend(boolean randomOpinions) {
		
		return new Initialization<Integer>() {

			@Override
			public Dynamics<Integer> initialize(int n) {
				int l = (int) (10*Math.log(n));
				ArrayList<Agent<Integer>> agents = new ArrayList<Agent<Integer>>(n);
				agents.add(new SourceAgent<Integer>(0));
				
				for (int i=1 ; i<n ; i++) {
					
					int opinion = randomOpinions ? Utils.random.nextInt(2) : 1;
					agents.add(new LazyAgent<Integer>(new TrendAgent(l,opinion), l));
					
				}
				
				Dynamics<Integer> core = new Dynamics<Integer>(agents,false);
				return core;
			}
			
		};
	}
	
	/**
	 * Return an {@link Initialization} of the "Voter" dynamics
	 * with a unique source agent.
	 * 
	 * @param randomOpinions If this is true, opinions will be drawn uniformly at random.
	 * Otherwise, they will be taken to be different from the correct opinion.
	 * @return An initialization of the "Voter" dynamics.
	 */
	public static Initialization<Integer> Voter(boolean randomOpinions) {
		
		return new Initialization<Integer>() {

			@Override
			public Dynamics<Integer> initialize(int n) {
				ArrayList<Agent<Integer>> agents = new ArrayList<Agent<Integer>>(n);
				agents.add(new SourceAgent<Integer>(0));
				for (int i=1 ; i<n ; i++) {
					
					int opinion = randomOpinions ? Utils.random.nextInt(2) : 1;
					agents.add(new VoterAgent<Integer>(opinion));
				}
				Dynamics<Integer> core = new Dynamics<Integer>(agents,false);
				return core;
			}
			
		};
	}
	
	public static void main(String [] args) {
		
		ArrayList<Initialization<Integer>> inits = new ArrayList<Initialization<Integer>>();
		inits.add(FollowTheTrend(true));
		inits.add(FollowTheTrend(false));
		inits.add(Voter(true));
		inits.add(Voter(false));
		
		for (int i=3 ; i<6 ; i++) {
			
			int n = (int) Math.pow(2,i);
			System.out.println("n = "+n);
			System.out.println("Follow the Trend (random): "+avgConvergenceTime(n,inits.get(0),100));
			System.out.println("Follow the Trend (pseudo-consensus): "+avgConvergenceTime(n,inits.get(1),100));
			System.out.println("Voter (random): "+avgConvergenceTime(n,inits.get(2),100));
			System.out.println("Voter (pseudo-consensus): "+avgConvergenceTime(n,inits.get(3),100));
			
		}
		
		System.out.println("Simulation over.");
				
	}
	
}





























