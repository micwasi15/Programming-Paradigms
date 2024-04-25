package Point;

public class MyPoint {
    private int x;
    private int y;

    public MyPoint() {
        x = 0;
        y = 0;
    }

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void translate(MyPoint vector) {
        x += vector.getX();
        y += vector.getY();
    }

    public boolean isInsideBoundingBox(MyPoint[] boundingBox) {
        if (boundingBox.length < 4) {
            return false;
        }

        int minX = boundingBox[0].getX();
        int maxX = boundingBox[0].getX();
        int minY = boundingBox[0].getY();
        int maxY = boundingBox[0].getY();

        for (int i = 1; i < boundingBox.length; i++) {
            if (boundingBox[i].getX() < minX) {
                minX = boundingBox[i].getX();
            }
            if (boundingBox[i].getX() > maxX) {
                maxX = boundingBox[i].getX();
            }
            if (boundingBox[i].getY() < minY) {
                minY = boundingBox[i].getY();
            }
            if (boundingBox[i].getY() > maxY) {
                maxY = boundingBox[i].getY();
            }
        }

        return getX() >= minX && getX() <= maxX && getY() >= minY && getY() <= maxY;
    }
}
