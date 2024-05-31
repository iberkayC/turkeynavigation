import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * MapManager class manages map data.
 * Reads city and connection data from files and stores them.
 */
public class MapManager {
    private ArrayList<City> cities;

    /**
     * Constructor for MapManager.
     */
    public MapManager() {
        cities = new ArrayList<>();
    }

    /**
     * Read cities from file and store them in the cities ArrayList.
     * Each line in the file should be in the format "cityName, x, y".
     *
     * @param filename               The name of the file to read.
     * @throws FileNotFoundException If file not found.
     */
    public void readCitiesFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        int index = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(", ");
            String cityName = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);

            City city = new City(cityName, x, y, index++);
            cities.add(city);
        }
    }

    /**
     * Read connections between cities from a file and connect them.
     * Each line in the file should be in the format "city1,city2".
     *
     * @param filename               The name of the file to read.
     * @throws FileNotFoundException If file not found.
     */
    public void readConnectionsFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            City startCity = findCityByName(parts[0]);

            City destinationCity = findCityByName(parts[1]);
            if (destinationCity != null) {
                startCity.connect(destinationCity);
            }
            else {
                System.out.println("Connection Error: City not found - " + parts[1]);
            }
        }
    }

    /**
     * Find a city by name.
     *
     * @param cityName The name of the city to be found.
     * @return The City object if found, null otherwise.
     */
    public City findCityByName(String cityName) {
        for(City city : cities) {
            if(city.getCityName().equalsIgnoreCase(cityName)) {
                return city;
            }
        }
        return null;
    }

    /**
     * Get list of cities.
     *
     * @return ArrayList containing all cities.
     */
    public ArrayList<City> getCities() {
        return cities;
    }
}
