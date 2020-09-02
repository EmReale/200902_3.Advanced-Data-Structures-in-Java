package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

/*
 * Creates a node at a specified location
 * @Author - ER
 */
public class GraphNode {
	
	private GeographicPoint location;
	private List<GraphEdge> nextEdges;
	private List<GraphNode> neighbors;
	
	/* Constructor sets the location and initialises lists */
	public GraphNode () {
		location = null;
		nextEdges = new ArrayList<GraphEdge>();
		neighbors = new ArrayList<GraphNode>();
	}
	
	/* Creates a new node with location specified */
	public GraphNode (GeographicPoint geoPoint) {
		location = geoPoint;
		nextEdges = new ArrayList<GraphEdge>();
		neighbors = new ArrayList<GraphNode>();
	}
	
	/* Adds a neighbour node */
	public void addNeighbor (GraphNode neighbor) {
		neighbors.add(neighbor);
	}
	
	/* Adds an edge to the node */
	public void addEdge(GraphEdge edge) {
		nextEdges.add(edge);
	}
	
	//GETTER METHODS
	
	/* Returns a list of neighbour nodes to be populated 
	   in MapGraph */
	public List<GraphNode> getNeighbors() {
		return neighbors;
	}
	
	/* Returns a list of edges */
	public List<GraphEdge> getEdges() {
		return new ArrayList<GraphEdge>(nextEdges);
	}
	
	/* Returns the location */
	public GeographicPoint getLocation() {
		return location;
	}
	
	/* Returns the string representation of a node */
	public String toString() {
		return ("Location is: " + location);
	}
	
	/* For debugging */
	public static void main(String[] args) {
		/*GeographicPoint gp = new GeographicPoint(1.0, 1.0);
		GraphNode newNode = new GraphNode(gp);
		System.out.println(newNode);
		
		GeographicPoint n1 = new GeographicPoint(4.0, 6.0);
		GeographicPoint n2 = new GeographicPoint(6.0, 7.0);
		
		newNode.addNeighbour(n1);
		newNode.addNeighbour(n2);
		System.out.println(newNode.getNeighbours());*/
		
	}

}
