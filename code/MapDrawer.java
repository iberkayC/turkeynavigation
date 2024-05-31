import java.awt.Font;
import java.util.ArrayList;

/**
 * Handles the drawing of the map, draws cities, paths and the map layout.
 */
public class MapDrawer {
    private final static int WIDTH = 2377;
    private final static int HEIGHT = 1055;
    private final static int SCALE = 2;
    private final Font font;

    /**
     * Constructor for MapDrawer.
     */
    public MapDrawer() {
        font = new Font("Arial", Font.PLAIN, 12);
        initializeCanvas();
    }

    /**
     * Initialize the canvas.
     */
    public void initializeCanvas() {
        StdDraw.setCanvasSize(WIDTH / SCALE, HEIGHT / SCALE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Draws the background map on the canvas.
     */
    public void drawMap() {
        StdDraw.clear();
        StdDraw.picture(WIDTH / 2.0, HEIGHT / 2.0, "map.png", WIDTH, HEIGHT, 0);
    }

    /**
     * Draw all cities on the map.
     *
     * @param cities An ArrayList of cities to draw.
     */
    public void drawCities(ArrayList<City> cities) {
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.setPenRadius(0.005);
        StdDraw.setFont(font);
        for (City city : cities) {
            drawCity(city);
        }
    }

    /**
     * Draw a single city on the map
     *
     * @param city The city to be drawn
     */
    public void drawCity(City city) {
        StdDraw.filledCircle(city.getX(), city.getY(), 5);
        StdDraw.text(city.getX(),city.getY() + 15, city.getCityName());
    }

    /**
     * Draws roads between cities based on their connections.
     *
     * @param cities An ArrayList of cities to draw roads between.
     */
    public void drawRoads(ArrayList<City> cities) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.002);
        for (City city : cities) {
            for (City connectedCity : city.getConnections()) {
                drawPath(city, connectedCity);
            }
        }
    }

    /**
     * Draw a path or a road between two cities.
     *
     * @param city1 The first city of the path
     * @param city2 The second city of the path.
     */
    private void drawPath(City city1, City city2) {
        StdDraw.line(city1.getX(), city1.getY(), city2.getX(), city2.getY());
    }

    /**
     * Draws the optimal path found by the PathFinder.
     *
     * @param path An ArrayList of cities representing the optimal path.
     */
    public void drawOptimalPath(ArrayList<City> path) {
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.setFont(font);
        for (int i = 0; i < path.size() - 1; i++) {
            StdDraw.setPenRadius(0.006);
            drawPath(path.get(i), path.get(i + 1));
            StdDraw.setPenRadius(0.005);
            drawCity(path.get(i));
        }
        drawCity(path.getLast());
    }
}
