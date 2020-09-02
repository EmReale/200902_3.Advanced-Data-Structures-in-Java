package basicgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** A class that implements a directed graph. 
 * The graph may have self-loops, parallel edges. 
 * Vertices are labeled by integers 0 .. n-1
 * and may also have String labels.
 * The edges of the graph are not labeled.
 * Representation of edges via adjacency lists.
 * 
 * @author UCSD MOOC development team and YOU
 *
 */
public class GraphAdjList extends Graph {


	private Map<Integer,ArrayList<Integer>> adjListsMap;
	
	/** 
	 * Create a new empty Graph
	 */
	public GraphAdjList () {
		adjListsMap = new HashMap<Integer,ArrayList<Integer>>();
	}

	/** 
	 * Implement the abstract method for adding a vertex. 
	 */
	public void implementAddVertex() {
		int v = getNumVertices();
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		adjListsMap.put(v,  neighbors);
	}
	
	/** 
	 * Implement the abstract method for adding an edge.
	 * @param v the index of the start point for the edge.
	 * @param w the index of the end point for the edge.  
	 */
	public void implementAddEdge(int v, int w) {
		(adjListsMap.get(v)).add(w);

	}
	
	/** 
	 * Implement the abstract method for finding all 
	 * out-neighbors of a vertex.
	 * If there are multiple edges between the vertex
	 * and one of its out-neighbors, this neighbor
	 * appears once in the list for each of these edges.
	 * 
	 * @param v the index of vertex.
	 * @return List<Integer> a list of indices of vertices.  
	 */	
	public List<Integer> getNeighbors(int v) {
		return new ArrayList<Integer>(adjListsMap.get(v));
	}

	/** 
	 * Implement the abstract method for finding all 
	 * in-neighbors of a vertex.
	 * If there are multiple edges from another vertex
	 * to this one, the neighbor
	 * appears once in the list for each of these edges.
	 * 
	 * @param v the index of vertex.
	 * @return List<Integer> a list of indices of vertices.  
	 */	
	public List<Integer> getInNeighbors(int v) {
		List<Integer> inNeighbors = new ArrayList<Integer>();
		//iterate through all edges in u's adjacency list
		for (int u : adjListsMap.keySet()) {
			//add u to the inNeighbor list of v whenever an edge
			//with startpoint u has endpoint v.
			for (int w : adjListsMap.get(u)) {
				if (v == w) {
					inNeighbors.add(u);
				}
			}
		}
		return inNeighbors;
	}
	 

	/** 
	 * Implement the abstract method for finding all 
	 * vertices reachable by two hops from v.
	 * 
	 * @param v the index of vertex.
	 * @return List<Integer> a list of indices of vertices.  
	 */		
	 //Implemented in week 2
	 public List<Integer> getDistance2(int v) {
		 List<Integer> answer = new ArrayList<Integer>();
		 //Get neighbours from first hop
		 List<Integer> next = getNeighbors(v);
		 //Get the neighbours of the initial neighbours
		 for (Integer n: next) {
			 answer.addAll(getNeighbors(n));
			 // Alternatively loop over all neighbours and add them
		 }
		 return answer;
	}
	
	/**
	 * Generate string representation of adjacency list
	 * @return the String
	 */
	public String adjacencyString() {
		String s = "Adjacency list";
		s += " (size " + getNumVertices() + "+" + getNumEdges() + " integers):";

		for (int v : adjListsMap.keySet()) {
			s += "\n\t"+v+": ";
			for (int w : adjListsMap.get(v)) {
				s += w+", ";
			}
		}
		return s;
	}

	public static void main (String[] args) {
		GraphAdjList adj = new GraphAdjList();
		adj.addVertex();
		adj.addVertex();
		adj.addVertex();
		adj.addVertex();
		adj.addEdge(0,2);
		adj.addEdge(0,3);
		adj.addEdge(0,1);
		adj.addEdge(0,2);
		adj.addEdge(2,3);
		adj.addEdge(3,1);
		List<Integer> degs = adj.degreeSequence();
		System.out.println(degs);
	}


}
