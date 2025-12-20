package painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// PaintBoard will be the View component of the application
// It handles the rendering all shapes of the app
public class PaintBoard extends JPanel {

    // Model State Variables
    private final List<Shape> shapes;
    private Color currentColor;
    private float currentStroke;
    private boolean isFilling;
    private boolean isDashed;

    // Temporary state for the shape currently being drawn
    private Shape previewCurrentShape;
    private Point startPoint;
    private String currentTool = "Rectangle"; // Default tool

    // Constructor for full initialization
    public PaintBoard() {
        // array to hold all shapes
        this.shapes = new ArrayList<>();
        // default color until changed
        this.currentColor = Color.BLACK;
        this.currentStroke = 2.0f;
        this.isFilling = false;
        this.isDashed = false;

        // Set the background color as white by default
        setBackground(Color.WHITE);
        setupMouseListeners();
    }

    // Setup mouse listeners for mouse events
    private void setupMouseListeners() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            // Update the start point when mouse is pressed
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
            }

            // Update the preview shape while dragging
            @Override
            public void mouseDragged(MouseEvent e) {
                if (startPoint == null)
                    return;

                Point endPoint = e.getPoint();
                updatePreview(endPoint);
                repaint();
            }

            // Add the shape to the list when mouse is released
            @Override
            public void mouseReleased(MouseEvent e) {
                if (previewCurrentShape != null) {
                    shapes.add(previewCurrentShape);
                    previewCurrentShape = null;
                    startPoint = null;
                    repaint();
                }
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    // To be improved in the future
    private void updatePreview(Point endPoint) {
        switch (currentTool) {
            case "Line":
                previewCurrentShape = new Line(startPoint, endPoint, currentColor, currentStroke);
                break;
            case "Rectangle":
                previewCurrentShape = new Rectangle(startPoint, endPoint, currentColor, currentStroke, isFilling);
                break;
            case "Oval":
                previewCurrentShape = new Oval(startPoint, endPoint, currentColor, currentStroke, isFilling);
                break;
        }
        if (previewCurrentShape != null) {
            previewCurrentShape.dashed = isDashed;
        }
    }

    // To be improved in the future
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ///////////////////////////////
        Graphics2D g2 = (Graphics2D) g;

        // Without it the shapes will look pixelated and not smooth
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all finished shapes
        for (Shape shape : shapes) {
            shape.draw(g2, shape.isFilled);
        }

        // Draw the shape currently being dragged
        if (previewCurrentShape != null) {
            previewCurrentShape.draw(g2, isFilling);
        }
    }

    // Setters for state variables
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setCurrentStroke(float stroke) {
        this.currentStroke = stroke;
    }

    public void setFilling(boolean filling) {
        this.isFilling = filling;
    }

    public void setDashed(boolean dashed) {
        this.isDashed = dashed;
    }

    public void setCurrentTool(String tool) {
        this.currentTool = tool;
    }

    public void addShape(Shape shape) {
        if (shape != null) {
            shapes.add(shape);
            repaint();
        }
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            repaint();
        }
    }

    public void clear() {
        // Predefined method to clear the panel
        shapes.clear();
        repaint();
    }
}
