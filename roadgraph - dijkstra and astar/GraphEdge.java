package roadgraph;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import geography.GeographicPoint;
/*
 * Creates an edge between two nodes
 * @ Author - ER
 */

public class GraphEdge {

	private GeographicPoint start;
	private GeographicPoint end;
	private String roadName;
	private String roadType;
	private double length;
	
	/* Creates a new GraphEdge
	 * Initialises the edge's start and finish points */
	public GraphEdge(GeographicPoint location1, GeographicPoint location2) {
		start = location1;
		end = location2;
	}
	
	/* Creates a new GraphEdge
	 * Initialises the edge's start, end and type information */
	public GraphEdge (GeographicPoint location1, GeographicPoint location2, String name,
			String type, double size) {
		start = location1;
		end = location2;
		roadName = name;
		roadType = type;
		length = size;
	}
	
	// GETTER METHODS 
	
	/* Returns the road name */
	public String getRoadName() {
		return roadName;
	}
	
	/* Returns the road type */
	public String getRoadType() {
		return roadType;
	}
	
	/* Returns the road length */
	public double getLength() {
		return length;
	}
	
	
	/* Returns start node */
	public GeographicPoint getStart() {
		return start;
	}
	
	/* Returns end node */
	public GeographicPoint getEnd() {
		return end;
	}
	
	/* Returns the string representation of the edge */
	public String toString() {
		return ("Edge " + roadName + "Starts at: " + start + "Ends at: " + end);
	}
		
	/* For debugging */
	public static void main(String[] args) {
	}

}
