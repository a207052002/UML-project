package Shapes;

import java.util.ArrayList;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;
import java.awt.Color;

public abstract class LineObj extends UMLObject {
    public LineObj(final String name, final int depth, final Coor pos, Port startPort, Port endPort) {
        super(name, depth, pos);
        _startPort = startPort;
        _endPort = endPort;
        _pos.x = Math.min(startPort.pos().x, endPort.pos().x);
        _pos.y = Math.min(startPort.pos().y, endPort.pos().y);
    }

    Port _startPort;
    Port _endPort;

    public Coor startPos() {
        Coor sp = new Coor();
        sp.x = _startPort.pos().x;
        sp.y = _startPort.pos().y;
        return sp;
    }

    public Coor endPos() {
        Coor ep = new Coor();
        ep.x = _endPort.pos().x;
        ep.y = _endPort.pos().y;
        return ep;
    }

    public boolean isTouch(Coor clickPos) {
        Coor sPos = _startPort.pos();
        Coor ePos = _endPort.pos();
        double m = (ePos.y - sPos.y)/(ePos.x - sPos.x);
        Coor minp = new Coor();
        Coor maxp = new Coor();
        minp.x = Math.min(sPos.x, ePos.x);
        maxp.x = Math.max(sPos.x, ePos.x);
        minp.y = Math.min(sPos.y, ePos.y);
        maxp.y = Math.max(sPos.y, ePos.y);
        double d = Math.abs(((clickPos.y - sPos.y) - m * (clickPos.x - sPos.x))/ Math.sqrt(Math.pow(m, 2) + 1));
        if(d < 3 && clickPos.x <= maxp.x && clickPos.x >= minp.x && clickPos.y <= maxp.y && clickPos.y >= minp.y) {
            
            return true;
        } else {
            return false;
        }
    }

    public boolean isInFrame(final Coor sPos, final Coor ePos) {
        Coor sPortPos = new Coor();
        Coor ePortPos = new Coor();
        sPortPos = _startPort.pos();
        ePortPos = _endPort.pos();
        if((sPos.x - sPortPos.x) * (ePos.x - sPortPos.x) <= 0 && (sPos.y - sPortPos.y) * (ePos.y - sPortPos.y) <= 0) {
            if((sPos.x - ePortPos.x) * (ePos.x - ePortPos.x) <= 0 && (sPos.y - ePortPos.y) * (ePos.y - ePortPos.y) <= 0) {
                return true;
            }
        }
        return false;
    }

}