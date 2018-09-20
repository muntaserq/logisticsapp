package Network;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import Exceptions.InvalidDataException;
import Facility.Facility;
import Facility.FacilityLink;

/**
 * @author muntaserqutub
 *
 */

public class NetworkImpl implements Network {

	private Double drivingHoursPerDay;
	private Double averageMilesPerHour;
	private HashMap<String, Vertex> facilityMap;
	private ArrayList<Facility> facilities;

	public NetworkImpl(ArrayList<Facility> facilities, Double drivingHoursPerDay, Double averageMilesPerHour)
			throws InvalidDataException {
		setFacilitiesList(facilities);
		setDrivingHoursPerDay(drivingHoursPerDay);
		setAverageMilesPerHour(averageMilesPerHour);
	}

	@Override
	public ArrayList<FacilityLink> calculateShortestPath(String facilitiyStart, String facilityEnd, boolean print)
			throws InvalidDataException {
		buildFacilityNetwork(facilities);
		ArrayList<FacilityLink> shortestPath = new ArrayList<FacilityLink>();

		Vertex source = getVertexByLocation(facilitiyStart);
		Vertex target = getVertexByLocation(facilityEnd);

		computePaths(source);
		List<Vertex> shortestPathVertices = getShortestPathTo(target);

		for (Vertex v : shortestPathVertices) {
			shortestPath
					.add(new FacilityLink(v.name, v.minDistance, getDrivingHoursPerDay(), getAverageMilesPerHour()));
		}

		if (print)
		{
			printShortestPath(facilitiyStart, facilityEnd, shortestPath);
		}
		
		return shortestPath;
	}

	private void printShortestPath(String facilitiyStart, String facilityEnd, ArrayList<FacilityLink> shortestPath) {
		System.out.println(facilitiyStart + " to " + facilityEnd + ":");

		String route = "";
		for (FacilityLink fl : shortestPath) {
			route = route + fl.getLocation() + " --> ";
		}

		if (!route.isEmpty()) {
			route = route.trim().substring(0, route.length() - 5);
		}

		Double totalDistance = shortestPath.get(shortestPath.size() - 1).getDistanceInMiles();

		route = route + " = " + totalDistance.toString() + " mi";

		System.out.println(" - " + route);

		Double daysToTravel = totalDistance / (getDrivingHoursPerDay() * getAverageMilesPerHour());
		Formatter travelTimeFormat = new Formatter();
		travelTimeFormat.format("%.2f", daysToTravel);

		System.out.println(" - " + totalDistance.toString() + " mi / (" + getDrivingHoursPerDay() + " hours per day * "
				+ getAverageMilesPerHour() + " mph ) = " + travelTimeFormat.toString() + " days");
		System.out.println();
		travelTimeFormat.close();
	}

	private static void computePaths(Vertex source) {
		source.minDistance = 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();

			// Visit each edge exiting u
			for (Edge e : u.adjacencies) {
				Vertex v = e.target;
				Double weight = e.weight;
				Double distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(v);
					v.minDistance = distanceThroughU;
					v.previous = u;
					vertexQueue.add(v);
				}
			}
		}
	}

	private static List<Vertex> getShortestPathTo(Vertex target) {
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex);

		Collections.reverse(path);
		return path;
	}

	private void buildFacilityNetwork(ArrayList<Facility> facilities) throws InvalidDataException {
		HashMap<String, Vertex> verticiesMap = new HashMap<String, Vertex>();
		ArrayList<Vertex> verticiesList = new ArrayList<Vertex>();

		for (Facility f : facilities) {
			String facilityLocation = f.getLocation();
			Vertex facilityVertex = new Vertex(facilityLocation);
			verticiesMap.put(facilityLocation, facilityVertex);
			verticiesList.add(facilityVertex);
		}

		for (int i = 0; i < facilities.size(); i++) {
			ArrayList<FacilityLink> linkedFacilitiesDTOList = facilities.get(i).getLinkedFacilitiesAsList();

			Edge[] edges = new Edge[linkedFacilitiesDTOList.size()];

			for (int j = 0; j < edges.length; j++) {
				String nameOfLinkedFacilitiy = linkedFacilitiesDTOList.get(j).getLocation();
				Double distanceToFacility = linkedFacilitiesDTOList.get(j).getDistanceInMiles();

				Vertex facilityVertex = verticiesMap.get(nameOfLinkedFacilitiy);

				edges[j] = new Edge(facilityVertex, distanceToFacility);
			}

			verticiesList.get(i).adjacencies = edges;
		}
		setFacilityMap(verticiesMap);
	}

	private Vertex getVertexByLocation(String location) throws InvalidDataException {
		if (location == null || location.isEmpty()) {
			throw new InvalidDataException("The data entered for the facility location is invalid.");
		}
		Vertex v = this.facilityMap.get(location);

		return v;
	}

	private void setFacilitiesList(ArrayList<Facility> facilities) throws InvalidDataException {
		if (facilities == null || facilities.isEmpty()) {
			throw new InvalidDataException("The data entered for the facilities is invalid.");
		}
		this.facilities = facilities;
	}

	private void setDrivingHoursPerDay(Double drivingHoursPerDay) throws InvalidDataException {
		if (drivingHoursPerDay == null || drivingHoursPerDay <= 0.0) {
			throw new InvalidDataException("The data entered for the driving hours per day is invalid.");
		}
		this.drivingHoursPerDay = drivingHoursPerDay;
	}

	private void setAverageMilesPerHour(Double averageMilesPerHour) throws InvalidDataException {
		if (averageMilesPerHour == null || averageMilesPerHour <= 0.0) {
			throw new InvalidDataException("The data entered for the average miles per hour is invalid.");
		}
		this.averageMilesPerHour = averageMilesPerHour;
	}

	private void setFacilityMap(HashMap<String, Vertex> facilityMap) {
		this.facilityMap = facilityMap;
	}

	private Double getDrivingHoursPerDay() {
		return drivingHoursPerDay;
	}

	private Double getAverageMilesPerHour() {
		return averageMilesPerHour;
	}

}

/*
 * 
 * Source of below code taken from
 * http://en.literateprograms.org/index.php?title=Special%3aDownloadCode/
 * Dijkstra%27s_algorithm_%28Java%29&oldid=15444
 * 
 */
class Vertex implements Comparable<Vertex> {
	public final String name;
	public Edge[] adjacencies;
	public Double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;

	public Vertex(String argName) {
		name = argName;
	}

	public String toString() {
		return name;
	}

	public int compareTo(Vertex other) {
		return Double.compare(minDistance, other.minDistance);
	}
}

class Edge {
	public final Vertex target;
	public final Double weight;

	public Edge(Vertex argTarget, Double argWeight) {
		target = argTarget;
		weight = argWeight;
	}
}
