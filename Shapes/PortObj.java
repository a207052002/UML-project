package Shapes;

import java.util.ArrayList;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.GlyphVector;

public abstract class PortObj extends UMLObject{

    final static int NPORT = 0;
    final static int EPORT = 1;
    final static int WPORT = 2;
    final static int SPORT = 3;
    final static int ERRORPORT = 4;

    public PortObj(final String name, final int depth, final Coor pos, final double height, final double width) {
        super(name, depth, pos);
        _portList = new ArrayList<Port>();
        _h = height;
        _w = width;
        _nPort = new Port(NPORT, this);
        _ePort = new Port(EPORT, this);
        _wPort = new Port(WPORT, this);
        _sPort = new Port(SPORT, this);
        _portList.add(_nPort);
        _portList.add(_ePort);
        _portList.add(_wPort);
        _portList.add(_sPort);
    }

    protected double _h;
    protected double _w;

    protected Port _nPort;
    protected Port _ePort;
    protected Port _wPort;
    protected Port _sPort;
    protected ArrayList<Port> _portList;

    public abstract boolean isTouch(final Coor clickPos);

    public double height() {
        return _h;
    }

    public double width() {
        return _w;
    }

    public ArrayList<Coor> portPos() {
        ArrayList<Coor> pl = new ArrayList<Coor>();
        for(Port p : _portList) {
            pl.add(p.pos());
        }
        return pl;
    }

    public Port touchPort(final Coor clickPos) {
        Coor centerPos = new Coor();
        Port port;
        final double m = _h / _w;
        centerPos.x = _pos.x + _w / 2;
        centerPos.y = _pos.y + _h / 2;
        final double condition1 = (clickPos.y - centerPos.y) - (clickPos.x - centerPos.x) * m;
        final double condition2 = (clickPos.y - centerPos.y) - (clickPos.x - centerPos.x) * -m;
        if(condition1 > 0 && condition2 > 0) {
            port = _sPort;
        } else if(condition1 > 0 && condition2 <= 0) {
            port = _wPort;
        } else if(condition1 <= 0 && condition2 >0) {
            port = _ePort;
        } else if(condition1 <=0 && condition2 <=0) {
            port = _nPort;
        } else {
            System.out.println("I shouldn't print it");
            port = null;
        }
        return port;
    }

    public boolean isInFrame(final Coor sPos, final Coor ePos) {
        Coor s = new Coor();
        Coor e = new Coor();
        s.x = Math.min(sPos.x, ePos.x);
        e.x = Math.max(sPos.x, ePos.x);
        s.y = Math.min(sPos.y, ePos.y);
        e.y = Math.max(sPos.y, ePos.y);
        if((s.x - _pos.x) * (e.x - _pos.x) <= 0 && (s.y - _pos.y) * (e.y - _pos.y) <= 0) {
            if((s.x - _pos.x - _w) * (e.x - _pos.x - _w) <= 0 && (s.y - _pos.y - _h) * (e.y - _pos.y - _h) <= 0) {
                return true;
            }
        }
        return false;
    }

    protected void renderPort(Graphics2D g2) {
        double portSize = 12;
        for(Coor pPos : this.portPos()) {
            double x = pPos.x;
            double y = pPos.y;
            x = x - portSize/2;
            y = y - portSize/2;
            g2.setColor(Color.BLACK);
            Rectangle2D.Double shape = new Rectangle2D.Double(x, y, portSize, portSize);
            g2.fill(shape);
        }
    }

    protected void renderText(Graphics2D g2) {
        String name = new String(_name);
        Coor pos = this.pos();
        double ow = this.width();

        Coor center = new Coor();
        center.x = pos.x + this.width()/2;
        center.y = pos.y + this.height()/2;
        double tmp;
        Font font = g2.getFont();
        GlyphVector gv = font.layoutGlyphVector(g2.getFontRenderContext(), name.toCharArray(), 0, name.length(), GlyphVector.FLAG_MASK);
        Rectangle2D bound = gv.getVisualBounds();
        double width = bound.getWidth();
        double height = bound.getHeight();

        tmp = width;
        int lines = 1;
        while(tmp > ow * 0.9) {
            lines += 1;
            tmp -= ow * 0.9;
        }
        double totalHeight = lines * height;
        double totalWidth = ow;

        Coor tsPos = new Coor();

        tsPos.x = center.x - (totalWidth * 0.9 >=  width ? width/2 : (totalWidth * 0.9) / 2);
        tsPos.y = center.y - totalHeight/2;

        int seg = name.length()/lines;

        for(int i = 0 ; i < lines ; i++) {
            g2.setColor(Color.BLACK);
            g2.drawString(name.substring(seg * i, seg * (i + 1)), (int)tsPos.x + 1, (int)(tsPos.y + (i + 1) * height));
        }

    }
}