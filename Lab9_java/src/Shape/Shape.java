package Shape;

import Item.Primitive;
import Point.MyPoint;

public abstract class Shape extends Primitive {
    private final boolean filled;

public Shape(MyPoint position, boolean filled) {
        super(position);
        this.filled = filled;
    }
    public boolean getFilled(){
        return filled;
    }
}
