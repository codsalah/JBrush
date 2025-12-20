import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PainterFrame extends JFrame {

    public PainterFrame() {
        super("Painter App");

        final Painter canvas = new Painter();

        // ===== Tool Buttons =====
        JButton brushBtn  = new JButton("Brush");
        JButton eraserBtn = new JButton("Eraser");
        JButton lineBtn   = new JButton("Line");
        JButton rectBtn   = new JButton("Rectangle");
        JButton ovalBtn   = new JButton("Oval");

        brushBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setTool(Painter.BRUSH);
            }
        });

        eraserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setTool(Painter.ERASER);
            }
        });

        lineBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setTool(Painter.LINE);
            }
        });

        rectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setTool(Painter.RECT);
            }
        });

        ovalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setTool(Painter.OVAL);
            }
        });

        JButton undoBtn = new JButton("Undo");

        undoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.undo();
            }
        });


        JButton clearBtn = new JButton("Clear");

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.clear();
            }
        });

        // ===== Color Buttons =====
        JButton blackBtn = new JButton("Black");
        JButton blueBtn  = new JButton("Blue");
        JButton greenBtn = new JButton("Green");
        JButton redBtn   = new JButton("Red");

        blackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentColor(Color.BLACK);
            }
        });

        blueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentColor(Color.BLUE);
            }
        });

        greenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentColor(Color.GREEN);
            }
        });

        redBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentColor(Color.RED);
            }
        });

        // ===== Checkboxes =====
        final JCheckBox dashedCheck = new JCheckBox("Dashed");
        final JCheckBox filledCheck = new JCheckBox("Filled");

        dashedCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setDashed(dashedCheck.isSelected());
            }
        });

        filledCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setFilled(filledCheck.isSelected());
            }
        });

        // ===== Toolbar Panel =====
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        top.add(new JLabel("Tools:"));
        top.add(brushBtn);
        top.add(eraserBtn);
        top.add(lineBtn);
        top.add(rectBtn);
        top.add(ovalBtn);

        top.add(new JLabel("   ")); // simple spacing

        top.add(new JLabel("Colors:"));
        top.add(blackBtn);
        top.add(blueBtn);
        top.add(greenBtn);
        top.add(redBtn);

        top.add(new JLabel("   "));

        top.add(dashedCheck);
        top.add(filledCheck);

        top.add(new JLabel("   "));
        top.add(undoBtn);
        top.add(clearBtn);

        // ===== Frame Layout =====
        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);

        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PainterFrame();
    }
}

