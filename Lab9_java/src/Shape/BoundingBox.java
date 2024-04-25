package Shape;

import Point.MyPoint;

import java.awt.*;

public class BoundingBox extends Rect {
    public BoundingBox(MyPoint position, int width, int height) {
        super(position, width, height, false);
    }

    public void draw(Graphics2D g2d) {
        Stroke originalStroke = g2d.getStroke();
        Color originalColor = g2d.getColor();

        float[] dash1 = {5.0f};
        BasicStroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash1, 0.0f);
        g2d.setStroke(dashedStroke);
        g2d.setColor(Color.BLACK);

        super.draw(g2d);
        g2d.setStroke(originalStroke);
        g2d.setColor(originalColor);
    }
}
