package Item;

import Point.MyPoint;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ComplexItem extends Item {
    private final List<Item> children;

    public ComplexItem(MyPoint position, List<Item> children) {
        super(position);
        this.children = children;
    }

    public List<Item> getChildren() {
        return children;
    }

    public MyPoint[] getBoundingBox() {
        MyPoint[] boundingBox = new MyPoint[4];

        if (children.isEmpty()){
            Arrays.fill(boundingBox, getPosition());
            return boundingBox;
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Item child : children) {
            MyPoint[] tempBoundingBox = child.getBoundingBox();
            if (tempBoundingBox != null) {
                for (MyPoint point : tempBoundingBox) {
                    if (point.getX() < minX) {
                        minX = point.getX();
                    }
                    if (point.getX() > maxX) {
                        maxX = point.getX();
                    }
                    if (point.getY() < minY) {
                        minY = point.getY();
                    }
                    if (point.getY() > maxY) {
                        maxY = point.getY();
                    }
                }
            }
        }



        boundingBox[0] = new MyPoint(minX, minY);
        boundingBox[1] = new MyPoint(maxX, minY);
        boundingBox[2] = new MyPoint(maxX, maxY);
        boundingBox[3] = new MyPoint(minX, maxY);

        return boundingBox;
    }

    public void draw(Graphics2D g2d) {
        for (Item child : children) {
            child.draw(g2d);
        }
    }

    public void translate(MyPoint vector) {
        for (Item child : children) {
            child.translate(vector);
        }
    }
}
