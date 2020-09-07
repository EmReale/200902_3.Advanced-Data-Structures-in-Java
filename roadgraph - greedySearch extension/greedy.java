package roadgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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


/*
 * Attempts to find the most efficient route between every 
 * vertex in the graph using the Greedy Algorithm
 * @Author - ER
 */

public class greedy extends MapGraph{
	
	/* Constructor creates new greedy search
	 */
	public greedy() {
		
	}
	
	/* Greedy search to finds the shortest route by following the next 
	 * shortest path. This also takes into account nodes that have 
	 * only one neighbour to avoid lengthy backtracking 
	 */
	public List<GeographicPoint> greedySearch(GeographicPoint start) {
		
		// Copy nodeMap as all nodes will be removed from the list as they are visited
		Map<GeographicPoint,GraphNode> toVisit = new HashMap<GeographicPoint,GraphNode>(nodeMap); 
		List<GeographicPoint> visited = new ArrayList<GeographicPoint>();
		GraphNode currNode = toVisit.remove(start);
		
		// While there are still nodes to visit
		while (!toVisit.isEmpty()) {
			double minDistance = Double.POSITIVE_INFINITY;
			GraphNode next = null;
			// Check distance from currNode to each node yet to be visited
			for (GeographicPoint point: toVisit.keySet()) {
				List<GeographicPoint> path = aStarSearch(currNode.getLocation(),point);
				double length = getRouteLength(path);
				
				// Add neighbour as next if it has no other neighbours
				List<GraphNode> neighbors = returnNeighbors(currNode);
				for (GraphNode n : neighbors) {
					if (returnNeighbors(n).size()<2) {
						next = n;
						break;
					}
					// Otherwise the next node will be the closest one
					else if (length<minDistance) {
						next = toVisit.get(point);
						minDistance = length;
					}
				}
			}
			// Update currNode and add to visited
			currNode = toVisit.remove(next.getLocation());
			visited.add(currNode.getLocation());
		}
		// Adds the start to the beginning and end to complete the journey
		visited.add(0,start);
		visited.add(visited.size(),start);
		return visited;
	}
	
	
	/* Returns route between start and other nodes in greedy method, as well 
	 * as length of the total path by using A* search as little as possible
	 * This was faster on average than using A* search alone - see benchMark class
	 */
	public double getRouteLength(List<GeographicPoint> path) {
		
		double totalLength = 0;
		
		for (int j=0; j<path.size()-1; j++) {
			// Finds the start and end nodes
			GraphNode start = nodeMap.get(path.get(j));
			GraphNode end = nodeMap.get(path.get(j+1));
			
			// Find the length of edge between nodes
			totalLength += start.getEdgeLength(end, edges);
			// Finds the length using A* only if there is no edge between nodes
			if (start.getEdgeLength(end, edges) == 0) {
				List<GeographicPoint> nodePath = aStarSearch(start.getLocation(),end.getLocation());
				totalLength += getRouteLength(nodePath);
			}
		}
		return totalLength;
	}
	
	
	/* Returns total length of the path - calculates distances using 
	 * aStarSearch before calculating the route length 
	 */
	public double getTotalLengthAStar(List<GeographicPoint> visited) {
		
		double totalLength = 0;
		for (int j=0; j<visited.size()-1; j++) {
			// Find the start and end nodes
			GeographicPoint start = visited.get(j);
			GeographicPoint end = visited.get(j+1);
			// Finds the route between each pair of nodes using A* search
			List<GeographicPoint> path = aStarSearch(start,end);
			totalLength += getRouteLength(path);
		}
		return totalLength;
	}
	
	
	/* For testing */
	public static void main(String[] args) {
		System.out.print("Making a new map...");
		greedy firstMap = new greedy();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		
		GeographicPoint gp = new GeographicPoint(7.0,3.0);
		List<GeographicPoint> visited = firstMap.greedySearch(gp);
		//System.out.println(visited);
		
		//double totalLength = firstMap.getRouteLength(visited);
		//System.out.println(totalLength);
		
		//double totalLength2 = firstMap.getTotalLengthAStar(visited);
		//System.out.println(totalLength2);
		
		//GeographicPoint start = new GeographicPoint(7.0,3.0);
		//GeographicPoint goal = new GeographicPoint(4.0,1.0);
				
		//System.out.println("\n" + firstMap.isEdge(start,goal));

		//double routeLength = firstMap.getRouteLength(visited);
		//System.out.println(routeLength);
	}

}
