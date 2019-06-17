package core.pagerank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.border.Border;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class StackGraph extends JApplet {  

	SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> stackgraph;
	DirectedGraph<String, DefaultEdge> uwgraph;
	int maxWidth;
	int maxHeight;
	ArrayList<String> toptokens;

	private static final Color DEFAULT_BG_COLOR = Color.decode("#ffffff");
	private static final Dimension DEFAULT_SIZE = new Dimension(900, 700);
	private JGraphModelAdapter m_jgAdapter;
	
	public StackGraph() {
		// default constructor: never called
	}
	
	@Deprecated
	public StackGraph(DirectedGraph<String, DefaultEdge> stackgraph,
			int maxWidth, int maxHeight) {
		// initialization
		this.uwgraph = stackgraph;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}

	@Deprecated
	public StackGraph(DirectedGraph<String, DefaultEdge> stackgraph, int maxWidth, int maxHeight, ArrayList<String> toptokens)
	{
		this.uwgraph = stackgraph;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.toptokens=toptokens;
	}
	
	public StackGraph(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> stackgraph, int maxWidth, int maxHeight, ArrayList<String> toptokens)
	{
		this.stackgraph = stackgraph;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.toptokens=toptokens;
	}
	
	
	public void initWG()
	{
		//init the weighted graph
		if (stackgraph.vertexSet().isEmpty())
			return;
		// create a JGraphT graph
		ListenableDirectedGraph<String, ?> g = new ListenableDirectedGraph<>(
				DefaultEdge.class);
		// create a visualization using JGraph, via an adapter
		m_jgAdapter = new JGraphModelAdapter(g);
		JGraph jgraph = new JGraph(m_jgAdapter);

		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);
		customizeEdge();

		// adding vertices
		for (String vertex : stackgraph.vertexSet()) {
			if (!g.containsVertex(vertex)) {
				g.addVertex(vertex);
			}
		}
		// adding edges
		for (DefaultWeightedEdge  edge : stackgraph.edgeSet()) {
			// g.edgeSet().addAll(stackgraph.edgeSet());
			String v1 = stackgraph.getEdgeSource(edge);
			String v2 = stackgraph.getEdgeTarget(edge);
			try {
				if (!g.containsEdge(v1, v2)) {
					g.addEdge(v1, v2);
				}
			} catch (Exception e) {
				// no need to handle
			}
		}
		
		// positioning the vertices of the graph
		int xpos = 100;
		int ypos = 100;
		for (String vertex : stackgraph.vertexSet()) {
			Random random = new Random();
			while (true) {
				int temp = random.nextInt(maxWidth);
				if (temp > xpos && temp < maxWidth - 50) {
					xpos = temp;
					break;
				}
			}
			while (true) {
				int temp = random.nextInt(maxHeight);
				if (temp > ypos && temp < maxHeight - 50) {
					ypos = temp;
					break;
				}
			}
			// now position the vertex
			try{
			if(this.stackgraph.containsVertex(vertex)){
				  if(this.toptokens.contains(vertex)){
					  positionVertexAt(vertex, xpos, ypos, true);
				  }else{
					  positionVertexAt(vertex, xpos, ypos, false);
				  }
			}
			}catch(Exception exc){
				//do something?
			}
			xpos = 100;
			ypos = 100;
		}

		// position vertices nicely within JGraph component
		/*
		 * positionVertexAt( "v1", 130, 40 ); positionVertexAt( "v2", 60, 200 );
		 * positionVertexAt( "v3", 310, 230 ); positionVertexAt( "v4", 380, 70
		 * );
		 */
	}
	
	@SuppressWarnings("unchecked")
	public void init() {

		if (uwgraph.vertexSet().isEmpty())
			return;
		// create a JGraphT graph
		ListenableDirectedGraph<String, ?> g = new ListenableDirectedGraph<>(
				DefaultEdge.class);
		// create a visualization using JGraph, via an adapter
		m_jgAdapter = new JGraphModelAdapter(g);
		JGraph jgraph = new JGraph(m_jgAdapter);

		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);
		customizeEdge();

		// adding vertices
		for (String vertex : uwgraph.vertexSet()) {
			if (!g.containsVertex(vertex)) {
				g.addVertex(vertex);
			}
		}
		// adding edges
		for (DefaultEdge edge : uwgraph.edgeSet()) {
			// g.edgeSet().addAll(stackgraph.edgeSet());
			String v1 = uwgraph.getEdgeSource(edge);
			String v2 = uwgraph.getEdgeTarget(edge);
			try {
				if (!g.containsEdge(v1, v2)) {
					g.addEdge(v1, v2);
				}
			} catch (Exception e) {
				// no need to handle
			}
		}
		
		// positioning the vertices of the graph
		int xpos = 100;
		int ypos = 100;
		for (String vertex : uwgraph.vertexSet()) {
			Random random = new Random();
			while (true) {
				int temp = random.nextInt(maxWidth);
				if (temp > xpos && temp < maxWidth - 50) {
					xpos = temp;
					break;
				}
			}
			while (true) {
				int temp = random.nextInt(maxHeight);
				if (temp > ypos && temp < maxHeight - 50) {
					ypos = temp;
					break;
				}
			}
			// now position the vertex
			try{
			if(this.uwgraph.containsVertex(vertex)){
				  if(this.toptokens.contains(vertex)){
					  positionVertexAt(vertex, xpos, ypos, true);
				  }else{
					  positionVertexAt(vertex, xpos, ypos, false);
				  }
			}
			}catch(Exception exc){
				//do something?
			}
			xpos = 100;
			ypos = 100;
		}
	}

	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);
        Color c=Color.decode("#ffffff");// DEFAULT_BG_COLOR;;
		jg.setBackground(c);
		// jg.setBackground(F1COLOR);
		//Color c2=Color.GREEN;
		//jg.setForeground(c2);
		//jg.setForeground(Color.decode("#00eeff"));

	}

	protected void customizeEdge()
	{
		//customize an edge
		AttributeMap attr=m_jgAdapter.getDefaultEdgeAttributes();
		attr.put("linecolor", Color.LIGHT_GRAY);
		m_jgAdapter.setDefaultEdgeAttributes(attr);
		//System.out.println(attr);
	}
	
	
	private void positionVertexAt(Object vertex, int x, int y, boolean important) {
		DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
		AttributeMap attr = cell.getAttributes();
		Rectangle2D b = (Rectangle2D) GraphConstants.getBounds(attr);
		// GraphConstants.setBounds( attr, new Rectangle2D( x, y, b.getWidth(),
		// b.getHeight() ) );
		Rectangle rect = new Rectangle(x, y, (int) b.getWidth(),
				(int) b.getHeight());
		Rectangle2D rect2d=new Rectangle2D.Double(x,y,b.getWidth(), b.getHeight());
		//System.out.println(attr);
		GraphConstants.setBounds(attr, rect2d);
		//GraphConstants.setForeground(attr, Color.GREEN);
		
		
		Font font=new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		attr.put("font", font);
		Border border=BorderFactory.createEmptyBorder();
		attr.put("border", border);
		
		
		if(important){
		attr.put("foregroundColor", Color.BLACK);
		attr.put("backgroundColor", Color.GREEN);
		}else{
			attr.put("foregroundColor", Color.BLACK);
			attr.put("backgroundColor", Color.LIGHT_GRAY);
		}
		
		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		m_jgAdapter.edit(cellAttr, null, null, null);
	}
}
