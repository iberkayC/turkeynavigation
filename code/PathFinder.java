import java.util.ArrayList;
import java.util.Arrays;

/**
 * The PathFinder class finds the optimal path between two cities.
 * It uses Dijkstra's algorithm to find the shortest path between two cities.
 */
public class PathFinder {
    private final MapManager mapManager;
    private final MapDrawer mapDrawer;

    /**
     * Constructor for PathFinder.
     *
     * @param mapManager The map manager.
     * @param mapDrawer  The map drawer.
     */
    public PathFinder(MapManager mapManager, MapDrawer mapDrawer) {
        this.mapManager = mapManager;
        this.mapDrawer = mapDrawer;
    }


    /**
     * Find the optimal path between two cities using Dijkstra's algorithm.
     *
     * @param startCity  The city from where the path starts.
     * @param targetCity The destination city.
     */
    public void findOptimalPath(City startCity, City targetCity) {
        ArrayList<City> cities = mapManager.getCities();
        int numberOfCities = cities.size();
        double[] distance = new double[numberOfCities];
        City[] previous = new City[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];

        // Initialize the distance array with infinite distance for all cities except the start city.
        initializeDistanceArray(distance, startCity);

        // Find the optimal path using Dijkstra's algorithm.
        findPathLogic(targetCity, distance, previous, visited);

        // Process the results of the pathfinding and display the optimal path if found.
        processPathResults(distance, previous, targetCity);
    }

    /**
     * Initialize the distance array with infinite distance for all cities except the start city.
     *
     * @param distance  An array to store the distance from the start city to each city.
     * @param startCity The city from where the pathfinding starts.
     */
    private void initializeDistanceArray(double[] distance, City startCity) {
        Arrays.fill(distance, Double.MAX_VALUE);
        distance[startCity.getIndex()] = 0;
    }

    /**
     * The core logic for finding the path using Dijkstra's algorithm.
     *
     * @param targetCity The destination city.
     * @param distance   An array to store the distance from the start city to each city.
     * @param previous   An array to store the previous city in the path.
     * @param visited    An array to store whether a city has been visited.
     */
    private void findPathLogic(City targetCity, double[] distance, City[] previous, boolean[] visited) {
        int numberOfCities = mapManager.getCities().size();
        for(int i = 0; i < numberOfCities; i++) {
            City nearest = findNearestCity(distance, visited);
            if(nearest == null || nearest == targetCity) {
                break;
            }

            visited[nearest.getIndex()] = true;
            updateDistances(nearest, distance, previous);
        }
    }

    /**
     * Find the nearest city that has not been visited yet.
     *
     * @param distance An array to store the distance from the start city to each city.
     * @param visited  An array to store whether a city has been visited.
     * @return The nearest city that has not been visited yet.
     */
    private City findNearestCity(double[] distance, boolean[] visited) {
        double minDistance = Double.MAX_VALUE;
        City minDistanceCity = null;

        for(City city : mapManager.getCities()) {
            int index = city.getIndex();
            if(!visited[index] && distance[index] < minDistance) {
                minDistance = distance[index];
                minDistanceCity = city;
            }
        }

        return minDistanceCity;
    }

    /**
     * Update the shortest distance to each neighbor of a given city.
     *
     * @param city      The city whose neighbors need to be updated.
     * @param distance  An array to store the distance from the start city to each city.
     * @param previous  An array to store the previous city in the path.
     */
    private void updateDistances(City city, double[] distance, City[] previous) {
        for (City neighbor : city.getConnections()) {
            double alt = distance[city.getIndex()] + calculateDistance(city, neighbor);
            if (alt < distance[neighbor.getIndex()]) {
                distance[neighbor.getIndex()] = alt;
                previous[neighbor.getIndex()] = city;
            }
        }
    }

    /**
     * Process the results of the pathfinding and print the optimal path if found.
     *
     * @param distance   An array to store the distance from the start city to each city.
     * @param previous   An array to store the previous city in the path.
     * @param targetCity The destination city.
     */
    private void processPathResults(double[] distance, City[] previous, City targetCity) {
        if(distance[targetCity.getIndex()] == Double.MAX_VALUE) {
            System.out.println("No path could be found.");
            return;
        }

        ArrayList<City> pathToTarget = buildPath(previous, targetCity);
        printDistanceAndPath(pathToTarget);
        mapDrawer.drawOptimalPath(pathToTarget);
    }

    /**
     * Calculate the distance between two cities.
     *
     * @param city1 The first city.
     * @param city2 The second city.
     * @return The distance between the two cities.
     */
    private double calculateDistance(City city1, City city2) {
        return Math.sqrt(Math.pow(city1.getX() - city2.getX(), 2) + Math.pow(city1.getY() - city2.getY(), 2));
    }

    /**
     * Build the path from the start city to the target city using the previous array.
     *
     * @param previous   An array to store the previous city in the path.
     * @param targetCity The destination city.
     * @return The path from the start city to the target city.
     */
    private ArrayList<City> buildPath(City[] previous, City targetCity) {
        ArrayList<City> path = new ArrayList<>();
        for (City at = targetCity; at != null; at = previous[at.getIndex()]) {
            path.add(at);
        }
        reversePath(path);
        return path;
    }

    /**
     * Reverse the path so that it is from the start city to the target city.
     *
     * @param path The path to reverse.
     */
    private void reversePath(ArrayList<City> path) {
        for(int i = 0; i < path.size() / 2; i++) {
            City temp = path.get(i);
            path.set(i, path.get(path.size() - i - 1));
            path.set(path.size() - i - 1, temp);
        }
    }

    /**
     * Print the total distance of the path and the path itself.
     *
     * @param pathToTarget The path from the start city to the target city.
     */
    private void printDistanceAndPath(ArrayList<City> pathToTarget) {
        double totalDistance = calculateTotalDistance(pathToTarget);
        System.out.printf("Total Distance: %.2f. Path: ", totalDistance);
        System.out.print(pathToTarget.get(0).getCityName());
        for(int i = 1; i < pathToTarget.size(); i++) {
            System.out.print(" -> " + pathToTarget.get(i).getCityName());
        }
    }

    /**
     * Calculate the total distance of the path.
     *
     * @param pathToTarget The path from the start city to the target city.
     * @return The total distance of the path.
     */
    private double calculateTotalDistance(ArrayList<City> pathToTarget) {
        double totalDistance = 0;
        for(int i = 0; i < pathToTarget.size() - 1; i++) {
            totalDistance += calculateDistance(pathToTarget.get(i), pathToTarget.get(i+1));
        }
        return totalDistance;
    }

}
