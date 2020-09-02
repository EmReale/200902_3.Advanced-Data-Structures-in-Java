
package roadgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;
import roadgraph.GraphNode;
import roadgraph.GraphEdge;

/**
 * @author UCSD MOOC development team and ER
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between roads
 *
 */

public class MapGraph { 
	private int numVertices;
	private int numEdges;
	
	private Map<GeographicPoint, GraphNode> nodeMap;
	private Set<GraphEdge> edges;
	
	/* Constructor creates a new empty MapGraph */
	public MapGraph(){
		nodeMap = new HashMap<GeographicPoint, GraphNode>();
		edges = new HashSet<GraphEdge>();
		numVertices = 0;
		numEdges = 0;
	}
	
	/* Returns the number of vertices in the graph */
	public int getNumVertices(){
		return nodeMap.size();
	}
	
	/* Return the intersections i.e vertices in the graph */
	public Set<GeographicPoint> getVertices(){
		Set<GeographicPoint> nodes = new HashSet<GeographicPoint>();
		for (GeographicPoint g: nodeMap.keySet()) {
			nodes.add(g);
		}
		return nodes;
	}
	
	/* Returns the no. of road sections i.e edges in the graph */
	public int getNumEdges(){
		return numEdges;
	}

	
	/** Adds a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph. */
	public boolean addVertex(GeographicPoint location){
		GraphNode newNode = new GraphNode(location);
		//If the node isn't already in the graph, add it and return true
		if (!nodeMap.containsKey(location)) {
			nodeMap.put(location,newNode);
			numVertices ++;
			return true;
		}
		//If it was already in the graph, return false
		else {
			return false;
		}
	}
	
	/**
	 * Adds a directed edge to the graph from 'from' to 'to'.  
	 * Both GeographicPoints must have already been added to the graph
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		
		// Throw an exception if either of the vertices don't exist
		if (!nodeMap.containsKey(from) || !nodeMap.containsKey(to)) {
			throw new NullPointerException("There is no node at this location");	
		}
		// Throws and exception if any of the information hasn't been provided
		if (from==null || to==null || roadName == null || roadType==null || length<0) {
			throw new NullPointerException("Null arguments are not allowed");
		}
		// Finds the from node
		GraphNode currNode = nodeMap.get(from);
		// Creates a new edge and attaches it to the node
		GraphEdge edge = new GraphEdge(from,to,roadName,roadType,length);
		currNode.addEdge(edge);
		edges.add(edge);
		numEdges ++;
	}
		
	
	/* New helper method - returns list of currNode neighbours */
	private List<GraphNode> returnNeighbors(GraphNode currNode) {
		// Finds the neighbours by returning the end point of associated edges
		// And adds them as neighbours
		List<GraphEdge> ge = currNode.getEdges();
		for (GraphEdge edge: ge) {
			currNode.addNeighbor(nodeMap.get(edge.getEnd()));
		}
		List<GraphNode> neighbors = currNode.getNeighbors();
		return neighbors;
	
	
	/* New helper method to build the path from the goal to the start */
	private List<GeographicPoint> buildPath (GeographicPoint start, GeographicPoint goal, Map<GraphNode,GraphNode> parentMap){
		// Initialises the list to return
		List<GeographicPoint> path = new LinkedList<>();
		if (!parentMap.isEmpty()) {
			// Finds the goal node within the parentMap and adds it to the 
			// start of the path
			GraphNode currNode = nodeMap.get(goal);
			path.add(0,(currNode.getLocation()));
			// Retraces steps until the start is reached
			while (!currNode.getLocation().equals(start)) {
				GraphNode prev = parentMap.get(currNode);
				System.out.println("Prev: " + prev);
				// Adds it to the start of the path
				path.add(0, (prev.getLocation()));
				currNode = prev;
			}
		}
		return path;
	}
	
	
	/** Finds the path from start to goal using breadth first search
	/* Dummy variable for calling the search algorithms */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		Consumer<GeographicPoint> temp = (x) -> {};
		return bfs(start, goal, temp);
	}
	
	
	/* Find the path from start to goal using breadth first search
	 * with a 'hook' for visualisation
	 */
	public List<GeographicPoint> bfs(GeographicPoint start,
					 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched){
		// Throw an exception if one of the points doesn't exist
		if (!nodeMap.containsKey(start) || !nodeMap.containsKey(goal)) {
			throw new NullPointerException("This location does not exist");
		}
		// Initialise: the queue of GraphNodes waiting to be visited
		// Linked list of the points its visited
		// ParentMap of currNode and nextNext for finding the path
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		Set<GraphNode> visited = new HashSet<GraphNode>();
		Map <GraphNode,GraphNode> parentMap = new HashMap <GraphNode,GraphNode>();
		
		// Find the start node and set it to currNode
		GraphNode currNode = nodeMap.get(start);
		// Add it to the queue and to visited
		queue.add(currNode);
		visited.add(currNode);
		
		// While the queue has nodes left to inspect
		while (!queue.isEmpty()) {
			// Remove and return the first node from the queue as currNode
			currNode = queue.remove();
			// Break from the loop if the goal has been reached
			if (currNode.getLocation().equals(goal)) {
				break;
			}
			// Find neighbours and add to the queue/ visited/ parentMap
			List<GraphNode> neighbors = returnNeighbors(currNode);
			for (GraphNode n: neighbors) {
				if (!visited.contains(n)) {
					visited.add(n);
					queue.add(n);
					parentMap.put(n,currNode);
					// Hook for visualization.
					nodeSearched.accept(n.getLocation());
				}
			}
		}
		
		// Build and return the path if it exists
		List<GeographicPoint> path = buildPath(start,goal,parentMap);
		if (path.size() > 0) {
			return path;	
		}
		return null;
	}
	
	/* Returns a string representation of the graph*/
	public String toString() {
		String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";
		return s;
	}

	
	/* Main method for testing */
	public static void main(String[] args){
		
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		//System.out.println(firstMap);
		
		//Test: add node
		GeographicPoint toAdd = new GeographicPoint(6.0, 1.0);
		firstMap.addVertex(toAdd);
		//System.out.println(firstMap);
		
		//Test: add nod (should return false)
		toAdd = new GeographicPoint(1.0, 1.0);
		firstMap.addVertex(toAdd);
		//System.out.println(firstMap.addVertex(toAdd));
		
		//Test: add Edge
		GeographicPoint from = new GeographicPoint(1.0, 1.0);
		GeographicPoint to = new GeographicPoint(4.0, 2.0);
		firstMap.addEdge(from, to, "Real Road", "Some type", 200);
		//System.out.println(firstMap.toString());
		
		//Test: Should return the location of currNode
		GeographicPoint start = new GeographicPoint(4.0, 1.0);
		GeographicPoint goal = new GeographicPoint(4.0, 1.0);
		firstMap.bfs(start, goal);
		List<GeographicPoint> gps = firstMap.bfs(start, goal);
		//System.out.println(gps);
		
		//Test: print edges:
		List<GraphNode> nList = new ArrayList<GraphNode>();
		for (GeographicPoint gp: firstMap.nodeMap.keySet()) {
			nList.add(firstMap.nodeMap.get(gp));
		}
		for (GraphNode n: nList) {
			//System.out.println("Edges of " + n +": " + n.getEdges());
			//System.out.println("Neighbours: " + firstMap.returnNeighbors(n));
		}
		
		// Test: add Edge (should throw null pointer exception)
		from = new GeographicPoint(1.6, 1.2);
		firstMap.addEdge(from, to, "Real Road", "Some type", 200);
		//System.out.println(firstMap.toString());
		
		//Test: bfs
		start = new GeographicPoint(8.0, -1.0);
		goal = new GeographicPoint(6.5, 0.0);
		firstMap.bfs(start, goal);
		gps = firstMap.bfs(start, goal);
		//System.out.println(gps);
		
	}
	
}
