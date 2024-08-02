import javax.swing.*;
import java.awt.*;

class FlightPathPanel extends JPanel {
    // Adjusted coordinates to avoid crossing paths
    private int[][] flight1 = {{1, 1}, {2, 2}, {3, 3}};
    private int[][] flight2 = {{1, 1}, {2, 4}, {3, 2}};
    private int[][] flight3 = {{1, 1}, {4, 2}, {3, 4}};
    private final int SCALE = 50; // scale factor for better visibility
    private final int POINT_RADIUS = 10;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFlightPath(g, flight1, Color.BLUE);
        drawFlightPath(g, flight2, Color.YELLOW);
        drawFlightPath(g, flight3, Color.RED);
    }

    private void drawFlightPath(Graphics g, int[][] flight, Color color) {
        g.setColor(color);
        for (int i = 0; i < flight.length - 1; i++) {
            int x1 = flight[i][0] * SCALE;
            int y1 = getHeight() - flight[i][1] * SCALE;
            int x2 = flight[i + 1][0] * SCALE;
            int y2 = getHeight() - flight[i + 1][1] * SCALE;
            g.drawLine(x1, y1, x2, y2);
        }
        // Draw circles at each point
        for (int[] point : flight) {
            int x = point[0] * SCALE;
            int y = getHeight() - point[1] * SCALE;
            g.fillOval(x - POINT_RADIUS / 2, y - POINT_RADIUS / 2, POINT_RADIUS, POINT_RADIUS);
        }
    }
}

public class FlightPathVisualizer {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flight Path Visualizer");
        FlightPathPanel panel = new FlightPathPanel();
        frame.add(panel);
        panel.setBackground(Color.gray);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}