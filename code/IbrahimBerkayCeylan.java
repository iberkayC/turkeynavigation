import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * For CMPE160 Object-Oriented Programming course in Bogazici University.
 * Program to draw a map of cities and roads and find the optimal path between two cities.
 * The program reads city coordinates and connections from files and uses
 * Dijkstra's Algorithm to find the optimal path between two cities.
 * @author Ibrahim Berkay Ceylan, Student ID: 2023400327
 * @since Date: 01.04.2024
 */
public class Main {
    // constants for filenames
    private static final String CITY_COORDINATES_FILE = "city_coordinates.txt";
    private static final String CITY_CONNECTIONS_FILE = "city_connections.txt";

    /**
     * The main method of the application.
     *
     * @param args Not used in this application.
     */
    public static void main(String[] args) {
        try {
            // Initialize components
            MapManager mapManager = new MapManager();
            MapDrawer mapDrawer = new MapDrawer();
            PathFinder pathFinder = new PathFinder(mapManager, mapDrawer);

            // Read data from files
            mapManager.readCitiesFromFile(CITY_COORDINATES_FILE);
            mapManager.readConnectionsFromFile(CITY_CONNECTIONS_FILE);

            // Draw map, cities, and roads
            mapDrawer.drawMap();
            mapDrawer.drawCities(mapManager.getCities());
            mapDrawer.drawRoads(mapManager.getCities());
            StdDraw.show();

            // User input for start and target cities to find the optimal path
            Scanner scanner = new Scanner(System.in);
            City startCity = getValidCity(scanner, "Enter starting city: ", mapManager);
            City targetCity = getValidCity(scanner, "Enter destination city: ", mapManager);

            // Find and draw the optimal path
            pathFinder.findOptimalPath(startCity, targetCity);
            StdDraw.show();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + e.getMessage());
        }
    }

    /**
     * Prompts the user to enter a valid city name and returns the City object.
     * Continues to prompt the user until a valid city name is entered.
     *
     * @param scanner    The scanner object to read user input
     * @param prompt     The prompt to display to the user
     * @param mapManager The map manager object to find the city
     * @return The City object corresponding to the valid city name entered by the user
     */
    private static City getValidCity(Scanner scanner, String prompt, MapManager mapManager) {
        City city = null;
        while(city == null) {
            System.out.print(prompt);
            String cityName = scanner.nextLine();
            if(cityName.isEmpty()) {
                System.out.println("City name cannot be empty. Please enter a valid city name.");
                continue;
            }
            city = mapManager.findCityByName(cityName);
            if (city == null) {
                System.out.println("City named '" + cityName + "' not found. Please enter a valid city name.");
            }
        }
        return city;
    }
}
