package Interface;

import Item.Item;
import Point.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyDrawingPanel extends JPanel implements DrawingPanelInterface {
    private final Scene scene;
    private Color currentColor;
    private final ArrayList<Item> items;
    private final ArrayList<Color> colorsOfItems;

    public MyDrawingPanel(Scene scene) {
        super();
        this.scene = scene;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        currentColor = Color.BLACK;
        items = new ArrayList<>();
        colorsOfItems = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < items.size() && i < colorsOfItems.size(); i++) {
            g2d.setColor(colorsOfItems.get(i));
            items.get(i).draw(g2d);
        }
    }

    public void translate(MyPoint vector) {
        for (Item item : items) {
            item.translate(vector);
        }
        scene.draw();
    }

    public void updateBackground() {
        this.setBackground(currentColor);
    }

    public void addItem(Item item) {
        items.add(item);
        colorsOfItems.add(currentColor);
        scene.draw();
    }

    public void removeItem(Item item) {
        if (!items.contains(item))
            return;

        int index = items.indexOf(item);
        items.remove(index);
        colorsOfItems.remove(index);
        scene.draw();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void reset() {
        setBackground(Color.WHITE);
        items.clear();
        colorsOfItems.clear();
        scene.draw();
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public JPanel getJPanel() {
        return this;
    }
}
