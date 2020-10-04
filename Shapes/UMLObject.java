package Shapes;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class UMLObject {

    public UMLObject(String name, int depth, Coor pos) {
        _name = name;
        _depth = depth;
        _pos = new Coor();
        _pos.x = pos.x;
        _pos.y = pos.y;
    }

    protected Coor _pos;
    protected int _depth;
    protected String _name;

    public abstract boolean isTouch(final Coor clickPos);
    public abstract boolean isInFrame(final Coor sPos, final Coor ePos);
    public abstract void print(Graphics2D g2, boolean selected);

    public int depth() {
        return _depth;
    }
    public String name() {
        String tmp = new String(_name);
        return tmp;
    }
    public Coor pos() {
        Coor pos = new Coor();
        pos.x = _pos.x;
        pos.y = _pos.y;
        return pos;
    }


    public void setDepth(int depth) {
        _depth = depth;
    }

    public void setName(String name) {
        _name = name;
    }

    public void move(Coor pos) {
        _pos.x = pos.x;
        _pos.y = pos.y;
    }
}