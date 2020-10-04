package UMLManager;
import Shapes.*;
import java.util.*;
import Mode.*;

import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
//import javax.swing.JFrame;
import javax.swing.BorderFactory;
import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
//import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.geom.*;



public class Manager extends JPanel {
    ArrayList<UMLObject> _globalObjList;
    int _amount;
    ArrayList<UMLObject> _selected;
    boolean _onPress;

    Coor _frameStart;
    Coor _lineStart;

    int _panelHeight;
    int _panelWidth;

    Mode _mode;
    Coor _mouseRec;
    ArrayList<Integer> _reserveDepth;

    public final static int _defaultH = 100;
    public final static int _defaultW = 80;


    public Manager() {
        _mode = new Select(this);
        setBorder(BorderFactory.createLoweredBevelBorder());
        //setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Coor clickPos = new Coor();
                clickPos.x = e.getX();
                clickPos.y = e.getY();
                _mouseRec.x = clickPos.x;
                _mouseRec.y = clickPos.y;
                _mode.press(clickPos);
                //repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Coor mPos = new Coor();
                mPos.x = e.getX();
                mPos.y = e.getY();
                _mode.drag(mPos);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                Coor pos = new Coor();
                pos.x = e.getX();
                pos.y = e.getY();
                _mode.release(pos);
                repaint();
            }
        });

        _globalObjList = new ArrayList<UMLObject>();
        _selected = new ArrayList<UMLObject>();
        _amount = 0;
        _mouseRec = new Coor();
        _lineStart = new Coor();
        _frameStart = new Coor();
        _reserveDepth = new ArrayList<Integer>();
    }

    public boolean selectObj(Coor clickPos) {
        UMLObject obj = clickOnObjCheck(clickPos, true);

        if(obj == null) {
            return false;
        } else if(_selected.contains(obj)) {
            return true;
        } else {
            _selected.clear();
            _selected.add(obj);
            return true;
        }
    }

    public void setObjName(String name) {
        if(_selected.size() == 1) {
            System.out.println("Success set name");
            _selected.get(0).setName(name);
        } else {
            System.out.println("Select more than one obj.");
        }
    }

    public UMLObject clickOnObjCheck(Coor clickPos, boolean includeLine) {
        ArrayList<UMLObject> objList = new ArrayList<UMLObject>();
        for(UMLObject obj : _globalObjList) {
            if(obj.isTouch(clickPos)) {
                if(!includeLine && (obj instanceof LineObj)) {
                    continue;
                } 
                objList.add(obj);
            }
        }
        if(objList.size() == 0) {
            System.out.println("There is no OBJ!");
            return null;
        }
        return objList.stream().max(Comparator.comparing(UMLObject::depth)).get();
    }


    public void moveSelected(Coor dPos) {
        double offsetX = (dPos.x - _mouseRec.x);
        double offsetY = (dPos.y - _mouseRec.y);
        for(UMLObject obj : _selected) {
            Coor pos = new Coor();
            pos.x = obj.pos().x + offsetX;
            pos.y = obj.pos().y + offsetY;
            obj.move(pos);
        }
        _mouseRec.x = dPos.x;
        _mouseRec.y = dPos.y;
    }


    public boolean startLining(Coor clickPos) {
        UMLObject selected = clickOnObjCheck(clickPos, false);
        if(selected != null) {
            if(selected instanceof PortObj) {
                _lineStart.x = ((PortObj)selected).touchPort(clickPos).pos().x;
                _lineStart.y = ((PortObj)selected).touchPort(clickPos).pos().y;
                return true;
            }
        }
        return false;
    }

    public Coor getLineStart() {
        Coor tmp = new Coor();
        tmp.x = _lineStart.x;
        tmp.y = _lineStart.y;
        return tmp;
    }

    public void startFraming(Coor clickPos) {

        _mouseRec.x = clickPos.x;
        _mouseRec.y = clickPos.y;
        _frameStart.x = clickPos.x;
        _frameStart.y = clickPos.y;
    }

    void lining(Coor mPos) {
        _mouseRec.x = mPos.x;
        _mouseRec.y = mPos.y;
    }

    void framing(Coor mPos) {
        _mouseRec.x = mPos.x;
        _mouseRec.y = mPos.y;
    }

    void endDragging(Coor rPos) {
        double offsetX = (_mouseRec.x - rPos.x);
        double offsetY = (_mouseRec.y - rPos.y);
        for(UMLObject obj : _selected) {
            Coor pos = new Coor();
            pos.x = obj.pos().x + offsetX;
            pos.y = obj.pos().y + offsetY;
            obj.move(pos);
        }
        _mouseRec.x = rPos.x;
        _mouseRec.y = rPos.y;
    }


    public void endFraming(Coor rPos) {
        _selected.clear();
        Coor sPos = new Coor();
        sPos.x = _frameStart.x;
        sPos.y = _frameStart.y;
        for(UMLObject obj : _globalObjList) {
            if(obj.isInFrame(sPos, rPos)) {
                _selected.add(obj);
            }
        }
    }

    public void addObj(UMLObject uo) {
        _globalObjList.add(uo);
        repaint();
    }

    public int maxDepth() {
        int maxDepth;
        if(_globalObjList.size() != 0) {
            maxDepth = Collections.max(_globalObjList, Comparator.comparing(o -> o.depth())).depth();
        } else {
            maxDepth = -1;
        }
        return maxDepth;
    }

    int selectedSize() {
        return _selected.size();
    }

    public void group() {
        if(_selected.size() < 2) {
            return;
        }

        int maxDepth = Collections.max(_globalObjList, Comparator.comparing(o -> o.depth())).depth();
        ArrayList<UMLObject> tmp = new ArrayList<UMLObject>();
        int count = 0;
        double x = 0;
        double y = 0;
        for(UMLObject o : _selected) {
            count += 1;
            x += o.pos().x;
            y += o.pos().y;
            tmp.add(o);
            _globalObjList.remove(o);
            _reserveDepth.add(o.depth());
        }
        Coor pos = new Coor();
        pos.x = x/count;
        pos.y = y/count;
        _globalObjList.add(new Composite("Group", maxDepth+1, pos, tmp));
    }

    public void ungroup() {
        if(_selected.size() != 1) {
            return;
        }
        UMLObject obj = _selected.get(0);
        if(obj instanceof Composite) {
            _globalObjList.remove((Composite)obj);
            ArrayList<UMLObject> list = ((Composite)obj).getObjList();
            for(UMLObject o : list) {
                _reserveDepth.remove(Integer.valueOf(o.depth()));
            }
            _globalObjList.addAll(list);
        }
    }

    public void initSize() {
        _panelHeight = this.getSize().height;
        _panelWidth = this.getSize().width;
    }
/*
    ArrayList<UMLObject> compositeUnfold(Composite obj) {
        ArrayList<UMLObject> tmpList = new ArrayList<UMLObject>();
        ArrayList<UMLObject> oList = obj.getObjList();
        for(UMLObject tmpObj : oList) {
            if(tmpObj instanceof Composite) {
                ArrayList<UMLObject> list = compositeUnfold((Composite)tmpObj);
                for(UMLObject tmpObj2 : list) {
                    tmpList.add(tmpObj2);
                }
            }
            tmpList.add(tmpObj);
        }
        //might be good for GC
        oList = null;
        return tmpList;
    }
*/
    public void setMode(Mode mode) {
        System.out.println("switching mode");
        _mode = mode;
    }

    public void reFresh() {
        repaint();
    }

    public void renderFraming(Coor ePos, Graphics2D g2) {
        Coor sPos = new Coor();
        sPos.x = _frameStart.x;
        sPos.y = _frameStart.y;
        g2.setStroke(new BasicStroke(2));
        double sx = Math.min(sPos.x, ePos.x);
        double sy = Math.min(sPos.y, ePos.y);
        double ex = Math.max(sPos.x, ePos.x);
        double ey = Math.max(sPos.y, ePos.y);
        Color c = new Color(240, 240, 240, 127);
        
        g2.setColor(c);
        Rectangle2D.Double shape = new Rectangle2D.Double(sx, sy, Math.abs(ex - sx), Math.abs(ey - sy));
        g2.fill(shape);
        g2.setColor(Color.BLACK);
        g2.draw(shape);
    }

    void renderLineing(Coor sPos, Coor ePos, Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        Line2D.Double shape = new Line2D.Double(sPos.x, sPos.y, ePos.x, ePos.y);
        g2.draw(shape);
    }


    public void objRemove() {
        for(UMLObject o : _selected) {
            if(o instanceof Composite) {
                for(UMLObject co : ((Composite)o).unfold()) {
                    _reserveDepth.remove(Integer.valueOf(co.depth()));
                }
            }
            _globalObjList.remove(o);
        }
        Collections.sort(_globalObjList, Comparator.comparing( o -> o.depth() ));
        int i = 0;
        for(UMLObject o : _globalObjList) {
            while(_reserveDepth.contains(Integer.valueOf(i))){
                i += 1;
            }
            o.setDepth(i);
        }
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        Collections.sort(_globalObjList, Comparator.comparing(o -> o.depth()));
        ((Graphics2D)g).setStroke(new BasicStroke(5));
        boolean selectedCheck;
        for(UMLObject pObj : _globalObjList) {
            selectedCheck = _selected.contains(pObj);
            pObj.print(g2, selectedCheck);
        }
        _mode.rendering(g2);
        // Simulate FAMING/LINING
    }

}