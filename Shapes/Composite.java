package Shapes;
import java.awt.Graphics2D;
import java.util.*;

public class Composite extends UMLObject{
    
    public Composite(String name, int depth, Coor pos, ArrayList<UMLObject> objList) {
        super(name, depth, pos);
        _objList = objList;
    }

    ArrayList<UMLObject> _objList;

    public ArrayList<UMLObject> getObjList() {
        ArrayList<UMLObject> list = new ArrayList<UMLObject>();
        for(UMLObject o : _objList)list.add(o);
        return list;
    }

    @Override
    public void move(Coor mPos) {
        double offsetX = mPos.x - _pos.x;
        double offsetY = mPos.y - _pos.y;
        Coor p = new Coor();
        
        for(UMLObject obj : _objList) {
            p.x = obj.pos().x + offsetX;
            p.y = obj.pos().y + offsetY;
            obj.move(p);
        }
    }

    public ArrayList<UMLObject> unfold() {
        ArrayList<UMLObject> tmpList = new ArrayList<UMLObject>();
        for(UMLObject o : _objList) {
            if(o instanceof Composite) {
                tmpList.addAll(((Composite)o).unfold());
            } else {
                tmpList.add(o);
            }
        }
        return tmpList;
    }

    public boolean isTouch(Coor clickPos) {
        for(UMLObject obj : _objList) {
            if(obj.isTouch(clickPos)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInFrame(final Coor sPos, final Coor ePos) {
        for(UMLObject obj : _objList) {
            if(!obj.isInFrame(sPos, ePos)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void print(Graphics2D g2, boolean selected) {
        for(UMLObject o : unfold()) {
            o.print(g2, selected);
        }
    }
}