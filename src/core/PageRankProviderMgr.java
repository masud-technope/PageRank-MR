package core;

import java.util.HashMap;
import java.util.HashSet;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class PageRankProviderMgr {

	public DirectedGraph<String, DefaultEdge> graph;

	public PageRankProviderMgr(DirectedGraph<String, DefaultEdge> graph) {
		this.graph = graph;
	}

	public HashMap<String, Double> getPageRanks() {
		if (this.graph != null) {
			HashSet<String> vertices = new HashSet<>(this.graph.vertexSet());
			HashMap<String, Double> tokendb = new HashMap<>();
			for (String token : vertices) {
				tokendb.put(token, 0.0);
			}
			// now get the pageRank
			PageRankProvider prProvider = new PageRankProvider(graph, tokendb);
			return prProvider.calculatePageRank();
		}
		return null;

	}

	public static void main(String[] args) {
		// this is the main method for pagerank
		DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(
				DefaultEdge.class);
		// add the vertex
		graph.addVertex("1");
		graph.addVertex("2");
		graph.addVertex("3");
		graph.addVertex("4");
		// add the edges
		graph.addEdge("1", "2");
		graph.addEdge("2", "3");
		graph.addEdge("2", "4");
		graph.addEdge("3", "2");
		graph.addEdge("4", "3");
		
		HashMap<String,Double> tokendb=new PageRankProviderMgr(graph).getPageRanks();
		System.out.println(tokendb);
	}
}
