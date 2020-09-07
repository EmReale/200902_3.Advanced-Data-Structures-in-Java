package roadgraph;

import util.GraphLoader;

import java.util.List;

import geography.GeographicPoint;
import roadgraph.greedy;

public class greedyBenchmark {
	
	static int trials = 10;
	
	public static void main(String [] args) {
		
		int index = 0;
		long startTime = 0;
		long endTimeA = 0;
		long endTime = 0;
		long timeA = 0;
		long time = 0;
		float totalTime = 0;
		float totalTimeA =0;
		
		greedy firstMap = new greedy();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		GeographicPoint start = new GeographicPoint(7.0,3.0);
		List<GeographicPoint> path = firstMap.greedySearch(start);
		
		System.out.print ("A* Search  Using Distance between nodes \n");
		
		for (int i=0; i<=trials; i++) {
			
			startTime = System.nanoTime();
			firstMap.getTotalLengthAStar(path);
			endTime = System.nanoTime();
			timeA = (endTime - startTime);
			totalTimeA += timeA;
			
			startTime = System.nanoTime();
			firstMap.getRouteLength(path);
			endTime = System.nanoTime();
			time = (endTime - startTime);
			totalTime += time;
			
			System.out.println(timeA +"  " + time);
		}
		
		if (totalTime<totalTimeA) {
			System.out.println("Getting length by adding distance between nodes is on average " + (totalTimeA/totalTime) + " faster");
		}
		else {
			System.out.println("Getting length by A* only is on average " + (totalTime/totalTimeA) + " faster");
		}
	}
}
