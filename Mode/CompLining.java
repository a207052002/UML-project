package Mode;
import UMLManager.*;
import Shapes.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CompLining extends Mode {
    public CompLining(Manager m) {
        super(m);
        _lining = false;
    }
    public CompLining(Manager m, Coor ep) {
        super(m, ep);
    }

    boolean _lining;
    
    @Override
    public void press(Coor pos) {
        _lining = _m.startLining(pos);
        _ep.x = pos.x;
        _ep.y = pos.y;
    }

    @Override
    public void drag(Coor pos) {
        if(_lining) {
            _ep.x = pos.x;
            _ep.y = pos.y;
        }
    }

    @Override
    public void release(Coor pos) {
        UMLObject rObj = _m.clickOnObjCheck(pos, false);
        if(rObj != null) {
            if(rObj instanceof PortObj) {
                Port sPort = ((PortObj)_m.clickOnObjCheck(_m.getLineStart(), false)).touchPort(_m.getLineStart());
                Port ePort = ((PortObj)rObj).touchPort(pos);
                int depth = _m.maxDepth() + 1;
                _m.addObj(new CompLine("", depth, pos, sPort, ePort));
            }
        }
        _lining = false;
    }
    @Override
    public void rendering(Graphics2D g2) {
        if(_lining){
            CompLine.printLine(_m.getLineStart(), _ep, g2);
        }
    }
}