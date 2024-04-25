package Shape;

import Point.MyPoint;

import java.awt.*;
import java.util.ArrayList;

public class StarPolygon extends Shape {
    private final ArrayList<MyPoint> points;
    private final int radius;
    private final int n;
    private final int m;

    public StarPolygon(MyPoint position, int radius, int n, int m, boolean filled) {
        super(position, filled);
        this.radius = radius;
        this.n = n;
        this.m = m;
        points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            points.add(new MyPoint(getPosition().getX() + radius + (int) (radius * Math.cos(Math.PI * (2.0 * i / n + 0.5))),
                    getPosition().getY() + radius + ((int) (radius * Math.sin(Math.PI * (2.0 * i / n + 0.5))))));
        }

        /*ArrayList<MyPoint> temp = new ArrayList<>();
        for (int i = 0; i < (n % m == 0 ? m : 1); i++) {
            int j = i;
            do {
                temp.add(points.get(j));
                j = (m + j) % n;
            } while (i != j);
        }
        points = temp;*/
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

    @Override
    public void draw(Graphics2D g2d) {
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];
        for (int i = 0; i < n; i++) {
            xPoints[i] = points.get(i).getX();
            yPoints[i] = points.get(i).getY();
        }
        if (getFilled()) {
            int[] tempX = new int[3];
            int[] tempY = new int[3];
            tempX[0] = getPosition().getX() + radius;
            tempY[0] = getPosition().getY() + radius;
            for (int i = 0; i < n; i++) {
                tempX[1] = xPoints[i];
                tempY[1] = yPoints[i];
                tempX[2] = xPoints[(i + m) % n];
                tempY[2] = yPoints[(i + m) % n];
                g2d.fillPolygon(tempX, tempY, 3);
            }
        } else {
            for (int i = 0; i < n; i++) {
                g2d.drawLine(xPoints[i], yPoints[i], xPoints[(i + m) % n], yPoints[(i + m) % n]);
            }
        }
    }

    private int minX() {
        return getPosition().getX();
    }

    private int maxX() {
        int max = points.get(0).getX();
        for (MyPoint point : points) {
            if (point.getX() > max) {
                max = point.getX();
            }
        }
        return max;
    }

    private int maxY() {
        int max = points.get(0).getY();
        for (MyPoint point : points) {
            if (point.getY() > max) {
                max = point.getY();
            }
        }
        return max;
    }

    private int minY() {
        return getPosition().getY();
    }
}
