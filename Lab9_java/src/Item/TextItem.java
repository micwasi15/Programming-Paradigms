package Item;

import Point.MyPoint;

import java.awt.*;
import java.util.Arrays;

public class TextItem extends Item {
    private final String text;
    private final MyPoint[] boundingBox;

    public TextItem(MyPoint position, String text) {
        super(position);
        this.text = text;
        boundingBox = new MyPoint[4];
        Arrays.fill(boundingBox, new MyPoint());
    }

    public String getText() {
        return text;
    }

    @Override
    public MyPoint[] getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textLength = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        g2d.drawString(text, getPosition().getX(), getPosition().getY() + textHeight);

        boundingBox[0] = getPosition();
        boundingBox[1] = new MyPoint(getPosition().getX() + textLength, getPosition().getY());
        boundingBox[2] = new MyPoint(getPosition().getX() + textLength, getPosition().getY() + textHeight);
        boundingBox[3] = new MyPoint(getPosition().getX(), getPosition().getY() + textHeight);
    }
}
