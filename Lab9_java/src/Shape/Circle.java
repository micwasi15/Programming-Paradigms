package Shape;

import Point.MyPoint;

import java.awt.*;

public class Circle extends Shape {
    private final int radius;

    public Circle(MyPoint position, int radius, boolean filled) {
        super(position, filled);
        this.radius = radius;
    }

    public MyPoint[] getBoundingBox() {
        MyPoint[] boundingBox = new MyPoint[4];
        boundingBox[0] = getPosition();
        boundingBox[1] = new MyPoint(getPosition().getX() + 2 * radius, getPosition().getY());
        boundingBox[2] = new MyPoint(getPosition().getX() + 2 * radius, getPosition().getY() + 2 * radius);
        boundingBox[3] = new MyPoint(getPosition().getX(), getPosition().getY() + 2 * radius);

        return boundingBox;
    }

    public void draw(Graphics2D g2d) {
        if (getFilled())
            g2d.fillOval(getPosition().getX(), getPosition().getY(), 2 * radius, 2 * radius);
        else {
            g2d.drawOval(getPosition().getX(), getPosition().getY(), 2 * radius, 2 * radius);
        }
    }

    public int getRadius() {
        return radius;
    }
}
