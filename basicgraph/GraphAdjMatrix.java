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
 * Representation of edges via an adjacency matrix.
 * 
 * @author UCSD MOOC development team and ER
 *
 */
public class GraphAdjMatrix extends Graph {

	private final int defaultNumVertices = 5;
	private int[][] adjMatrix;
	
	// Constructor creates a new empty graph
	public GraphAdjMatrix () {
		adjMatrix = new int[defaultNumVertices][defaultNumVertices];
	}
	
	// Implements the abstract method of adding a vertex
	public void implementAddVertex() {
		int v = getNumVertices();
		if (v >= adjMatrix.length) {
			int[][] newAdjMatrix = new int[v*2][v*2];
			for (int i = 0; i < adjMatrix.length; i ++) {
				for (int j = 0; j < adjMatrix.length; j ++) {
					newAdjMatrix[i][j] = adjMatrix[i][j];
				}
			}
			adjMatrix = newAdjMatrix;
		}
	}
	
	// Implements the abstract method of adding an edge
	// given the start (v) and end (w) points	
	public void implementAddEdge(int v, int w) {
		adjMatrix[v][w] += 1;
	}
	
	// Implements the abstract method of finding all outgoing 
	// neighbours of a given vertex	
	public List<Integer> getNeighbors(int v) {
		List<Integer> neighbors = new ArrayList<Integer>();
		for (int i = 0; i < getNumVertices(); i ++) {
			for (int j=0; j< adjMatrix[v][i]; j ++) {
				neighbors.add(i);
			}
		}
		return neighbors;
	}
	
	// Implements the abstract method of returning all incoming 
	// neighbours of a vertex
	public List<Integer> getInNeighbors(int v) {
		List<Integer> inNeighbors = new ArrayList<Integer>();
		for (int i = 0; i < getNumVertices(); i ++) {
			for (int j=0; j< adjMatrix[i][v]; j++) {
				inNeighbors.add(i);
			}
		}
		return inNeighbors;
	}
	
	// Implements the abstract method of finding vertices 'two hops' 
	// from a given vertex	
	//Implemented in w/2
	public List<Integer> getDistance2(int v) {
		List<Integer> answer = new ArrayList<Integer>();
		List<Integer> neighbours = getNeighbors(v);
		
		for (Integer n: neighbours) {
			answer.addAll(getNeighbors(n));
		}
		
		return answer;
	}
	
	// Generates a string representation of the adjacency matrix
	public String adjacencyString() {
		int dim = getNumVertices();
		String s = "Adjacency matrix";
		s += " (size " + dim + "x" + dim + " = " + dim* dim + " integers):";
		for (int i = 0; i < dim; i ++) {
			s += "\n\t"+i+": ";
			for (int j = 0; j < dim; j++) {
			s += adjMatrix[i][j] + ", ";
			}
		}
		return s;
	}

}
