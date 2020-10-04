package Shapes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public class ClassObj extends PortObj {
    public ClassObj(final String name, final int depth, final Coor pos, final double height, final double width) {
        super(name, depth, pos, height, width);
    }

    public boolean isTouch(final Coor clickPos) {
        if (clickPos.x - _pos.x < _w + 0.5 && clickPos.x - _pos.x >= -0.5 && clickPos.y - _pos.y < _h + 0.5
                && clickPos.y - _pos.y >= -0.5) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void print(Graphics2D g2, boolean selected) {
        g2.setStroke(new BasicStroke(3));
        final double x = this.pos().x;
        final double y = this.pos().y;
        final double w = this.width();
        final double h = this.height();
        final double offseth = h / 3;
        final Rectangle2D.Double shape = new Rectangle2D.Double(x, y, w, h);
        final Line2D.Double line1 = new Line2D.Double(x, y + offseth, x + w, y + offseth);
        g2.setColor(Color.WHITE);
        g2.fill(shape);
        g2.setColor(Color.BLACK);
        g2.draw(shape);
        g2.draw(line1);
        line1.setLine(x, y + offseth*2, x + w, y + offseth*2);
        g2.draw(line1);
        if(selected){
            renderPort(g2);
        }
        renderText(g2);
    }
}