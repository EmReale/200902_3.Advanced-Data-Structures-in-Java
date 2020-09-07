/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
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
import java.util.PriorityQueue;

import geography.GeographicPoint;
import util.GraphLoader;
import roadgraph.GraphNode;
import roadgraph.GraphEdge;

/**
 * @author UCSD MOOC development team and ER
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 */
//TODO: Add your member variables here in WEEK 3
public class MapGraph {
	
	private int numVertices;
	private int numEdges;
	
	// Create a list of edges for easy access
	protected Set<GraphEdge> edges;
	protected Map<GeographicPoint, GraphNode> nodeMap;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	// TODO: Implement in this constructor in WEEK 3
	public MapGraph(){
		nodeMap = new HashMap<GeographicPoint, GraphNode>();
		edges = new HashSet<GraphEdge>();
		numVertices = 0;
		numEdges = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	//TODO: Implement this method in WEEK 3
	public int getNumVertices(){
		return nodeMap.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	//TODO: Implement this method in WEEK 3
	public Set<GeographicPoint> getVertices(){
		Set<GeographicPoint> nodes = new HashSet<GeographicPoint>();
		for (GeographicPoint g: nodeMap.keySet()) {
			nodes.add(g);
		}
		return nodes;
	}
	
	
	public Set<GraphEdge> getEdges() {
		return new HashSet<GraphEdge>(edges);
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	//TODO: Implement this method in WEEK 3
	public int getNumEdges(){
		return edges.size();
	}

	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	// TODO: Implement this method in WEEK 3
	public boolean addVertex(GeographicPoint location){
		GraphNode newNode = new GraphNode(location);
		//If the node isn't in the graph already add it and return true
		if (!nodeMap.containsKey(location)) {
			nodeMap.put(location,newNode);
			return true;
		}
		//If it was already in the graph and couldn't be added, return false
		else {
			return false;
		}
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	//TODO: Implement this method in WEEK 3
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		
		// Throw an exception if either of the vertices don't exist
		if (!nodeMap.containsKey(from) || !nodeMap.containsKey(to)) {
			throw new NullPointerException("There is no node at this location");	
		}
		
		if (from==null || to==null || roadName == null || roadType==null || length<0) {
			throw new NullPointerException("Null arguments are not allowed");
		}
		
		GraphNode currNode = nodeMap.get(from);
		GraphEdge edge = new GraphEdge(from,to,roadName,roadType,length);
		currNode.addEdge(edge);
		edges.add(edge);
	}
		
	
	// New helper method - returns list of currNode neighbours
	protected List<GraphNode> returnNeighbors(GraphNode currNode) {
		// Finds the neighbours by returning the end point of associated edges
		// And adds them as neighbours
		List<GraphEdge> ge = currNode.getEdges();
		for (GraphEdge edge: ge) {
			currNode.addNeighbor(nodeMap.get(edge.getEnd()));
		}
		List<GraphNode> neighbors = currNode.getNeighbors();
		return neighbors;
	}
	
	
	// New helper method to build the path from the goal to the start
	private List<GeographicPoint> buildPath (GeographicPoint start, GeographicPoint goal, Map<GraphNode,GraphNode> parentMap){
		// Finds the goal node within the parentMap and adds it to the path
		List<GeographicPoint> path = new LinkedList<>();
		if (!parentMap.isEmpty()) {
			GraphNode currNode = nodeMap.get(goal);
			path.add(0,(currNode.getLocation()));
			// Retraces steps until the start is reached
			while (!currNode.getLocation().equals(start)) {
				GraphNode prev = parentMap.get(currNode);
				//System.out.println("Prev: " + prev);
				// Adds it to the start of the path
				path.add(0, (prev.getLocation()));
				currNode = prev;
			}
		}
		return path;
	}
	
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	// Dummy variable for calling the search algorithms
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		Consumer<GeographicPoint> temp = (x) -> {};
		return bfs(start, goal, temp);
	}
	
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	// TODO: Implement this method in WEEK 3
	// Hook for visualization.  See writeup.
	//nodeSearched.accept(next.getLocation());
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched){
		// Throw an exception if one of the points doesn't exist
		if (!nodeMap.containsKey(start)) {
			throw new NullPointerException("Location " + start + " does not exist");
		}
		if (!nodeMap.containsKey(goal)) {
			throw new NullPointerException("Location " + goal + " does not exist");
		}
		
		// Initialise: the queue of GraphNodes waiting to be visited
		// Linked list of the points its visited
		// ParentMap of currNode and nextNext for finding the path
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		Set<GraphNode> visited = new HashSet<GraphNode>();
		Map <GraphNode,GraphNode> parentMap = new HashMap <GraphNode,GraphNode>();
		
		// Find the node and set it to currNode
		GraphNode currNode = nodeMap.get(start);
		// Add it to the queue and to visited
		queue.add(currNode);
		visited.add(currNode);
		
		// While the queue has nodes left to inspect
		while (!queue.isEmpty()) {
			// Take the first node from the queue as currNode
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
					nodeSearched.accept(n.getLocation());
					parentMap.put(n,currNode);
				}
			}
		}
		
		// Build and return the path and return if it exists
		List<GeographicPoint> path = buildPath(start,goal,parentMap);
		if (path.size() > 0) {
			return path;	
		}
		return null;
	}

	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	// TODO: Implement this method in WEEK 4
	// Hook for visualization.  See writeup.
	//nodeSearched.accept(next.getLocation());
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched){
		return aStarSearch(start, goal, nodeSearched, true);
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp, false);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	/* Very similar to Djikstra, only it also takes into consideration the distance 
	 * from the goal	 
	 * */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched, boolean dijkstra){
		// Throw an exception if one of the points doesn't exist
		if (!nodeMap.containsKey(start)) {
			throw new NullPointerException("Location " + start + " does not exist");
		}
		if (!nodeMap.containsKey(goal)) {
			throw new NullPointerException("Location " + goal + " does not exist");
		}
		
		//Initialise priority queue, visited and parentMap
		PriorityQueue<GraphNode> pq = new PriorityQueue<GraphNode>();
		Set<GraphNode> visited = new HashSet<GraphNode>();
		Map<GraphNode,GraphNode> parentMap= new HashMap<GraphNode,GraphNode>();
		
		// Set start as currNode and add to queue
		// Initialise distance from start to zero
		GraphNode currNode = nodeMap.get(start);
		currNode.setDistanceToStart(0);
		pq.add(currNode);
		
		while (!pq.isEmpty()) {
			// Set currNode to the first element in the priority queue
			currNode = pq.remove();
			// Add currNode to visited and return parentMap if goal is found
			if (!visited.contains(currNode)) {
				visited.add(currNode);
				if (currNode.getLocation().equals(goal)) {
					break;
				}
				// Find the neighbours of currNode
				List<GraphNode> neighbors = returnNeighbors(currNode);
				for (GraphNode n: neighbors) {
					// For each unvisited neighbour
					if (!visited.contains(n)) {
						// Resets distance if method is called multiple times
						n.setDistanceToStart(Double.POSITIVE_INFINITY);
						// Find the distance of n from the start
						double edgeLength = currNode.getEdgeLength(n,edges);
						double distanceTravelled = currNode.getDistanceToStart() + edgeLength;
						// Predicted distance between neighbour and goal location
						double predictedDistance = 0;
						// If not being used for the dijkstra algorithm calculate the predicted
						// distance to the end node
						if (!dijkstra) {
							predictedDistance = n.getLocation().distance(goal);
						}
						
						// Find and explore the shortest path, setting the distance/ predicted distance
						// and adding it to the priority queue and parentMap
						// Add the hook for visualisation
						if (n.getDistanceToStart() == Double.POSITIVE_INFINITY || 
							distanceTravelled < n.getDistanceToStart()) {
							n.setDistanceToStart(distanceTravelled);
							n.setPredictedDistance(predictedDistance);
							parentMap.put(n,currNode);
							pq.add(n);
							nodeSearched.accept(n.getLocation());
						}
					}
				}
			}
		}
		// Build and return the path and return if it exists
		List<GeographicPoint> path = buildPath(start,goal,parentMap);
		if (path.size() > 0) {
			return path;	
		}
		return null;
	}

	
	public String toString() {
		String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";
		return s;
	}

	
	public static void main(String[] args){
		
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		//System.out.println(firstMap);
		
		// Test createEdgeMap
		/*System.out.println(firstMap.edges);
		firstMap.buildEdgeMap();
		for (GraphEdge edge: firstMap.edgeMap.keySet()) {
			System.out.println(firstMap.edgeMap.get(edge));
		}*/
		
		GeographicPoint start = new GeographicPoint(7.0,3.0);
		GeographicPoint goal = new GeographicPoint(6.5,0.0);
		
		System.out.println(firstMap.aStarSearch(start, goal));
		
		//System.out.println("Distance Travelled: " + distanceTravelled);
		
		/*for (GraphEdge edge: firstMap.edges){
			System.out.println(edge.getRoadName() + " " + edge.getStart() + " " + edge.getLength());
		}*/
		
		/*//Test: add node
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
		
		*/
		
		// You can use this method for testing.  
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		/*
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}

}
