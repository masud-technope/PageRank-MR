package core.pagerank;

import java.util.HashMap;
import java.util.Set;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import ca.usask.cs.srlab.pagerank.config.StaticData;

public class PageRankProvider {
	public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> wgraph;
	public DirectedGraph<String, DefaultEdge> graph;
	HashMap<String, Double> tokendb;
	HashMap<String, Double> oldScoreMap;
	HashMap<String, Double> newScoreMap;
	final double EDGE_WEIGHT_TH = StaticData.EDGE_WEIGHT_TH;
	final double INITIAL_VERTEX_SCORE = StaticData.INITIAL_VERTEX_SCORE;
	final double DAMPING_FACTOR = StaticData.DAMPING_FACTOR;
	final int MAX_ITERATION = StaticData.MAX_ITERATION;

	public PageRankProvider(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> wgraph,
			HashMap<String, Double> tokendb) {
		// initialization of different objects
		// weighted graph constructor
		this.wgraph = wgraph;
		this.tokendb = tokendb;
		this.oldScoreMap = new HashMap<>();
		this.newScoreMap = new HashMap<>();
	}

	public PageRankProvider(DirectedGraph<String, DefaultEdge> graph, HashMap<String, Double> tokendb) {
		// un-weighted graph constructor
		this.graph = graph;
		this.tokendb = tokendb;
		this.oldScoreMap = new HashMap<>();
		this.newScoreMap = new HashMap<>();
	}

	boolean checkSignificantDiff(double oldV, double newV) {
		double diff = 0;
		if (newV > oldV)
			diff = newV - oldV;
		else
			diff = oldV - newV;
		return diff > StaticData.SIGNIFICANCE_THRESHOLD ? true : false;
	}

	public HashMap<String, Double> calculatePageRankWeighted(boolean normalize) {
		// calculating token rank score
		double d = this.DAMPING_FACTOR;
		double N = wgraph.vertexSet().size();
		// initially putting 1 to all
		for (String vertex : wgraph.vertexSet()) {
			oldScoreMap.put(vertex, this.INITIAL_VERTEX_SCORE);
			newScoreMap.put(vertex, this.INITIAL_VERTEX_SCORE);
		}
		boolean enoughIteration = false;
		int itercount = 0;

		while (!enoughIteration) {
			int insignificant = 0;
			for (String vertex : wgraph.vertexSet()) {
				Set<DefaultWeightedEdge> incomings = wgraph.incomingEdgesOf(vertex);
				// now calculate the PR score
				double trank = (1 - d);
				double comingScore = 0;
				for (DefaultWeightedEdge edge : incomings) {
					String source1 = wgraph.getEdgeSource(edge);
					int outdegree = wgraph.outDegreeOf(source1);
					// score and out degree should be affected by the edge weight
					double score = oldScoreMap.get(source1);
					// adding edge weight
					double edgeWeight = wgraph.getEdgeWeight(edge);
					// edgeWeight=1; //by default 1.0
					score = score * edgeWeight;

					if (outdegree == 0)
						comingScore += score;
					else
						comingScore += (score / outdegree);
				}
				comingScore = comingScore * d;
				trank += comingScore;
				boolean significant = checkSignificantDiff(oldScoreMap.get(vertex).doubleValue(), trank);
				if (significant) {
					newScoreMap.put(vertex, trank);
				} else {
					insignificant++;
				}
			}
			// coping values to new Hash Map
			for (String key : newScoreMap.keySet()) {
				oldScoreMap.put(key, newScoreMap.get(key));
			}
			itercount++;
			if (insignificant == wgraph.vertexSet().size())
				enoughIteration = true;
			if (itercount == MAX_ITERATION)
				enoughIteration = true;
		}
		System.out.println("Iter count:" + itercount);
		// saving token ranks
		if (normalize) {
			recordNormalizedScores();
		} else {
			recordOriginalScores();
		}
		// sort the token rank scores
		// this.tokendb = MyItemSorter.sortItemMap(this.tokendb);
		// showing token rank scores
		// showTokenRanks();
		return this.tokendb;
	}

	public HashMap<String, Double> calculatePageRank(boolean normalize) {
		// calculating token rank score
		double d = this.DAMPING_FACTOR;
		double N = graph.vertexSet().size();
		// initially putting 1 to all
		for (String vertex : graph.vertexSet()) {
			oldScoreMap.put(vertex, this.INITIAL_VERTEX_SCORE);
			newScoreMap.put(vertex, this.INITIAL_VERTEX_SCORE);
		}
		boolean enoughIteration = false;
		int itercount = 0;

		while (!enoughIteration) {
			int insignificant = 0;
			for (String vertex : graph.vertexSet()) {
				Set<DefaultEdge> incomings = graph.incomingEdgesOf(vertex);
				// now calculate the PR score
				double trank = (1 - d);
				double comingScore = 0;
				for (DefaultEdge edge : incomings) {
					String source1 = graph.getEdgeSource(edge);
					int outdegree = graph.outDegreeOf(source1);

					// score and out degree should be affected by the edge
					// weight
					double score = oldScoreMap.get(source1);
					// score=score*this.EDGE_WEIGHT_TH;

					if (outdegree == 1)
						comingScore += score;
					else if (outdegree > 1)
						comingScore += (score / outdegree);
				}
				comingScore = comingScore * d;
				trank += comingScore;
				boolean significant = checkSignificantDiff(oldScoreMap.get(vertex).doubleValue(), trank);
				if (significant) {
					newScoreMap.put(vertex, trank);
				} else {
					insignificant++;
				}
			}
			// coping values to new Hash Map
			for (String key : newScoreMap.keySet()) {
				oldScoreMap.put(key, newScoreMap.get(key));
			}
			itercount++;
			if (insignificant == graph.vertexSet().size())
				enoughIteration = true;
			if (itercount == this.MAX_ITERATION)
				enoughIteration = true;
		}

		// saving token ranks
		if (normalize) {
			recordNormalizedScores();
		} else {
			recordOriginalScores();
		}
		return this.tokendb;
	}

	protected void recordOriginalScores() {
		for (String key : newScoreMap.keySet()) {
			double score = newScoreMap.get(key).doubleValue();
			tokendb.put(key, score);
		}
	}

	protected void recordNormalizedScores() {
		// record normalized scores
		double maxRank = 0;
		for (String key : newScoreMap.keySet()) {
			double score = newScoreMap.get(key).doubleValue();
			if (score > maxRank) {
				maxRank = score;
			}
		}
		for (String key : newScoreMap.keySet()) {
			double score = newScoreMap.get(key).doubleValue();
			score = score / maxRank;
			tokendb.put(key, score);
		}
	}

	protected void showTokenRanks() {
		// showing token ranks
		for (String key : this.tokendb.keySet()) {
			System.out.println(key + " " + tokendb.get(key));
		}
	}
}
