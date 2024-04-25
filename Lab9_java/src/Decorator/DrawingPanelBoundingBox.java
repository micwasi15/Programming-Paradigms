package Decorator;

import Interface.DrawingPanelInterface;
import Item.Item;
import Point.MyPoint;
import Shape.BoundingBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DrawingPanelBoundingBox extends DrawingPanelDecorator {
    private BoundingBox boundingBoxRef = null;

    public DrawingPanelBoundingBox(DrawingPanelInterface drawingPanel) {
        super(drawingPanel);

        drawingPanel.getJPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                removeBoundingBox();

                MyPoint point = new MyPoint(event.getX(), event.getY());
                List<Item> items = getItems();

                for (int i = items.size() - 1; i >= 0; i--) {
                    MyPoint[] boundingBox = items.get(i).getBoundingBox();

                    if (point.isInsideBoundingBox(boundingBox)) {
                        int minX = boundingBox[0].getX();
                        int maxX = boundingBox[0].getX();
                        int minY = boundingBox[0].getY();
                        int maxY = boundingBox[0].getY();

                        for (int j = 1; j < boundingBox.length; j++) {
                            if (boundingBox[j].getX() < minX) {
                                minX = boundingBox[j].getX();
                            }
                            if (boundingBox[j].getX() > maxX) {
                                maxX = boundingBox[j].getX();
                            }
                            if (boundingBox[j].getY() < minY) {
                                minY = boundingBox[j].getY();
                            }
                            if (boundingBox[j].getY() > maxY) {
                                maxY = boundingBox[j].getY();
                            }
                        }

                        int width = maxX - minX;
                        int height = maxY - minY;
                        boundingBoxRef = new BoundingBox(new MyPoint(minX, minY), width, height);
                        addItem(boundingBoxRef);

                        break;
                    }
                }
            }
        });
    }

    private void removeBoundingBox() {
        if (boundingBoxRef != null) {
            super.removeItem(boundingBoxRef);
            boundingBoxRef = null;
        }
    }

    @Override
    public void removeItem(Item item) {
        if (item instanceof BoundingBox) {
            int index = getItems().indexOf(item);
            if (index > 0)
                super.removeItem(getItems().get(index - 1));
        } else {
            super.removeItem(item);
        }
        removeBoundingBox();
    }
}
