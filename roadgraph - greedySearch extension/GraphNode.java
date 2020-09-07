package roadgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import geography.GeographicPoint;
import util.GraphLoader;

/*
 * Creates a node at a specified location
 * @Author - ER
 */

/* Implements Comparator for the priority queue */
public class GraphNode implements Comparable<GraphNode>{

	private GeographicPoint location;
	private List<GraphEdge> nextEdges;
	private List<GraphNode> neighbors;
	
	// Distance to start for Dijkstra and A* searches
	private double distanceToStart;
	// Predicted distance to the end node for A* search
	private double predictedDistance;
	
	/* Constructor sets the location and initialises lists, and
	 * the distances from start and goal to infinity
	 */
	public GraphNode (GeographicPoint geoPoint) {
		location = geoPoint;
		nextEdges = new ArrayList<GraphEdge>();
		neighbors = new ArrayList<GraphNode>();
		distanceToStart = Double.POSITIVE_INFINITY;
		predictedDistance = Double.POSITIVE_INFINITY;
	}
	
	/* Adds a neighbour node */
	public void addNeighbor (GraphNode neighbor) {
		neighbors.add(neighbor);
	}
	
	/* Adds an edge */
	public void addEdge(GraphEdge edge) {
		nextEdges.add(edge);
	}
	
	//GETTER + SETTER METHODS
	
	/* Returns a list of neighbour nodes */
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
	
	/* New helper method to find edgelength to a specified node */
	public double getEdgeLength(GraphNode n, Set<GraphEdge> edges){
		double edgeLength= 0;
		for (GraphEdge edge: edges) {
			if (edge.getStart().equals(this.getLocation()) &&
				edge.getEnd().equals(n.getLocation())) {
				edgeLength = edge.getLength();
			}
		}
		return edgeLength;
	}
	
	
	/* Returns the distance to start for Dijkstra algorithm */
	public double getDistanceToStart () {
		return distanceToStart;
	}
	
	/* Sets the distance to start for Dijkstra algorithm */
	public void setDistanceToStart (double distance) {
		distanceToStart = distance;
	}
	
	/* Returns the predicted distance to goal node for A* algorithm */
	public double getPredictedDistance () {
		return predictedDistance;
	}
	
	/* Sets the predicted distance to goal node for A* algorithm */
	public void setPredictedDistance (double distance) {
		predictedDistance = distance;
	}
	
	
	/* Compares the distance from the start for two nodes 
	 * for the priority queue
	 * */
	public int compareTo(GraphNode other) {
		// For the Dijsktra method:
		//return Double.compare(this.distanceToStart, other.distanceToStart);
		if (this.getDistanceToStart() + this.getPredictedDistance() < 
			other.getDistanceToStart() + other.getPredictedDistance()) {
			return -1;
		}
		else if (this.getDistanceToStart() + this.getPredictedDistance() > 
			other.getDistanceToStart() + other.getPredictedDistance()) {
			return 1;
		}
		else {
			return 0;
		}
		
	}
	
	/* Returns the strin representation of a graphNode */
	public String toString() {
		return ("Location is: " + location);
	}
	
	/* For debugging */
	public static void main(String[] args) {
		/*
		GeographicPoint gp = new GeographicPoint(1.0, 1.0);
		GraphNode newNode = new GraphNode(gp);
		System.out.println(newNode.getDistanceToStart());
		
		GeographicPoint n1 = new GeographicPoint(4.0, 6.0);
		GeographicPoint n2 = new GeographicPoint(6.0, 7.0);
		
		newNode.addNeighbor(n1);
		newNode.addNeighbor(n2);
		System.out.println(newNode.getNeighbors());*/
	}

}
