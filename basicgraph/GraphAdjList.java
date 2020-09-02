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
 * @author UCSD MOOC development team and ER
 *
 */
public class GraphAdjList extends Graph {


	private Map<Integer,ArrayList<Integer>> adjListsMap;
	
	// Constructor creates a new empty graph
	public GraphAdjList () {
		adjListsMap = new HashMap<Integer,ArrayList<Integer>>();
	}

	// Implements the abstract method of adding a vertex
	public void implementAddVertex() {
		int v = getNumVertices();
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		adjListsMap.put(v,  neighbors);
	}
	
	// Implements the abstract method of adding an edge
	// given the start (v) and end (w) points
	public void implementAddEdge(int v, int w) {
		(adjListsMap.get(v)).add(w);

	}
	
	// Implements the abstract method of finding all outgoing 
	// neighbours of a given vertex		
	public List<Integer> getNeighbors(int v) {
		return new ArrayList<Integer>(adjListsMap.get(v));
	}

	// Implements the abstract method of returning all incoming 
	// neighbours of a vertex
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
	 

	 // Implements the abstract method of finding vertices 'two hops' 
	 // from a given vertex	
	 //Implemented in w/2
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
	
	// Generates a string representation of the adjacency list
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
	
	// For testing
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
