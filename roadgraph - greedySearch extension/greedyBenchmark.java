package roadgraph;

import util.GraphLoader;

import java.util.List;

import geography.GeographicPoint;
import roadgraph.greedy;

public class greedyBenchmark {
	
	// No. of times the method will be called
	static int trials = 10;
	
	/* Method to find which method is more efficient for finding the length of the path;
	 * Using A* only or by calculating the length of edges between nodes where possible
	 */
	public static void main(String [] args) {
		
		int index = 0;
		long startTime = 0;
		long endTimeA = 0;
		long endTime = 0;
		long timeA = 0;
		long time = 0;
		float totalTime = 0;
		float totalTimeA =0;
		
		// Creates a new map and populates the path
		greedy firstMap = new greedy();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		GeographicPoint start = new GeographicPoint(7.0,3.0);
		List<GeographicPoint> path = firstMap.greedySearch(start);
		
		// Column headings
		System.out.print ("A* Search  Using Distance between nodes \n");
		
		for (int i=0; i<=trials; i++) {
			// Finds how long it takes to calculate the path length uing A* only
			startTime = System.nanoTime();
			firstMap.getTotalLengthAStar(path);
			endTime = System.nanoTime();
			timeA = (endTime - startTime);
			totalTimeA += timeA;
			
			// Finds how long it takes to execute the path length using A* 
			// as little as possible
			startTime = System.nanoTime();
			firstMap.getRouteLength(path);
			endTime = System.nanoTime();
			time = (endTime - startTime);
			totalTime += time;
			
			// Prints the time for each iteration
			System.out.println(timeA +"  " + time);
		}
		// Returns which method is generally faster and by how much
		if (totalTime<totalTimeA) {
			System.out.println("Getting length by adding distance between nodes is on average " + (totalTimeA/totalTime) + " faster");
		}
		else {
			System.out.println("Getting length by A* only is on average " + (totalTime/totalTimeA) + " faster");
		}
	}
}
