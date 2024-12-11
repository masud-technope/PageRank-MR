package ca.usask.cs.srlab.pagerank;

public interface StaticData {

	// public attributes used by PageRank
	/***
	 * attributes are taken from Mihalcea et al. 
	 * 
	 */
	
	public static double SIGNIFICANCE_THRESHOLD = 0.0001;
	public final static int WINDOW_SIZE = 2;

	public final double EDGE_WEIGHT_TH = 0.25;
	public final double INITIAL_VERTEX_SCORE = 0.25;
	public final double DAMPING_FACTOR = 0.85;
	public final int MAX_ITERATION = 100;

}
