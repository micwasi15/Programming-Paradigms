package Interface;

import Item.Item;
import Point.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public interface DrawingPanelInterface {
    void translate(MyPoint vector);
    void updateBackground();
    void addItem(Item item);
    void removeItem(Item item);
    List<Item> getItems();
    void reset();
    void setCurrentColor(Color currentColor);
    Color getCurrentColor();
    JPanel getJPanel();
}
