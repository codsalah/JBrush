import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Painter extends JPanel {

    // Tools (no extra class)
    public static final int BRUSH = 0;
    public static final int ERASER = 1;
    public static final int LINE = 2;
    public static final int RECT = 3;
    public static final int OVAL = 4;

    private int tool = BRUSH;

    private Color currentColor = Color.BLACK;
    private boolean dashed = false;
    private boolean filled = false;

    private int brushW = 10;
    private int brushH = 10;

    private int startX, startY;

    private ArrayList<Shape> shapes = new ArrayList<>();
    private Shape previewShape = null;

    public Painter() {
        setBackground(Color.WHITE);

        MouseAdapter mouse = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();

                if (tool == BRUSH) {
                    addDot(e.getX(), e.getY(), currentColor);
                } else if (tool == ERASER) {
                    addDot(e.getX(), e.getY(), getBackground());
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                if (tool == BRUSH) {
                    addDot(e.getX(), e.getY(), currentColor);
                    return;
                }

                if (tool == ERASER) {
                    addDot(e.getX(), e.getY(), getBackground());
                    return;
                }

                // Preview for LINE/RECT/OVAL
                previewShape = createShape(startX, startY, e.getX(), e.getY());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (tool == LINE || tool == RECT || tool == OVAL) {
                    Shape finalShape = createShape(startX, startY, e.getX(), e.getY());
                    shapes.add(finalShape);
                    previewShape = null;
                    repaint();
                }
            }
        };

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    // ===== Brush/Eraser dots =====
    private void addDot(int mx, int my, Color c) {
        int x = mx - brushW / 2;
        int y = my - brushH / 2;

        shapes.add(new Freehand(x, y, c, brushW, brushH));
        repaint();
    }

    // ===== Create shapes =====
    private Shape createShape(int x1, int y1, int x2, int y2) {

        if (tool == LINE) {
            Line line = new Line(x1, y1, currentColor, x2, y2);
            line.setDashed(dashed);
            return line;
        }

        // RECT/OVAL: convert drag points to top-left + positive w/h
        int left = Math.min(x1, x2);
        int top  = Math.min(y1, y2);
        int w    = Math.abs(x2 - x1);
        int h    = Math.abs(y2 - y1);

        if (tool == RECT) {
            Rectangle r = new Rectangle(left, top, currentColor, w, h);
            r.setDashed(dashed);
            r.setFilled(filled);
            return r;
        }

        // OVAL
        Oval o = new Oval(left, top, currentColor, w, h);
        o.setDashed(dashed);
        o.setFilled(filled);
        return o;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (Shape s : shapes) {
            s.draw(g2);
        }

        if (previewShape != null) {
            previewShape.draw(g2);
        }
    }

    // ===== Setters called by UI buttons/checkboxes =====
    public void setTool(int tool) {
        this.tool = tool;
        previewShape = null; // remove preview when switching tool
    }

    public void setCurrentColor(Color c) {
        this.currentColor = c;
    }

    public void setDashed(boolean dashed) {
        this.dashed = dashed;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void setBrushSize(int w, int h) {
        this.brushW = w;
        this.brushH = h;
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            previewShape = null;
            repaint();
        }
    }

    public void clear() {
        shapes.clear();
        previewShape = null;
        repaint();
    }

}
