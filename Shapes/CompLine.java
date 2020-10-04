package Shapes;

import java.util.ArrayList;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public class CompLine extends LineObj{
    public CompLine(final String name, final int depth, final Coor pos, Port startPort, Port endPort) {
        super(name, depth, pos, startPort, endPort);
    }

    static void renderDiamond(Coor pos, double s, Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(pos.x - s, pos.y);
        poly.lineTo(pos.x, pos.y - s);
        poly.lineTo(pos.x + s, pos.y);
        poly.lineTo(pos.x, pos.y + s);
        poly.closePath();
        g2.setColor(Color.WHITE);
        g2.fill(poly);
        g2.setColor(Color.BLACK);
        g2.draw(poly);
    }

    public static void printLine(Coor sp, Coor ep, Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Double(sp.x, sp.y, ep.x, ep.y));
        renderDiamond(ep, 6, g2);
        renderDiamond(sp, 6, g2);
    }
    
    @Override
    public void print(Graphics2D g2, boolean selected) {
        g2.setStroke(new BasicStroke(2));
        Coor sp = this.startPos();
        Coor ep = this.endPos();
        g2.draw(new Line2D.Double(sp.x, sp.y, ep.x, ep.y));

        if(selected) {
            double x = (sp.x + ep.x)/2;
            double y = (sp.y + ep.y)/2;
            g2.setColor(Color.BLACK);
            Rectangle2D.Double sShape = new Rectangle2D.Double(x - 6, y - 6, 12, 12);
            g2.fill(sShape);
        }

        renderDiamond(ep, 6, g2);
        renderDiamond(sp, 6, g2);

    }

}
