package Shapes;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public class UserCaseObj extends PortObj {

    public UserCaseObj(final String name, final int depth, final Coor pos, final double height, final double width) {
        super(name, depth, pos, height, width);
    }

    public boolean isTouch(final Coor clickPos) {
        double condition = Math.pow(clickPos.x - (_pos.x + _w/2), 2) / Math.pow(_w/2, 2) + Math.pow(clickPos.y - (_pos.y + _w/2), 2) / Math.pow(_h/2, 2) - 1; 
        if (condition <= 0.5) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void print(Graphics2D g2, boolean selected) {
        g2.setStroke(new BasicStroke(3));
        double x = _pos.x;
        double y = _pos.y;
        double w = _w;
        double h = _h;
        Ellipse2D.Double shape = new Ellipse2D.Double(x, y, w, h);
        g2.setColor(Color.WHITE);
        g2.fill(shape);
        g2.setColor(Color.BLACK);
        g2.draw(shape);
        if(selected) {
            this.renderPort(g2);
        }
        renderText(g2);
    }
}