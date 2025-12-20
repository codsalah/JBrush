package painter;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line extends Shape {

    // Constructor for full initialization
    public Line(Point point1, Point point2, Color color, float stroke) {
        super(point1, point2, color, stroke, false);
    }

    // Draw the line with the specified color and stroke
    @Override
    public void draw(Graphics2D g2, boolean isFilled) {
        g2.setColor(color);
        g2.setStroke(makeStroke());
        g2.drawLine(point1.x, point1.y, point2.x, point2.y);
    }

    // Check if the point is inside the line (true if inside)
    @Override
    public boolean contains(int px, int py) {
        double dist = Line2D.ptSegDist(point1.x, point1.y, point2.x, point2.y, px, py);
        return dist < (stroke + 2);
    }

}
