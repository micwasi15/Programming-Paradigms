package Interface;

import Decorator.DrawingPanelBoundingBox;
import Item.*;
import Point.MyPoint;
import Segment.Segment;
import Shape.*;
import Decorator.DrawingPanelSingleton;

import javax.swing.*;
import java.awt.*;

public class Scene {
    private final JFrame frame;
    private final DrawingPanelInterface drawingMyPanel;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    public Scene() {
        this(WIDTH, HEIGHT);
    }

    public Scene(int width, int height) {
        frame = new JFrame("GInterface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height));

        drawingMyPanel = new DrawingPanelSingleton(new DrawingPanelBoundingBox(new MyDrawingPanel(this)));
        JPanel colorButtonPanel = createColorButtonPanel();
        JPanel controlPanel = new JPanel(new GridLayout(2, 1));

        controlPanel.add(createTranslateAndDeletePanel());
        controlPanel.add(createControlsPanel());

        frame.add(colorButtonPanel, BorderLayout.NORTH);
        frame.add(drawingMyPanel.getJPanel());
        frame.add(controlPanel, BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);
    }

    public void addItem(Item item) {
        drawingMyPanel.addItem(item);
    }

    public void draw() {
        frame.repaint();
    }

    private JPanel createColorButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 5));

        JButton colorInfoButton = new JButton("Current color");
        colorInfoButton.setEnabled(false);
        colorInfoButton.setBackground(drawingMyPanel.getCurrentColor());

        JButton redButton = createColorButton(Color.RED, colorInfoButton);
        JButton greenButton = createColorButton(Color.GREEN, colorInfoButton);
        JButton blueButton = createColorButton(Color.BLUE, colorInfoButton);
        JButton yellowButton = createColorButton(Color.YELLOW, colorInfoButton);
        JButton cyanButton = createColorButton(Color.CYAN, colorInfoButton);
        JButton magentaButton = createColorButton(Color.MAGENTA, colorInfoButton);
        JButton blackButton = createColorButton(Color.BLACK, colorInfoButton);
        JButton whiteButton = createColorButton(Color.WHITE, colorInfoButton);
        JButton backgroundColorButton = new JButton("Set background color");
        backgroundColorButton.addActionListener(e -> drawingMyPanel.updateBackground());

        panel.add(redButton);
        panel.add(greenButton);
        panel.add(blueButton);
        panel.add(yellowButton);
        panel.add(colorInfoButton);

        panel.add(cyanButton);
        panel.add(magentaButton);
        panel.add(blackButton);
        panel.add(whiteButton);
        panel.add(backgroundColorButton);

        return panel;
    }

    private JButton createColorButton(Color color, JButton colorLabel) {
        JButton button = new JButton();

        button.setBackground(color);
        button.addActionListener(e -> {
            drawingMyPanel.setCurrentColor(color);
            colorLabel.setBackground(color);
        });

        return button;
    }

    private JPanel createTranslateAndDeletePanel() {
        JPanel panel = new JPanel();
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        JButton translateButton = new JButton("Translate");
        translateButton.addActionListener(e -> {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                drawingMyPanel.translate(new MyPoint(x, y));
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        });

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            if (!drawingMyPanel.getItems().isEmpty())
                drawingMyPanel.removeItem(drawingMyPanel.getItems().get(drawingMyPanel.getItems().size() - 1));
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> drawingMyPanel.reset());

        panel.add(new JLabel("X:"));
        panel.add(xField);
        panel.add(new JLabel("Y:"));
        panel.add(yField);
        panel.add(translateButton);
        panel.add(undoButton);
        panel.add(clearButton);

        return panel;
    }

    private JPanel createControlsPanel() {
        JPanel bigPanel = new JPanel(new GridLayout(3, 1));
        JPanel panel = new JPanel();

        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
        JLabel xLabel = new JLabel("X:");
        JLabel yLabel = new JLabel("Y:");
        JCheckBox filledCheckBox = new JCheckBox("Filled");
        JButton createButton = new JButton("Create");

        String[] shapeStrings = {"Circle", "Triangle", "Rectangle", "Segment", "Text", "Star polygon"};
        JComboBox<String> shapeList = new JComboBox<>(shapeStrings);
        shapeList.setSelectedIndex(0);

        shapeList.addActionListener(e -> {
            String shape = (String) shapeList.getSelectedItem();
            xField.setVisible(true);
            yField.setVisible(true);
            xLabel.setVisible(true);
            yLabel.setVisible(true);
            filledCheckBox.setVisible(true);
            createButton.removeActionListener(createButton.getActionListeners()[0]);

            switch (shape) {
                case "Circle":
                    bigPanel.remove(2);
                    bigPanel.add(createCirclePanel(xField, yField, filledCheckBox, createButton));
                    break;
                case "Triangle":
                    bigPanel.remove(2);
                    xField.setVisible(false);
                    yField.setVisible(false);
                    xLabel.setVisible(false);
                    yLabel.setVisible(false);
                    bigPanel.add(createTrianglePanel(filledCheckBox, createButton));
                    break;
                case "Rectangle":
                    bigPanel.remove(2);
                    bigPanel.add(createRectPanel(xField, yField, filledCheckBox, createButton));
                    break;
                case "Segment":
                    bigPanel.remove(2);
                    xField.setVisible(false);
                    yField.setVisible(false);
                    xLabel.setVisible(false);
                    yLabel.setVisible(false);
                    filledCheckBox.setVisible(false);
                    bigPanel.add(createSegmentPanel(createButton));
                    break;
                case "Text":
                    bigPanel.remove(2);
                    filledCheckBox.setVisible(false);
                    bigPanel.add(createTextPanel(xField, yField, createButton));
                    break;
                case "Star polygon":
                    bigPanel.remove(2);
                    bigPanel.add(createStarPolygonPanel(xField, yField, filledCheckBox, createButton));
                    break;
            }
            bigPanel.revalidate();
        });

        panel.add(xLabel, BorderLayout.CENTER);
        panel.add(xField, BorderLayout.CENTER);
        panel.add(yLabel, BorderLayout.CENTER);
        panel.add(yField, BorderLayout.CENTER);
        panel.add(filledCheckBox, BorderLayout.CENTER);
        panel.add(createButton, BorderLayout.CENTER);

        bigPanel.add(panel);
        bigPanel.add(shapeList);
        bigPanel.add(createCirclePanel(xField, yField, filledCheckBox, createButton));

        return bigPanel;
    }

    private JPanel createRectPanel(JTextField xField, JTextField yField, JCheckBox filledCheckBox, JButton createButton) {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);

        createButton.addActionListener(e -> {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                boolean filled = filledCheckBox.isSelected();
                addItem(new Rect(new MyPoint(x, y), width, height, filled));
            } catch (NumberFormatException ignored) {
            }
        });

        panel.add(new JLabel("Width:"));
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        panel.add(heightField);

        return panel;
    }

    private JPanel createCirclePanel(JTextField xField, JTextField yField, JCheckBox filledCheckBox, JButton createButton) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JTextField radiusField = new JTextField(5);

        createButton.addActionListener(e -> {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                int radius = Integer.parseInt(radiusField.getText());
                boolean filled = filledCheckBox.isSelected();
                addItem(new Circle(new MyPoint(x, y), radius, filled));
            } catch (NumberFormatException ignored) {
            }
        });

        panel.add(new JLabel("Radius:"));
        panel.add(radiusField);

        return panel;
    }

    private JPanel createTrianglePanel(JCheckBox filledCheckBox, JButton createButton) {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField x1Field = new JTextField(5);
        JTextField y1Field = new JTextField(5);
        JTextField x2Field = new JTextField(5);
        JTextField y2Field = new JTextField(5);
        JTextField x3Field = new JTextField(5);
        JTextField y3Field = new JTextField(5);

        createButton.addActionListener(e -> {
            try {
                int x1 = Integer.parseInt(x1Field.getText());
                int y1 = Integer.parseInt(y1Field.getText());
                int x2 = Integer.parseInt(x2Field.getText());
                int y2 = Integer.parseInt(y2Field.getText());
                int x3 = Integer.parseInt(x3Field.getText());
                int y3 = Integer.parseInt(y3Field.getText());
                boolean filled = filledCheckBox.isSelected();
                addItem(new Triangle(new MyPoint(x1, y1), new MyPoint(x2, y2), new MyPoint(x3, y3), filled));
            } catch (NumberFormatException ignored) {
            }
        });

        panel.add(new JLabel("Points:"));
        panel.add(new JLabel());
        panel.add(new JLabel("X"));
        panel.add(new JLabel("Y"));
        panel.add(x1Field);
        panel.add(y1Field);
        panel.add(x2Field);
        panel.add(y2Field);
        panel.add(x3Field);
        panel.add(y3Field);

        return panel;
    }

    JPanel createSegmentPanel(JButton createButton) {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField x1Field = new JTextField(5);
        JTextField y1Field = new JTextField(5);
        JTextField x2Field = new JTextField(5);
        JTextField y2Field = new JTextField(5);

        createButton.addActionListener(e -> {
            try {
                int x1 = Integer.parseInt(x1Field.getText());
                int y1 = Integer.parseInt(y1Field.getText());
                int x2 = Integer.parseInt(x2Field.getText());
                int y2 = Integer.parseInt(y2Field.getText());
                addItem(new Segment(new MyPoint(x1, y1), new MyPoint(x2, y2)));
            } catch (NumberFormatException ignored) {
            }
        });

        panel.add(new JLabel("Points:"));
        panel.add(new JLabel());
        panel.add(new JLabel("X"));
        panel.add(new JLabel("Y"));
        panel.add(x1Field);
        panel.add(y1Field);
        panel.add(x2Field);
        panel.add(y2Field);

        return panel;
    }

    private JPanel createTextPanel(JTextField xField, JTextField yField, JButton createButton) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JTextField textField = new JTextField(5);

        createButton.addActionListener(e -> {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                String text = textField.getText();
                addItem(new TextItem(new MyPoint(x, y), text));
            } catch (NumberFormatException ignored) {
            }
        });

        panel.add(new JLabel("Text:"));
        panel.add(textField);

        return panel;
    }

    private JPanel createStarPolygonPanel(JTextField xField, JTextField yField, JCheckBox filledCheckBox, JButton createButton) {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField radiusField = new JTextField(5);
        JTextField nField = new JTextField(5);
        JTextField mField = new JTextField(5);

        createButton.addActionListener(e -> {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                int radius = Integer.parseInt(radiusField.getText());
                int n = Integer.parseInt(nField.getText());
                int m = Integer.parseInt(mField.getText());
                boolean filled = filledCheckBox.isSelected();
                addItem(new StarPolygon(new MyPoint(x, y), radius, n, m, filled));
            } catch (NumberFormatException ignored) {
            }
        });

        panel.add(new JLabel("Radius:"));
        panel.add(radiusField);
        panel.add(new JLabel("N:"));
        panel.add(nField);
        panel.add(new JLabel("M:"));
        panel.add(mField);

        return panel;
    }
}

