package pull_model;

import java.util.ArrayList;

/**
 * 
 * Contains the {@link main(String[]) main} method.
 *
 */
public class Main {
	
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
	public static Initialization<Integer> FollowTheTrend(boolean randomOpinions, int numberOfOpinions) {
		
		return new Initialization<Integer>() {

			@Override
			public Dynamics<Integer> initialize(int n) {
				int l = (int) (10*Math.log(n));
				ArrayList<Agent<Integer>> agents = new ArrayList<Agent<Integer>>(n);
				agents.add(new SourceAgent<Integer>(0));
				
				for (int i=1 ; i<n ; i++) {
					
					int opinion = randomOpinions ? Utils.random.nextInt(numberOfOpinions) : 1;
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
	public static Initialization<Integer> Voter(boolean randomOpinions, int numberOfOpinions) {
		
		return new Initialization<Integer>() {

			@Override
			public Dynamics<Integer> initialize(int n) {
				ArrayList<Agent<Integer>> agents = new ArrayList<Agent<Integer>>(n);
				agents.add(new SourceAgent<Integer>(0));
				for (int i=1 ; i<n ; i++) {
					
					int opinion = randomOpinions ? Utils.random.nextInt(numberOfOpinions) : 1;
					agents.add(new VoterAgent<Integer>(opinion));
				}
				Dynamics<Integer> core = new Dynamics<Integer>(agents,false);
				return core;
			}
			
		};
	}
	
	public static void main(String [] args) {
		
		int nMin = Integer.valueOf(args[0]);
		int nMax = Integer.valueOf(args[1]);
		
		int iterations = 1000;
		
		ArrayList<Initialization<Integer>> inits = new ArrayList<Initialization<Integer>>();
		inits.add(FollowTheTrend(true,2));
		inits.add(FollowTheTrend(false,2));
		inits.add(FollowTheTrend(true,10));
		inits.add(Voter(true,2));
		inits.add(Voter(false,2));
		inits.add(Voter(true,10));
		
		ArrayList<Integer> populationSize1 = new ArrayList<Integer>();
		ArrayList<Integer> populationSize2 = new ArrayList<Integer>();
		
		ArrayList<ArrayList<Double>> results = new ArrayList<ArrayList<Double>>();
		for (int i=0 ; i<6 ; i++) results.add(new ArrayList<Double>());
		
		
		for (int i=nMin ; i<nMax ; i++) {
			
			int n = (int) Math.pow(2,i);
			populationSize1.add(n);
			System.out.println("Processing n = "+n+";");
			
			results.get(0).add(avgConvergenceTime(n,inits.get(0),iterations));
			results.get(1).add(avgConvergenceTime(n,inits.get(1),iterations));
			results.get(2).add(avgConvergenceTime(n,inits.get(2),iterations));
			if (i<=10) {
				results.get(3).add(avgConvergenceTime(n,inits.get(3),iterations));
				results.get(4).add(avgConvergenceTime(n,inits.get(4),iterations));
				results.get(5).add(avgConvergenceTime(n,inits.get(5),iterations));
				populationSize2.add(n);
			}
			
			/*
			System.out.println("Follow the Trend (random): "+avgConvergenceTime(n,inits.get(0),100));
			System.out.println("Follow the Trend (pseudo-consensus): "+avgConvergenceTime(n,inits.get(1),100));
			if (i<=10) {
				System.out.println("Voter (random): "+avgConvergenceTime(n,inits.get(2),100));
				System.out.println("Voter (pseudo-consensus): "+avgConvergenceTime(n,inits.get(3),100));
			}
			*/
			
		}
		
		XMLWriter writer = new XMLWriter("test","xscale","log");
		
		writer.plot("populationSize1","data0","color","tab:blue","label","Follow the Trend","ls","-","marker","o");
		writer.plot("populationSize1","data1","color","tab:blue","label","Follow the Trend (Pseudo-consensus)","ls","--","marker","o");
		writer.plot("populationSize1","data2","color","tab:blue","label","Follow the Trend (10 opinions)","ls","dotted","marker","o");
		writer.plot("populationSize2","data3","color","tab:orange","label","Voter","ls","-","marker","^");
		writer.plot("populationSize2","data4","color","tab:orange","label","Voter (Pseudo-consensus)","ls","--","marker","^");
		writer.plot("populationSize2","data5","color","tab:orange","label","Voter (10 opinions)","ls","dotted","marker","^");
		
		writer.addData("populationSize1",populationSize1);
		writer.addData("populationSize2",populationSize2);
		for (int i=0 ; i<6 ; i++) writer.addData("data"+i,results.get(i));
		writer.close();
		
		System.out.println("Simulation over.");
				
	}
	
}





























