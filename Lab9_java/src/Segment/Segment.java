package Segment;

import Item.Primitive;
import Point.MyPoint;

import java.awt.*;

public class Segment extends Primitive {
    private final int length;
    private final MyPoint start;
    private final MyPoint end;

    public Segment(MyPoint start, MyPoint end) {
        super(new MyPoint(Math.min(start.getX(), end.getX()), Math.min(start.getY(), end.getY())));
        this.start = start;
        this.end = end;
        this.length = (int) Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
    }

    @Override
    public MyPoint[] getBoundingBox() {
        MyPoint[] boundingBox = new MyPoint[4];
        boundingBox[0] = getPosition();
        boundingBox[1] = new MyPoint(Math.max(start.getX(), end.getX()), Math.min(start.getY(), end.getY()));
        boundingBox[2] = new MyPoint(Math.max(start.getX(), end.getX()), Math.max(start.getY(), end.getY()));
        boundingBox[3] = new MyPoint(Math.min(start.getX(), end.getX()), Math.max(start.getY(), end.getY()));

        return boundingBox;
    }

    public void translate(MyPoint vector) {
        super.translate(vector);

        start.translate(vector);
        end.translate(vector);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public int getLength() {
        return length;
    }

    public MyPoint getStart() {
        return start;
    }

    public MyPoint getEnd() {
        return end;
    }
}
