package Shape;

import Point.MyPoint;

import java.awt.*;

public class Rect extends Shape {
    private final int width;
    private final int height;

    public Rect(MyPoint position, int width, int height, boolean filled) {
        super(position, filled);
        this.width = width;
        this.height = height;
    }

    @Override
    public MyPoint[] getBoundingBox() {
        MyPoint[] boundingBox = new MyPoint[4];
        boundingBox[0] = getPosition();
        boundingBox[1] = new MyPoint(getPosition().getX() + width, getPosition().getY());
        boundingBox[2] = new MyPoint(getPosition().getX() + width, getPosition().getY() + height);
        boundingBox[3] = new MyPoint(getPosition().getX(), getPosition().getY() + height);

        return boundingBox;
    }

    public void draw(Graphics2D g2d) {
        if (getFilled())
            g2d.fillRect(getPosition().getX(), getPosition().getY(), width, height);
        else {
            g2d.drawRect(getPosition().getX(), getPosition().getY(), width, height);
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
