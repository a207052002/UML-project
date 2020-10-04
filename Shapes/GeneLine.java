package Shapes;

import java.util.ArrayList;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public class GeneLine extends LineObj{
    public GeneLine(final String name, final int depth, final Coor pos, Port startPort, Port endPort) {
        super(name, depth, pos, startPort, endPort);
    }


    public static void printLine(Coor sp, Coor ep, Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Double(sp.x, sp.y, ep.x, ep.y));
        double d = Math.sqrt(Math.pow(ep.x - sp.x, 2) + Math.pow(ep.y - sp.y, 2));
        double unitX = (ep.x - sp.x)/d;
        double unitY = (ep.y - sp.y)/d;
        Line2D.Double decoLine = new Line2D.Double(ep.x, ep.y, ep.x - unitX*15, ep.y - unitY*15);
        AffineTransform at1 = AffineTransform.getRotateInstance(Math.toRadians(20), decoLine.getX1(), decoLine.getY1());
        AffineTransform at2 = AffineTransform.getRotateInstance(Math.toRadians(-20), decoLine.getX1(), decoLine.getY1());
        Path2D.Double poly = new Path2D.Double();
        double Ax = ((Path2D.Double)at1.createTransformedShape(decoLine)).getCurrentPoint().getX();
        double Ay = ((Path2D.Double)at1.createTransformedShape(decoLine)).getCurrentPoint().getY();
        double Bx = ((Path2D.Double)at2.createTransformedShape(decoLine)).getCurrentPoint().getX();
        double By = ((Path2D.Double)at2.createTransformedShape(decoLine)).getCurrentPoint().getY();
        //Path2D.Double po = (Path2D.Double)at1.createTransformedShape(decoLine);
        poly.moveTo(ep.x, ep.y);
        poly.lineTo(Ax, Ay);
        poly.lineTo(Bx, By);
        poly.closePath();
        //po.append(at2.createTransformedShape(decoLine), false);
        //po.closePath();
        g2.setColor(Color.WHITE);
        g2.fill(poly);
        g2.setColor(Color.BLACK);
        g2.draw(poly);
    }

    @Override
    public void print(Graphics2D g2, boolean selected) {
        g2.setStroke(new BasicStroke(2));
        Coor sp = this.startPos();
        Coor ep = this.endPos();
        g2.draw(new Line2D.Double(sp.x, sp.y, ep.x, ep.y));
        double d = Math.sqrt(Math.pow(ep.x - sp.x, 2) + Math.pow(ep.y - sp.y, 2));
        double unitX = (ep.x - sp.x)/d;
        double unitY = (ep.y - sp.y)/d;
        Line2D.Double decoLine = new Line2D.Double(ep.x, ep.y, ep.x - unitX*15, ep.y - unitY*15);
        AffineTransform at1 = AffineTransform.getRotateInstance(Math.toRadians(20), decoLine.getX1(), decoLine.getY1());
        AffineTransform at2 = AffineTransform.getRotateInstance(Math.toRadians(-20), decoLine.getX1(), decoLine.getY1());

        if(selected) {
            double x = (sp.x + ep.x)/2;
            double y = (sp.y + ep.y)/2;
            g2.setColor(Color.BLACK);
            Rectangle2D.Double sShape = new Rectangle2D.Double(x - 6, y - 6, 12, 12);
            g2.fill(sShape);
        }
        Path2D.Double poly = new Path2D.Double();
        double Ax = ((Path2D.Double)at1.createTransformedShape(decoLine)).getCurrentPoint().getX();
        double Ay = ((Path2D.Double)at1.createTransformedShape(decoLine)).getCurrentPoint().getY();
        double Bx = ((Path2D.Double)at2.createTransformedShape(decoLine)).getCurrentPoint().getX();
        double By = ((Path2D.Double)at2.createTransformedShape(decoLine)).getCurrentPoint().getY();
        //Path2D.Double po = (Path2D.Double)at1.createTransformedShape(decoLine);
        poly.moveTo(ep.x, ep.y);
        poly.lineTo(Ax, Ay);
        poly.lineTo(Bx, By);
        poly.closePath();
        //po.append(at2.createTransformedShape(decoLine), false);
        //po.closePath();
        g2.setColor(Color.WHITE);
        g2.fill(poly);
        g2.setColor(Color.BLACK);
        g2.draw(poly);
    }
}