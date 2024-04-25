package Shape;

import Point.MyPoint;

import java.awt.*;

public class Triangle extends Shape {
    private final MyPoint P1, P2, P3;

    public Triangle(MyPoint P1, MyPoint P2, MyPoint P3, boolean filled) {
        super(new MyPoint(Math.min(P1.getX(), Math.min(P2.getX(), P3.getX())), Math.min(P1.getY(), Math.min(P2.getY(), P3.getY()))), filled);
        this.P1 = P1;
        this.P2 = P2;
        this.P3 = P3;
    }

    @Override
    public MyPoint[] getBoundingBox() {
        MyPoint[] boundingBox = new MyPoint[4];
        boundingBox[0] = getPosition();
        boundingBox[1] = new MyPoint(maxX(), minY());
        boundingBox[2] = new MyPoint(maxX(), maxY());
        boundingBox[3] = new MyPoint(minX(), maxY());

        return boundingBox;
    }

    public void translate(MyPoint vector) {
        super.translate(vector);

        P1.translate(vector);
        P2.translate(vector);
        P3.translate(vector);
    }

    public void draw(Graphics2D g2d) {
        int[] xPoints = {P1.getX(), P2.getX(), P3.getX()};
        int[] yPoints = {P1.getY(), P2.getY(), P3.getY()};
        if (getFilled()) {
            g2d.fillPolygon(xPoints, yPoints, 3);
        } else {
            g2d.drawPolygon(xPoints, yPoints, 3);
        }
    }

    private int minX() {
        return getPosition().getX();
    }

    private int maxX() {
        return Math.max(P1.getX(), Math.max(P2.getX(), P3.getX()));
    }

    private int minY() {
        return getPosition().getY();
    }

    private int maxY() {
        return Math.max(P1.getY(), Math.max(P2.getY(), P3.getY()));
    }

    public MyPoint getP1() {
        return P1;
    }

    public MyPoint getP2() {
        return P2;
    }

    public MyPoint getP3() {
        return P3;
    }
}
