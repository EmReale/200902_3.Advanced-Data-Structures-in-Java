package basicgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import util.GraphLoader;

/** An abstract class that implements a directed graph. 
 * Vertices are labeled by integers 0 .. n-1
 * and may also have String labels.
 * The edges of the graph are not labeled.
 * Representation of edges is left abstract.
 * 
 * @author UCSD MOOC development team and ER
 */

// Abstract class extended by GraphMatrix and GraphList
public abstract class Graph {

	private int numVertices;
	private int numEdges;
	//optional association of String labels to vertices 
	private Map<Integer,String> vertexLabels;
	
	// Constructor creates a new empty graph
	public Graph() {
		numVertices = 0;
		numEdges = 0;
		vertexLabels = null;
	}

	
	// Returns the number of vertices in the graph
	public int getNumVertices() {
		return numVertices;
	}
	
	
	// Return the number of edges
	public int getNumEdges() {
		return numEdges;
	}
	
	// Adds a new vertex to the graph where its index is the next available integer
	// Adding a vertex increases the count by 1
	public int addVertex() {
		implementAddVertex();
		numVertices ++;
		return (numVertices-1);
	}
	
	// Abstract method implementing adding a new
	// vertex to the representation of the graph.
	public abstract void implementAddVertex();
	
	// Adds a new edge to the graph between given vertices
	public void addEdge(int v , int w) {
		numEdges ++;
		if (v < numVertices && w < numVertices) {
			implementAddEdge(v , w);			
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	// Abstract method implementing adding a new
	// edge to the representation of the graph.
	public abstract void implementAddEdge(int v, int w);
	
	// Abstract method that returns outgoing neightbours of a given vertex
	public abstract List<Integer> getNeighbors(int v); 
	
	// Abstract method that returns incoming neightbours of a given vertex
	public abstract List<Integer> getInNeighbors(int v);

	
	// The degree sequence of a graph is a sorted (organized in numerical order 
	// from largest to smallest, possibly with repetitions) list of the degrees 
	// of the vertices in the graph.
	// Implemented in w/2
	public List<Integer> degreeSequence() {
		List<Integer> sequence = new ArrayList<Integer>();
		int count = 0;
		int next = 0;
		
		//Look at the num of vertices
		for (int i=0; i<numVertices; i++) {
			//Get the no. of edges out
			List<Integer> outList = getNeighbors(i);
			//Get the no. of edges in
			List<Integer> inList = getInNeighbors(i);
			//Add them up
			count = outList.size() + inList.size();
			//If count > largest, add to to the front of the list 
			sequence.add(count);
		}
		// Sort the list in reverse order
		Collections.sort(sequence, Collections.reverseOrder());
		return sequence;
	}
	
	// Returns vertices that are two away from the given vertex
	public abstract List<Integer> getDistance2(int v); 

	// Returns a string representation of the graph
	public String toString() {
		String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";
		s += "Degree sequence: " + degreeSequence() + ".\n";
		if (numVertices <= 20) s += adjacencyString();
		return s;
	}

	// Generates a string representation of the adjacency List
	public abstract String adjacencyString();

	
	// The next methods implement labeled vertices.
	// Basic graphs may or may not have labeled vertices.
	
	// Create a new map of vertex indices to string labels
	public void initializeLabels() {
		vertexLabels = new HashMap<Integer,String>();
	}	
	
	// Returns true if the graph contains a vertex with the given index
	public boolean hasVertex(int v){
		return v < getNumVertices();
	}
	
	// Returns true if the graph contains a vertex with the given string label
	public boolean hasVertex(String s){
		return vertexLabels.containsValue(s);
	}
	
	// Adds a label to an unlablled vertex in the graph
	public void addLabel(int v, String s) {
		if (v < getNumVertices() && !vertexLabels.containsKey(v)) 
		{
			vertexLabels.put(v, s);
		}
		else {
			System.out.println("ERROR: tried to label a vertex that is out of range or already labeled");
		}
	}
	
	// Returns the label of a given vertex
	public String getLabel(int v) {
		if (vertexLabels.containsKey(v)) {
			return vertexLabels.get(v);
		}
		else return null;
	}

	// Returns the index of a vertex with a given label
	public int getIndex(String s) {
		for (Map.Entry<Integer,String> entry : vertexLabels.entrySet()) {
			if (entry.getValue().equals(s))
				return entry.getKey();
		}
		System.out.println("ERROR: No vertex with this label");
		return -1;
	}
	

	
	/** Main method for testing */
	public static void main (String[] args) {
		GraphLoader.createIntersectionsFile("data/maps/ucsd.map", "data/intersections/ucsd.intersections");
		

		// For testing of Part 1 functionality
		System.out.println("Loading graphs based on real data...");
		System.out.println("Goal: use degree sequence to analyse graphs.");
		
		System.out.println("****");
		System.out.println("Roads / intersections:");
		GraphAdjList graphFromFile = new GraphAdjList();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", graphFromFile);
		System.out.println(graphFromFile);
		
		System.out.println("Observe all degrees are <= 12.");
		System.out.println("****");

		System.out.println("\n****");
		
		// You can test with real road data here.  Use the data files in data/maps
		
		System.out.println("Flight data:");
		GraphAdjList airportGraph = new GraphAdjList();
		GraphLoader.loadRoutes("data/airports/routesUA.dat", airportGraph);
		System.out.println(airportGraph);
		System.out.println("Observe most degrees are small (1-30), eight are over 100.");
		System.out.println("****");
		
		//For testing Part 2 functionality
		System.out.println("Testing distance-two methods on sample graphs...");
		System.out.println("Goal: implement method using two approaches.");
	}
}
