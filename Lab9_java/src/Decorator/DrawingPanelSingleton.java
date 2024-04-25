package Decorator;

import Interface.DrawingPanelInterface;
import Item.Item;

public class DrawingPanelSingleton extends DrawingPanelDecorator {
    public DrawingPanelSingleton(DrawingPanelInterface drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void addItem(Item item) {
        Item itemOfTheSameType = getItemOfTheSameType(item);
        if (itemOfTheSameType != null) {
            removeItem(itemOfTheSameType);
        }

        super.addItem(item);
    }

    private Item getItemOfTheSameType(Item item) {
        for (Item i : getItems()) {
            if (i.getClass().equals(item.getClass())) {
                return i;
            }
        }
        return null;
    }
}
