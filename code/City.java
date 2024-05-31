import java.util.ArrayList;

/**
 * City class
 * This class represents a city on the map.
 * It has a name, x and y coordinates, and a list of connections to other cities.
 * It also has a method to connect to another city.
 */
public class City {
    private final String cityName; // Name of the city
    private final int x; // x-coordinate of the city
    private final int y; // y-coordinate of the city
    private final int index; // Index of the city in the map manager's list of cities
    private final ArrayList<City> connections; // List of connections to other cities

    /**
     * Constructor for City class.
     *
     * @param cityName The name of the city
     * @param x        The x-coordinate of the city
     * @param y        The y-coordinate of the city
     * @param index    The index of the city in map manager's list of cities
     */
    public City(String cityName, int x, int y, int index) {
        this.cityName = cityName;
        this.x = x;
        this.y = y;
        this.index = index;
        this.connections = new ArrayList<>();
    }

    /**
     * Get the name of the city.
     *
     * @return The name of the city.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Get the x-coordinate of the city.
     *
     * @return The x-coordinate of the city.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y-coordinate of the city.
     *
     * @return The y-coordinate of the city.
     */
    public int getY() {
        return y;
    }

    /**
     * Get the index of the city.
     *
     * @return The index of the city.
     */
    public int getIndex() { return index; }

    /**
     * Get the list of connections to other cities.
     *
     * @return The list of connections to other cities.
     */
    public ArrayList<City> getConnections() {
        return connections;
    }

    /**
     * Connect this city to another city, the connection is symmetric.
     *
     * @param city The city to connect to.
     */
    public void connect(City city) {
        if (city == null || connections.contains(city)) {
            return;
        }
        connections.add(city);
        city.connections.add(this); // symmetric connection
    }
}
