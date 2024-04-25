package Item;

import Point.MyPoint;

import java.awt.*;

public abstract class Item {
    private MyPoint position;

    public Item(MyPoint position) {
        this.position = position;
    }

    public MyPoint getPosition() {
        return position;
    }
    public void translate(MyPoint vector){
        position.translate(vector);
    }
    public abstract MyPoint[] getBoundingBox();
    public abstract void draw(Graphics2D g2d);
}
