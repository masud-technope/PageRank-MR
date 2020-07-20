## PageRank

PageRank implementation in Java

How to Use?
------------
```Java
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
```

# Some not working as expected?
Please contact **Masud Rahman** (masud.rahman@dal.ca) or  [submit an issue](https://github.com/masud-technope/PageRank-MR/issues/new).
