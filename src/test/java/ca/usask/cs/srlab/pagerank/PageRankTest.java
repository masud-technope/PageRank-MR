package ca.usask.cs.srlab.pagerank;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PageRankTest {

    @Test
    public void testGetPageRanks(){

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

        boolean normalizeScore=false;

        HashMap<String,Double> tokendb=new PageRankProviderMgr(graph).getPageRanks(normalizeScore);
        System.out.println(tokendb);
        assertFalse(tokendb.isEmpty());
    }

}
