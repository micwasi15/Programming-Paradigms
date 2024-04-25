package Decorator;

import Interface.DrawingPanelInterface;
import Item.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Point.MyPoint;

public abstract class DrawingPanelDecorator implements DrawingPanelInterface {
    private final DrawingPanelInterface drawingPanel;

    public DrawingPanelDecorator(DrawingPanelInterface drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void addItem(Item item) {
        drawingPanel.addItem(item);
    }

    @Override
    public void removeItem(Item item) {
        drawingPanel.removeItem(item);
    }

    @Override
    public List<Item> getItems() {
        return drawingPanel.getItems();
    }

    @Override
    public JPanel getJPanel() {
        return drawingPanel.getJPanel();
    }

    @Override
    public Color getCurrentColor() {
        return drawingPanel.getCurrentColor();
    }

    @Override
    public void setCurrentColor(Color color) {
        drawingPanel.setCurrentColor(color);
    }

    @Override
    public void updateBackground() {
        drawingPanel.updateBackground();
    }

    @Override
    public void reset() {
        drawingPanel.reset();
    }

    @Override
    public void translate(MyPoint vector) {
        drawingPanel.translate(vector);
    }
}
